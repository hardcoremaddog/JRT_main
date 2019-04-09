package com.javarush.task.task27.task2712.statistic.event;

import java.util.Date;

public class NoAvailableVideoEventDataRow implements EventDataRow {
	private int totalDuration;
	private Date currentDate;

	public NoAvailableVideoEventDataRow(int totalDuration) {
		this.totalDuration = totalDuration;
		currentDate = new Date();
	}

	@Override
	public int getTime() {
		return totalDuration;
	}

	@Override
	public EventType getType() {
		return EventType.NO_AVAILABLE_VIDEO;
	}

	@Override
	public Date getDate() {
		return currentDate;
	}

	public int getTotalDuration() {
		return totalDuration;
	}

	public Date getCurrentDate() {
		return currentDate;
	}
}
