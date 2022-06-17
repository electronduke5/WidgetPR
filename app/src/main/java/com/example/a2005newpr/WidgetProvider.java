package com.example.a2005newpr;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class WidgetProvider extends AppWidgetProvider {
    private static final String BTN_CLICK_ACTION = "BTN_CLICKED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        ComponentName widget = new ComponentName(context, WidgetProvider.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setOnClickPendingIntent(R.id.btnReplace, getPendingSelfIntent(context, BTN_CLICK_ACTION));

        appWidgetManager.updateAppWidget(widget, views);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, WidgetProvider.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


        if (BTN_CLICK_ACTION.equals(intent.getAction())) {
            new JokeLoader(text -> {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName widget = new ComponentName(context, WidgetProvider.class);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                views.setTextViewText(R.id.txt_Main, text);
                appWidgetManager.updateAppWidget(widget, views);
            }).execute();
        }
    }
}