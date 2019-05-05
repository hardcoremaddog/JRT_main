package com.javarush.task.task28.task2810.view;

import com.google.common.base.Utf8;
import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
	private Controller controller;
	private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/vacancies.html";

	@Override
	public void update(List<Vacancy> vacancies) {
		try {
			updateFile(getUpdatedFileContent(vacancies));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void userCitySelectEmulationMethod() {
		controller.onCitySelect("Odessa");

	}

	protected Document getDocument() throws IOException {
		return Jsoup.parse(new File(filePath), "UTF-8");
	}

	private String getUpdatedFileContent(List<Vacancy> vacancies) {
		return null;
	}

	private void updateFile(String content) {
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
