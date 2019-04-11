package ru.asshands.softwire.mylockapp;


import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.content.Context.DEVICE_POLICY_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyWidget extends AppWidgetProvider {

    final static  String TAG = "TAG";
    final static String ACTION_LOCK_NOW = "ru.startandroid.mylock.lock_now";
    public DevicePolicyManager devicePolicyManager;
    private ComponentName compName;
    private boolean useAccessibility;




    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        SharedPreferences sp = context.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE);
        useAccessibility = sp.getBoolean(ConfigActivity.USE_ACCESSIBILITY_SERVICE_PREF, false);


        // обновляем все экземпляры
        for (int i : appWidgetIds) {
                updateWidget(context, appWidgetManager, i);

        }
    }

    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        // При удалении можно например написать чтобы убирались админские права
    }

    @Override
    public void onDisabled(Context context) { // когда удалили последний виджет с экрана
        super.onDisabled(context);
        Intent actIntent = new Intent(context, act.class);
        actIntent.addFlags(FLAG_ACTIVITY_NEW_TASK); // флаг для запуска активности из контекста без активности
        context.startActivity (actIntent);
    }

    static void updateWidget(Context ctx, AppWidgetManager appWidgetManager, int widgetID) {
        RemoteViews widgetView = new RemoteViews(ctx.getPackageName(), R.layout.widget);



        // формируем PendingIntent для отправки по нажатию на виджет
        Intent lockIntent = new Intent(ctx, MyWidget.class);
        lockIntent.setAction(ACTION_LOCK_NOW);
        lockIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        PendingIntent pIntent = PendingIntent.getBroadcast(ctx, widgetID, lockIntent, 0);
        widgetView.setOnClickPendingIntent(R.id.tvBlockNow, pIntent);



        // Обновляем виджет
        appWidgetManager.updateAppWidget(widgetID, widgetView);
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (!useAccessibility) {
            devicePolicyManager = (DevicePolicyManager) context.getSystemService(DEVICE_POLICY_SERVICE);
            compName = new ComponentName(context, MyAdmin.class);

            //     Toast.makeText(context, "OnReceive", Toast.LENGTH_SHORT).show();

            // Проверяем, что этот intent содержит "заблокировать"

            if (intent.getAction().equalsIgnoreCase(ACTION_LOCK_NOW)) { // ACTION_LOCK_NOW


                // извлекаем ID экземпляра
                int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    mAppWidgetId = extras.getInt(
                            AppWidgetManager.EXTRA_APPWIDGET_ID,
                            AppWidgetManager.INVALID_APPWIDGET_ID);

                }
                if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {

                    boolean active = devicePolicyManager.isAdminActive(compName);
                    if (active) {
                        devicePolicyManager.lockNow();
                    } else {
                        Toast.makeText(context, "You need to enable the Admin Device Features", Toast.LENGTH_SHORT).show();
                    }

                }
            }
         //   Log.e(TAG, "Nothing to DO");
            //   Toast.makeText(context, "Nothing to DO", Toast.LENGTH_SHORT).show();
            devicePolicyManager = null;
            compName = null;
        }
    }
}