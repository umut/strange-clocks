package com.hoydaa.strangeclock;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;

/**
 * An analog clock that show hour and minute with two gears connected to each other.
 * 
 * @author Umut Utkan
 */
public class GearClockWidget extends AppWidgetProvider {

	private static final int WIDTH = 320;

	private static final int HEIGHT = 320;

	private CalendarUtil _util = new CalendarUtil();

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());

		Drawable bigGear = context.getResources().getDrawable(R.drawable.big_gear);
		Drawable smallGear = context.getResources().getDrawable(R.drawable.small_gear);
		Drawable indicator = context.getResources().getDrawable(R.drawable.indicator);

		_util.setCalendar(Calendar.getInstance());

		float hourAngle = _util.getHourAngle();
		float minuteAngle = _util.getMinuteAngle();
		// float secondAngle = _util.getSecondAngle();

		int xCenterForBigGear = (WIDTH - 305) / 2 + bigGear.getIntrinsicWidth() / 2;
		int xCenterForSmallGear = WIDTH - (WIDTH - 305) / 2 - smallGear.getIntrinsicWidth() / 2;

		for (int appWidgetId : appWidgetIds) {
			Bitmap b = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b);
			Paint paint = new Paint();
			paint.setColor(0xff000000);
			paint.setStyle(Style.FILL_AND_STROKE);

			indicator.setBounds(xCenterForBigGear - indicator.getIntrinsicWidth() / 2, HEIGHT / 2
					- bigGear.getIntrinsicHeight() / 2 - indicator.getIntrinsicHeight(), xCenterForBigGear
					+ indicator.getIntrinsicWidth() / 2, HEIGHT / 2 - bigGear.getIntrinsicHeight() / 2);
			indicator.draw(c);
			indicator.setBounds(xCenterForSmallGear - indicator.getIntrinsicWidth() / 2, HEIGHT / 2
					- smallGear.getIntrinsicHeight() / 2 - indicator.getIntrinsicHeight(), xCenterForSmallGear
					+ indicator.getIntrinsicWidth() / 2, HEIGHT / 2 - smallGear.getIntrinsicHeight() / 2);
			indicator.draw(c);

			c.rotate(-1 * hourAngle, xCenterForBigGear, HEIGHT / 2);
			bigGear.setBounds(xCenterForBigGear - bigGear.getIntrinsicWidth() / 2, HEIGHT / 2
					- bigGear.getIntrinsicHeight() / 2, xCenterForBigGear + bigGear.getIntrinsicWidth() / 2, HEIGHT / 2
					+ bigGear.getIntrinsicHeight() / 2);
			bigGear.draw(c);
			c.rotate(hourAngle, xCenterForBigGear, HEIGHT / 2);
			c.rotate(minuteAngle, xCenterForSmallGear, HEIGHT / 2);
			smallGear.setBounds(xCenterForSmallGear - smallGear.getIntrinsicWidth() / 2, HEIGHT / 2
					- smallGear.getIntrinsicHeight() / 2, xCenterForSmallGear + smallGear.getIntrinsicWidth() / 2,
					HEIGHT / 2 + smallGear.getIntrinsicHeight() / 2);
			smallGear.draw(c);

			c.save();

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_gear);
			views.setImageViewBitmap(R.id.image_gear, b);
			views.setTextViewText(R.id.text_date, format.format(_util.getTime()));
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

}
