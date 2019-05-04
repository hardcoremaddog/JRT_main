package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
	private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";
	private static final String CACHED_URL = "https://javarush.ru/testdata/big28data.html";
	private int PAGE_VALUE = 0;

	@Override
	public List<Vacancy> getVacancies(String searchString) {
		List<Vacancy> vacancies = new ArrayList<>();

		Document doc = null;

		try {
			doc = getDocument(searchString, PAGE_VALUE);
			while (true) {
				Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
				if (elements.size() == 0) {
					PAGE_VALUE = 0;
					break;
				}
				for (Element element : elements) {
					if (element != null) {
						Vacancy vac = new Vacancy();
						vac.setTitle(element.getElementsByAttributeValueContaining("data-qa", "title").text());
						vac.setCity(element.getElementsByAttributeValueContaining("data-qa", "address").text());
						vac.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
						vac.setSiteName(URL_FORMAT);
						String urlPage = element.getElementsByAttributeValueContaining("data-qa", "title").attr("href");
						vac.setUrl(urlPage);
						String salary = element.getElementsByAttributeValueContaining("data-qa", "compensation").text();
						vac.setSalary(salary.length() == 0 ? "" : salary);
						vacancies.add(vac);
					}
				}
				PAGE_VALUE++;
				doc = getDocument(searchString, PAGE_VALUE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vacancies;
	}

	protected Document getDocument(String searchString, int page) throws IOException {
		if (searchString != null) {
			String url = String.format(URL_FORMAT, searchString, page);
			Connection connection = Jsoup.connect(url);
			Document doc = connection
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")
					.referrer("no-referrer-when-downgrade").get();
			return doc;
		}
		return null;
	}
}
