package com.javarush.task.task27.task2712.statistic.event;


import com.javarush.task.task27.task2712.ad.Advertisement;

import java.util.Date;
import java.util.List;

public class VideoSelectedEventDataRow implements EventDataRow {
	private List<Advertisement> optimalVideoSet;
	private long amount;
	private int totalDuration;
	private Date currentDate;

	public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration) {
		this.optimalVideoSet = optimalVideoSet;
		this.amount = amount;
		this.totalDuration = totalDuration;
		currentDate = new Date();
	}

	@Override
	public EventType getType() {
		return EventType.SELECTED_VIDEOS;
	}

	@Override
	public int getTime() {
		return totalDuration;
	}

	@Override
	public Date getDate() {
		return currentDate;
	}

	public List<Advertisement> getOptimalVideoSet() {
		return optimalVideoSet;
	}

	public long getAmount() {
		return amount;
	}

	public int getTotalDuration() {
		return totalDuration;
	}

	public Date getCurrentDate() {
		return currentDate;
	}
}
