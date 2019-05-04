package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
	private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";



	@Override
	public List<Vacancy> getVacancies(String searchString) {
		List<Vacancy> vacancies = new ArrayList<>();

		if (searchString != null) {
			Document doc = null;
			try {
				Connection connection = Jsoup.connect(URL_FORMAT);
				doc = connection
						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")
						.referrer("").get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Elements links = doc.select("a[href]");





		}
		return vacancies;
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width-1) + ".";
		else
			return s;
	}
}
