package ru.asshands.softwire.mylockapp;

import android.content.SharedPreferences;
import android.util.Log;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {
    private String TAG = "TAG";


    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG, "onServiceConnected");
 /*       SharedPreferences sp = getSharedPreferences(ConfigActivity.WIDGET_PREF, MODE_PRIVATE);
        boolean useAccessibility = sp.getBoolean(ConfigActivity.USE_ACCESSIBILITY_SERVICE_PREF, false);
        if (!useAccessibility) {
            disableSelf();//доступно только с API 24
        }*/
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "get any event");

        final int eventType = event.getEventType();
   //     Log.e(TAG, Integer.toString(eventType));
        if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            Log.e(TAG, "TYPE = TYPE_VIEW_CLICKED!");

            AccessibilityNodeInfo nodeInfo = event.getSource();

            if (nodeInfo == null) {
                return;
            }
            //      nodeInfo.refresh(); // в некоторых случаях помогает получть getViewIdResourceName. не помогло.
            Log.e(TAG, "get nodeInfo:");
            Log.d(TAG, "ClassName:" + nodeInfo.getClassName() +
                    " Text:" + nodeInfo.getText() +
                    " ViewIdResourceName:" + nodeInfo.getViewIdResourceName() +
                    " isClickable:" + nodeInfo.isClickable());
            if (nodeInfo.getText().equals("Lock") ){
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
            }
        }
    }

    @Override
    public void onInterrupt() {
    }
}