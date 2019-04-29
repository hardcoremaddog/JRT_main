package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {

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

	private String getQLMatch(String query, String groupName) {
		String match = null;
		Matcher m = Pattern.compile(
				"get (?<field1>\\w+) for (?<field2>\\w+) = \"(?<value1>.*?)\"")
				.matcher(query);
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

	private boolean checkEventAndStatus(String logLine, Event event) {
		return event.equals(getEventFromLogLine(logLine)) && Status.OK.equals(getStatusFromLogLine(logLine));
	}

	/** IPQuery Interface*/
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

	/** UserQuery Interface*/
	@Override
	public Set<String> getAllUsers() {
		Set<String> allUniqueUsers = new HashSet<>();
		String user;

		for (String logLine : getAllLogLines()) {
			user = getUserFromLogLine(logLine);
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
			if (checkEventAndStatus(logLine, Event.DOWNLOAD_PLUGIN)) {
				downloadPluginUsers.add(getUserFromLogLine(logLine));
			}
		}
		return downloadPluginUsers;
	}

	@Override
	public Set<String> getWroteMessageUsers(Date after, Date before) {
		Set<String> wroteMessageUsers = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (checkEventAndStatus(logLine, Event.WRITE_MESSAGE)) {
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

	/**DateQuery Interface*/
	@Override
	public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
		Set<Date> datesForUserAndEvent = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (user.equals(getUserFromLogLine(logLine))) {
				if (event.equals(getEventFromLogLine(logLine))) {
					datesForUserAndEvent.add(getDateFromLogLine(logLine));
				}
			}
		}
		return datesForUserAndEvent;
	}

	@Override
	public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
		Set<Date> datesWhenSomethingFailed = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (getStatusFromLogLine(logLine).equals(Status.FAILED)) {
				datesWhenSomethingFailed.add(getDateFromLogLine(logLine));
			}
		}
		return datesWhenSomethingFailed;
	}

	@Override
	public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
		Set<Date> datesWhenErrorHappened = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (getStatusFromLogLine(logLine).equals(Status.ERROR)) {
				datesWhenErrorHappened.add(getDateFromLogLine(logLine));
			}
		}
		return datesWhenErrorHappened;
	}

	@Override
	public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
		List<Date> datesWhenUserLogged = new ArrayList<>();
		Date dateWhenUserLoggedFirstTime;

		for (String logLine : getLogLinesByDate(after, before)) {
			if (user.equals(getUserFromLogLine(logLine))
					&& checkEventAndStatus(logLine, Event.LOGIN)) {
				datesWhenUserLogged.add(getDateFromLogLine(logLine));
			}
		}
		Collections.sort(datesWhenUserLogged);
		try {
			dateWhenUserLoggedFirstTime = datesWhenUserLogged.get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return dateWhenUserLoggedFirstTime;
	}

	@Override
	public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
		List<Date> datesWhenUserSolvedTask = new ArrayList<>();
		Date dateWhenUserSolvedTaskFirstTime;

		for (String logLine : getLogLinesByDate(after, before)) {
			if (user.equals(getUserFromLogLine(logLine))
					&& checkEvent(logLine, Event.SOLVE_TASK)
					&& getMatch(logLine, "taskNumber").equals(String.valueOf(task))) {
				datesWhenUserSolvedTask.add(getDateFromLogLine(logLine));
			}
		}
		Collections.sort(datesWhenUserSolvedTask);
		try {
			dateWhenUserSolvedTaskFirstTime = datesWhenUserSolvedTask.get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return dateWhenUserSolvedTaskFirstTime;
	}

	@Override
	public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
		List<Date> datesWhenUserDoneTask = new ArrayList<>();
		Date dateWhenUserDoneTask;

		for (String logLine : getLogLinesByDate(after, before)) {
			if (user.equals(getUserFromLogLine(logLine))
					&& checkEvent(logLine, Event.DONE_TASK)
					&& getMatch(logLine, "taskNumber").equals(String.valueOf(task))) {
				datesWhenUserDoneTask.add(getDateFromLogLine(logLine));
			}
		}
		Collections.sort(datesWhenUserDoneTask);
		try {
			dateWhenUserDoneTask = datesWhenUserDoneTask.get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return dateWhenUserDoneTask;
	}

	@Override
	public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
		Set<Date> datesWhenUserWroteMessage = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (user.equals(getUserFromLogLine(logLine))
					&& checkEvent(logLine, Event.WRITE_MESSAGE)) {
				datesWhenUserWroteMessage.add(getDateFromLogLine(logLine));
			}
		}
		return datesWhenUserWroteMessage;
	}

	@Override
	public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
		Set<Date> datesWhenUserDownloadedPlugin = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (user.equals(getUserFromLogLine(logLine))
					&& checkEvent(logLine, Event.DOWNLOAD_PLUGIN)) {
				datesWhenUserDownloadedPlugin.add(getDateFromLogLine(logLine));
			}
		}
		return datesWhenUserDownloadedPlugin;
	}

	/**EventQuery Interface*/
	@Override
	public int getNumberOfAllEvents(Date after, Date before) {
		return getAllEvents(after, before).size();
	}

	@Override
	public Set<Event> getAllEvents(Date after, Date before) {
		Set<Event> allEvents = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			allEvents.add(getEventFromLogLine(logLine));
		}
		return allEvents;
	}

	@Override
	public Set<Event> getEventsForIP(String ip, Date after, Date before) {
		Set<Event> eventsForIP = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (ip.equals(getIPFromLogLine(logLine))) {
				eventsForIP.add(getEventFromLogLine(logLine));
			}
		}
		return eventsForIP;
	}

	@Override
	public Set<Event> getEventsForUser(String user, Date after, Date before) {
		Set<Event> eventsForUser = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (user.equals(getUserFromLogLine(logLine))) {
				eventsForUser.add(getEventFromLogLine(logLine));
			}
		}
		return eventsForUser;
	}

	@Override
	public Set<Event> getFailedEvents(Date after, Date before) {
		Set<Event> failedEvents = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (getStatusFromLogLine(logLine).equals(Status.FAILED)) {
				failedEvents.add(getEventFromLogLine(logLine));
			}
		}
		return failedEvents;
	}

	@Override
	public Set<Event> getErrorEvents(Date after, Date before) {
		Set<Event> errorEvents = new HashSet<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (getStatusFromLogLine(logLine).equals(Status.ERROR)) {
				errorEvents.add(getEventFromLogLine(logLine));
			}
		}
		return errorEvents;
	}

	@Override
	public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
		List<Event> solveTaskEvents = new ArrayList<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (checkEvent(logLine, Event.SOLVE_TASK)
					&& getMatch(logLine, "taskNumber").equals(String.valueOf(task))) {
				solveTaskEvents.add(getEventFromLogLine(logLine));
			}
		}
		return solveTaskEvents.size();
	}

	@Override
	public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
		List<Event> successfulSolveTaskEvents = new ArrayList<>();

		for (String logLine : getLogLinesByDate(after, before)) {
			if (checkEvent(logLine, Event.DONE_TASK)
					&& getMatch(logLine, "taskNumber").equals(String.valueOf(task))) {
				successfulSolveTaskEvents.add(getEventFromLogLine(logLine));
			}
		}
		return successfulSolveTaskEvents.size();
	}

	@Override
	public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
		return getLogLinesByDate(after, before)
				.stream().filter(logLine -> getEventFromLogLine(logLine).equals(Event.SOLVE_TASK))
				.collect(Collectors.toMap(logLine -> Integer.valueOf(getMatch(logLine, "taskNumber")), logLine -> 1, Integer::sum));
	}

	@Override
	public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
		return getLogLinesByDate(after, before)
				.stream().filter(logLine -> getEventFromLogLine(logLine).equals(Event.DONE_TASK))
				.collect(Collectors.toMap(logLine -> Integer.valueOf(getMatch(logLine, "taskNumber")), logLine -> 1, Integer::sum));
	}

	/**QLQuery Interface*/
	@Override
	public Set<Object> execute(String query) {
/**		Общий формат запроса с параметром:
		get field1 for field2 = "value1"
		Где: field1 - одно из полей: ip, user, date, event или status;
		field2 - одно из полей: ip, user, date, event или status;
		value1 - значение поля field2.

		Алгоритм обработки запроса следующий:
		просматриваем записи в логе,
		если поле field2 имеет значение value1,
		то добавляем поле field1 в множество,
		которое затем будет возвращено методом execute.*/

		Set<Object> returnData = new HashSet<>();

		String field1 = getQLMatch(query, "field1");
		String field2 = getQLMatch(query, "field2");
		String value1 = getQLMatch(query, "value1");

		if (field1 != null && field2 != null && value1 != null) {
			for (String logLine : getAllLogLines()) {

				if (getMatch(logLine, field2).equals(value1)) {
					switch (field1) {
						case "date" : {
							returnData.add(getDateFromLogLine(logLine));
							break;
						}
						case "event" : {
							returnData.add(getEventFromLogLine(logLine));
							break;
						}
						case "status" : {
							returnData.add(getStatusFromLogLine(logLine));
							break;
						}
						default : {
							returnData.add(getMatch(logLine, field1));
							break;
						}
					}
				}
			}
		}

		switch (query) {
			case "get ip" : {
				returnData = new HashSet<>(getUniqueIPs(null, null));
				break;
			}
			case "get user" : {
				returnData = new HashSet<>(getAllUsers());
				break;
			}
			case "get date" : {
				returnData = new HashSet<>();
				for (String logLine : getAllLogLines()) {
					returnData.add(getDateFromLogLine(logLine));
				}
				break;
			}
			case "get event" : {
				returnData = new HashSet<>(getAllEvents(null, null));
				break;
			}
			case "get status" : {
				returnData = new HashSet<>();
				for (String logLine : getAllLogLines()) {
					returnData.add(getStatusFromLogLine(logLine));
				}
				break;
			}
		}
		return returnData;
	}
}