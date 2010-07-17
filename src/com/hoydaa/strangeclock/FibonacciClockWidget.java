package com.hoydaa.strangeclock;

import java.util.Calendar;

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
 * An analog clock that shows time using a spiral, each ring represents 8 hours.
 * 
 * @author Umut Utkan
 */
public class FibonacciClockWidget extends AppWidgetProvider {

	private static final int WIDTH = 320;

	private static final int HEIGHT = 320;

	private static final int DIAL_WIDTH = 4;

	private static final int[] DIAL_RADIUSES = new int[] { 36, 38, 40, 46, 55, 65, 70, 73, 73, 75, 80, 85, 93, 103,
			109, 113, 113, 115, 116, 123, 131, 142, 148 };

	private CalendarUtil _util = new CalendarUtil();

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Drawable fibonacci = context.getResources().getDrawable(R.drawable.fibonacci);

		_util.setCalendar(Calendar.getInstance());

		int hour = _util.getHourOfDay();
		int minute = _util.getMinute();
		float hourAngle = (float) (((hour % 8) + minute / 60.0) * 45);

		int currentDialHeight = DIAL_RADIUSES[hour];
		int dialDiff = DIAL_RADIUSES[hour + 1 == 24 ? hour : hour + 1] - currentDialHeight;
		int dialHeight = (int) (currentDialHeight + (minute / 60.0) * dialDiff);

		for (int appWidgetId : appWidgetIds) {
			Bitmap b = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b);
			Paint paint = new Paint();
			paint.setColor(0xffb82a2a);
			paint.setStyle(Style.FILL_AND_STROKE);

			c.rotate(hourAngle, 174, 174);
			c.drawRect(174 - DIAL_WIDTH / 2, 174 - dialHeight, 174 + DIAL_WIDTH / 2, 174, paint);
			c.rotate(-1 * hourAngle, 174, 174);

			fibonacci.setBounds(0, 0, WIDTH, HEIGHT);
			fibonacci.draw(c);

			c.save();

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_fibonacci);
			views.setImageViewBitmap(R.id.image_fibonacci, b);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

}
