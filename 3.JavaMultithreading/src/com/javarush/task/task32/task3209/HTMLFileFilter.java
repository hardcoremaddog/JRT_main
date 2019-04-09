package com.javarush.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {
	public HTMLFileFilter() {
		super();
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) return true;
		else {
			if (f.getName().toLowerCase().matches("^.+[.](html)?(htm)?$")) {
				return true;
			}
			return false;
		}
	}

	@Override
	public String getDescription() {
		return "HTML и HTM файлы";
	}
}
