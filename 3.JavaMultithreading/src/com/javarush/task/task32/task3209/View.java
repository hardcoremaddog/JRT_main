package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;
import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private Controller controller;
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);

    private JTabbedPane tabbedPane = new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();

    public Controller getController() {
        return controller;
    }

    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public UndoListener getUndoListener() {
        return undoListener;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Новый"            : controller.createNewDocument();
            case "Открыть"          : controller.openDocument();
            case "Сохранить"        : controller.saveDocument();
            case "Сохранить как..."  : controller.saveDocumentAs();
            case "Выход"            : controller.exit();
            case "О программе"      : showAbout();
        }
    }

    public void init() {
        initGui();
        addWindowListener(new FrameListener(this));
        setVisible(true);
    }

    public void exit() {
        controller.exit();
    }

    public void initMenuBar() {
        JMenuBar jMenuBar = new JMenuBar(); //9.1.1. Создавать новый объект типа JMenuBar. Это и будет наша панель меню.

        MenuHelper.initFileMenu(this, jMenuBar);
        MenuHelper.initEditMenu(this, jMenuBar);
        MenuHelper.initStyleMenu(this, jMenuBar);
        MenuHelper.initAlignMenu(this, jMenuBar);
        MenuHelper.initColorMenu(this, jMenuBar);
        MenuHelper.initFontMenu(this, jMenuBar);
        MenuHelper.initHelpMenu(this, jMenuBar); //9.1.2. С помощью MenuHelper инициализировать меню в следующем порядке:
        // Файл, Редактировать, Стиль, Выравнивание, Цвет, Шрифт и Помощь.

        getContentPane().add(jMenuBar, BorderLayout.NORTH); //9.1.3. Добавлять в верхнюю часть панели контента текущего фрейма
        // нашу панель меню, аналогично тому, как это мы делали с панелью вкладок.


    }

    public void initEditor() {
        htmlTextPane.setContentType("text/html");

        tabbedPane.addTab("HTML", new JScrollPane(htmlTextPane));
        tabbedPane.addTab("Текст", new JScrollPane(plainTextPane));

        tabbedPane.setPreferredSize(new Dimension(500, 300));

        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public void initGui() {
        initMenuBar();
        initEditor();
        pack();
    }

    public boolean canUndo() {
        return undoManager.canUndo();
    }

    public boolean canRedo() {
        return undoManager.canRedo();
    }

    public void selectedTabChanged() {
         switch (tabbedPane.getSelectedIndex()) {
             case 0 : controller.setPlainText(plainTextPane.getText());
             case 1 : plainTextPane.setText(controller.getPlainText());
         }
         resetUndo();
    }

    public void undo() {
        try {
            undoManager.undo();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public void redo() {
        try {
            undoManager.redo();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public void resetUndo() {
        undoManager.discardAllEdits();
    }

    public void selectHtmlTab() {
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }

    public boolean isHtmlTabSelected() {
        return tabbedPane.getSelectedIndex() == 0;
    }

    public void update() {
        htmlTextPane.setDocument(controller.getDocument());
    }

    public void showAbout() {
        Object message = "Created by hardcoremaddog \n All Rights Reserved";
        JOptionPane.showMessageDialog(getParent(), message, "HTML Viewer v1.0", JOptionPane.INFORMATION_MESSAGE);
    }
}
