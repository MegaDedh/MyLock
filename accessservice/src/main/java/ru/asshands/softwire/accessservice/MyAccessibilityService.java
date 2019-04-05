package ru.asshands.softwire.accessservice;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {
    private String TAG = "TAG";

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG, "onServiceConnected");

    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "get event");
        final int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {


/*            StringBuilder sb = new StringBuilder();
            for (CharSequence s : event.getText()) {
                sb.append(s);
            }*/


            AccessibilityNodeInfo nodeInfo = event.getSource();

            if (nodeInfo == null) {
                return;
            }
        //    nodeInfo.refresh();

            Log.d(TAG, "ClassName:" + nodeInfo.getClassName() +
                    " Text:" + nodeInfo.getText() +
                    " ViewIdResourceName:" + nodeInfo.getViewIdResourceName() +
                    " isClickable:" + nodeInfo.isClickable());
        }
    //    AccessibilityNodeInfo source = findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
    //    Log.d(TAG, source.getViewIdResourceName());//viewIdResourceName = source.getViewIdResourceName();


      //      performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
            Log.e(TAG, "get nodeInfo");

        }





    @Override
    public void onInterrupt() {
    }


}
