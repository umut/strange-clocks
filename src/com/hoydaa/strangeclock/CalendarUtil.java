package com.hoydaa.strangeclock;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	private Calendar _calendar;

	private int _hour;

	private int _minute;

	private int _second;

	public CalendarUtil() {

	}

	public void setCalendar(Calendar calendar) {
		this._calendar = calendar;
		_hour = _calendar.get(Calendar.HOUR_OF_DAY);
		_minute = _calendar.get(Calendar.MINUTE) % 60;
		_second = _calendar.get(Calendar.SECOND) % 60;
	}

	public float getHourAngle() {
		return (float) ((_hour + _minute / 60.0) * 30);
	}
	
	public int getHour() {
		return _calendar.get(Calendar.HOUR);
	}
	
	public int getHourOfDay() {
		return _calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public float getMinuteAngle() {
		return (float) (_minute * 6 + _second / 10.0);
	}

	public int getMinute() {
		return _calendar.get(Calendar.MINUTE);
	}
	
	public float getSecondAngle() {
		return (float) (_second * 6.0);
	}
	
	public int getSecond() {
		return _calendar.get(Calendar.SECOND);
	}
	
	public Date getTime() {
		return _calendar.getTime();
	}

}
