package com.hoydaa.strangeclock;

import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Draws an analog clock with all hands are connected to each other.
 * 
 * @author Umut Utkan
 */
public class HandInHandClock extends AppWidgetProvider {

	private static final String LT = "handinhand";

	private static final int HOUR_BOTTOM_OFFSET = 8;

	private static final int HOUR_TOP_OFFSET = 8;

	private static final int MINUTE_OFFSET = 8;

	private int width = 146;

	private int height = 146;

	private CalendarUtil _util = new CalendarUtil();

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		Log.d(LT, "Width : " + width + " height: " + height);

		_util.setCalendar(Calendar.getInstance());

		for (int appWidgetId : appWidgetIds) {
			float hourAngle = _util.getHourAngle();
			float minuteAngle = _util.getMinuteAngle();
			// float secondAngle = _util.getSecondAngle();

			Drawable hourHand = context.getResources().getDrawable(R.drawable.hour);
			Drawable minuteHand = context.getResources().getDrawable(R.drawable.minute);

			Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b);
			Paint paint = new Paint();
			paint.setColor(0xff000000);
			paint.setStyle(Style.FILL_AND_STROKE);

			paint.setColor(0xAA000000);
			c.drawArc(new RectF(0, 0, width, height), 0, 360, false, paint);

			c.rotate(hourAngle, width / 2, height / 2);
			hourHand
					.setBounds(width / 2 - hourHand.getIntrinsicWidth() / 2, height / 2 - hourHand.getIntrinsicHeight()
							+ HOUR_BOTTOM_OFFSET, width / 2 + hourHand.getIntrinsicWidth() / 2, height / 2
							+ HOUR_BOTTOM_OFFSET);
			hourHand.draw(c);
			c.rotate(minuteAngle - hourAngle, width / 2, height / 2 - hourHand.getIntrinsicHeight()
					+ HOUR_BOTTOM_OFFSET + HOUR_TOP_OFFSET);
			minuteHand.setBounds(width / 2 - minuteHand.getIntrinsicWidth() / 2, height / 2
					- hourHand.getIntrinsicHeight() + HOUR_BOTTOM_OFFSET + HOUR_TOP_OFFSET
					- minuteHand.getIntrinsicHeight() + MINUTE_OFFSET, width / 2 + minuteHand.getIntrinsicWidth() / 2,
					height / 2 - hourHand.getIntrinsicHeight() + HOUR_BOTTOM_OFFSET + HOUR_TOP_OFFSET + MINUTE_OFFSET);
			minuteHand.draw(c);
			c.save();

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_handinhand);
			views.setImageViewBitmap(R.id.image, b);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}

	}

}
