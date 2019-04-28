package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser implements IPQuery {

	private Path logDir;
	private List<String> allLogLines;

	private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public LogParser(Path logDir) {
		this.logDir = logDir;
		this.allLogLines = getAllLogLines();
	}

	private String getMatch(String logLine, String groupName) {
		String match = null;
		Matcher m = Pattern.compile(
				"(?<ip>[\\d]+.[\\d]+.[\\d]+.[\\d]+)\\s" +
				"(?<name>[a-zA-Z ]+)\\s" +
				"(?<date>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\\s" +
				"(?<event>[\\w]+)\\s?(" +
				"(?<taskNumber>[\\d]+)|)\\s" +
				"(?<status>[\\w]+)")
				.matcher(logLine);
		if (m.find()) {
			match = m.group(groupName);
		}
		return match;
	}

	private List<String> getAllLogLines() {
		List<String> logLines = new ArrayList<>();

		try {
			List<Path> files = Files.list(logDir)
					.filter(p -> p.toString()
					.endsWith(".log"))
					.collect(Collectors.toList());
			for (Path log : files) {
				logLines.addAll(Files.readAllLines(log));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logLines;
	}

	private List<String> getLogLinesByDate(Date after, Date before) {
		List<String> logLines = new ArrayList<>();

		for (String logLine : allLogLines) {
			Date date = getDateFromLogLine(logLine);

			if ((after == null || !date.before(after)) && (before == null || !date.after(before))) {
				logLines.add(logLine);
			}
		}
		return logLines;
	}

	private Date getDateFromLogLine(String logLine) {
		String dateString = getMatch(logLine, "date");
		Date date = null;

		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	private String getIPFromLogLine(String logLine) {
		return getMatch(logLine, "ip");
	}

	private String getUserFromLogLine(String logLine) {
		return getMatch(logLine, "name");
	}

	private Event getEventFromLogLine(String logLine) {
		String stringEvent = getMatch(logLine, "event");
		Event event = null;

		switch (stringEvent) {
			case "LOGIN" : {
				event = Event.LOGIN;
				break;
			}
			case "DOWNLOAD_PLUGIN" : {
				event = Event.DOWNLOAD_PLUGIN;
				break;
			}
			case "WRITE_MESSAGE" : {
				event = Event.WRITE_MESSAGE;
				break;
			}
			case "SOLVE_TASK" : {
				event = Event.SOLVE_TASK;
				break;
			}
			case "DONE_TASK" : {
				event = Event.DONE_TASK;
				break;
			}
		}

		return event;
	}

	private Status getStatusFromLogLine(String logLine) {
		String stringStatus = getMatch(logLine, "status");
		Status status = null;

		switch (stringStatus) {
			case "OK": {
				status = Status.OK;
				break;
			}
			case "FAILED": {
				status = Status.FAILED;
				break;
			}
			case "ERROR": {
				status = Status.ERROR;
				break;
			}
		}
		return status;
	}

	@Override
	public int getNumberOfUniqueIPs(Date after, Date before) {
		return getUniqueIPs(after, before).size();
	}

	@Override
	public Set<String> getUniqueIPs(Date after, Date before) {
		Set<String> ips = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			ips.add(getIPFromLogLine(logLine));
		}
		return ips;
	}

	@Override
	public Set<String> getIPsForUser(String user, Date after, Date before) {
		Set<String> ips = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (user.equals(getUserFromLogLine(logLine))) {
				ips.add(getIPFromLogLine(logLine));
			}
		}
		return ips;
	}

	@Override
	public Set<String> getIPsForEvent(Event event, Date after, Date before) {
		Set<String> ips = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (event.equals(getEventFromLogLine(logLine))) {
				ips.add(getIPFromLogLine(logLine));
			}
		}
		return ips;
	}

	@Override
	public Set<String> getIPsForStatus(Status status, Date after, Date before) {
		Set<String> ips = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (status.equals(getStatusFromLogLine(logLine))) {
				ips.add(getIPFromLogLine(logLine));
			}
		}
		return ips;
	}
}