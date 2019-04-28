package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;
import com.javarush.task.task39.task3913.query.UserQuery;

import javax.swing.tree.ExpandVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery {

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
				"(?<user>[a-zA-Z ]+)\\s" +
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
		return getMatch(logLine, "user");
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

	private boolean checkEvent(String logLine, Event event) {
		return event.equals(getEventFromLogLine(logLine));
	}

	private boolean checkEventAndStatus(String logLine, Event event, Status status) {
		return event.equals(getEventFromLogLine(logLine)) && status.equals(getStatusFromLogLine(logLine));
	}

	/** IPQuery Interface */
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

	/** UserQuery Interface */
	@Override
	public Set<String> getAllUsers() {
		Set<String> allUniqueUsers = new HashSet<>();
		String user;

		for (String logLine : getAllLogLines()) {
			user = getMatch(logLine, "user");
			allUniqueUsers.add(user);
		}
		return allUniqueUsers;
	}

	@Override
	public int getNumberOfUsers(Date after, Date before) {
		Set<String> allUniqueUsers = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			allUniqueUsers.add(getUserFromLogLine(logLine));
		}
		return allUniqueUsers.size();
	}

	@Override
	public int getNumberOfUserEvents(String user, Date after, Date before) {
		Set<Event> allUserEvents = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (user.equals(getUserFromLogLine(logLine))) {
				allUserEvents.add(getEventFromLogLine(logLine));
			}
		}
		return allUserEvents.size();
	}

	@Override
	public Set<String> getUsersForIP(String ip, Date after, Date before) {
		Set<String> usersByIP = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (ip.equals(getIPFromLogLine(logLine))) {
				usersByIP.add(getUserFromLogLine(logLine));
			}
		}
		return usersByIP;
	}

	@Override
	public Set<String> getLoggedUsers(Date after, Date before) {
		Set<String> loggedUsers = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (checkEvent(logLine, Event.LOGIN)) {
				loggedUsers.add(getUserFromLogLine(logLine));
			}
		}
		return loggedUsers;
	}

	@Override
	public Set<String> getDownloadedPluginUsers(Date after, Date before) {
		Set<String> downloadPluginUsers = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (checkEventAndStatus(logLine, Event.DOWNLOAD_PLUGIN, Status.OK)) {
				downloadPluginUsers.add(getUserFromLogLine(logLine));
			}
		}
		return downloadPluginUsers;
	}

	@Override
	public Set<String> getWroteMessageUsers(Date after, Date before) {
		Set<String> wroteMessageUsers = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (checkEventAndStatus(logLine, Event.WRITE_MESSAGE, Status.OK)) {
				wroteMessageUsers.add(getUserFromLogLine(logLine));
			}
		}
		return wroteMessageUsers;
	}

	@Override
	public Set<String> getSolvedTaskUsers(Date after, Date before) {
		Set<String> solvedTaskUsers = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (checkEvent(logLine, Event.SOLVE_TASK)) {
				solvedTaskUsers.add(getUserFromLogLine(logLine));
			}
		}
		return solvedTaskUsers;
	}

	@Override
	public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
		Set<String> solvedTaskUsers = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (checkEvent(logLine, Event.SOLVE_TASK)
					&& (getMatch(logLine, "taskNumber")
					.equals(String.valueOf(task)))) {
				solvedTaskUsers.add(getUserFromLogLine(logLine));
			}
		}
		return solvedTaskUsers;
	}

	@Override
	public Set<String> getDoneTaskUsers(Date after, Date before) {
		Set<String> doneTaskUsers = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (checkEvent(logLine, Event.DONE_TASK)) {
				doneTaskUsers.add(getUserFromLogLine(logLine));
			}
		}
		return doneTaskUsers;
	}

	@Override
	public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
		Set<String> doneTaskUsers = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (checkEvent(logLine, Event.DONE_TASK)
					&& getMatch(logLine, "taskNumber")
					.equals(String.valueOf(task))) {
				doneTaskUsers.add(getUserFromLogLine(logLine));
			}
		}
		return doneTaskUsers;
	}
}