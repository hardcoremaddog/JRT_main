package com.javarush.task.task28.task2810.view;

import com.google.common.base.Utf8;
import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

		Document document = null;
		try {
			document = getDocument();

			Element templateOriginal = document.getElementsByClass("template").first();
			Element copyTemplate = templateOriginal.clone();
			copyTemplate.removeAttr("style");
			copyTemplate.removeClass("template");
			document.select("tr[class=vacancy]").remove().not("tr[class=vacancy template");

			for (Vacancy vacancy : vacancies) {
				Element localClone = copyTemplate.clone();
				localClone.getElementsByClass("city").first().text(vacancy.getCity());
				localClone.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
				localClone.getElementsByClass("salary").first().text(vacancy.getSalary());
				Element link =localClone.getElementsByTag("a").first();
				link.text(vacancy.getTitle());
				link.attr("href", vacancy.getUrl());

				templateOriginal.before(localClone.outerHtml());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "Some exception occurred";
		}
		return document.html();
	}

	private void updateFile(String content) {
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
