package ru.asshands.softwire.mylockapp;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class ConfigActivity extends Activity {
    private String TAG = "TAG";
    private int api = Build.VERSION.SDK_INT;
    private boolean useAccessibility;
    public final static String WIDGET_PREF = "widget_pref";
    public final static String USE_ACCESSIBILITY_SERVICE_PREF = "USE_ACCESSIBILITY_SERVICE_PREF";


    int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultValue;

    private Button ok, disable, enable, btncheckAccessibilityService;
    private CheckBox chbUseAccessability;
    public static final int RESULT_ENABLE = 11;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName compName;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // извлекаем ID конфигурируемого виджета
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // и проверяем его корректность
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        // формируем intent ответа
        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

        // отрицательный ответ
        setResult(RESULT_CANCELED, resultValue);

        setContentView(R.layout.config);

        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        compName = new ComponentName(this, MyAdmin.class);

        ok = (Button) findViewById(R.id.okBtn);
        enable = (Button) findViewById(R.id.enableBtn);
        disable = (Button) findViewById(R.id.disableBtn);
        btncheckAccessibilityService = (Button) findViewById(R.id.btnCheckAccessibilityService);
        chbUseAccessability = (CheckBox) findViewById(R.id.CheckboxAccessability);


    }

    protected void onResume() {
        super.onResume();
        boolean isActive = devicePolicyManager.isAdminActive(compName);
        disable.setVisibility(isActive ? View.VISIBLE : View.GONE);
        enable.setVisibility(isActive ? View.GONE : View.VISIBLE);
    //    useAccessability.setVisibility((api >= 28) ? View.VISIBLE : View.GONE);
        if (api < 28) {
            useAccessibility = false;
            chbUseAccessability.setVisibility(View.GONE);
            btncheckAccessibilityService.setVisibility(View.GONE);
        }
        else{
            SharedPreferences sp = getSharedPreferences(ConfigActivity.WIDGET_PREF, MODE_PRIVATE);
            useAccessibility = sp.getBoolean(ConfigActivity.USE_ACCESSIBILITY_SERVICE_PREF, false);
            chbUseAccessability.setChecked(useAccessibility);
            btncheckAccessibilityService.setVisibility(useAccessibility ? View.VISIBLE : View.GONE);
            }
    }


    public void onClick(View view) {

        if (view == enable) {

            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Additional text explaining why we need this permission");
            startActivityForResult(intent, RESULT_ENABLE);

        } else if (view == disable) {
            devicePolicyManager.removeActiveAdmin(compName);
            disable.setVisibility(View.GONE);
            enable.setVisibility(View.VISIBLE);
        } else if (view == ok) {
            setResult(RESULT_OK, resultValue);
            finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case RESULT_ENABLE :
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(ConfigActivity.this, "You have enabled the Admin Device features", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConfigActivity.this, "Problem to enable the Admin Device features", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }




    // *** ДВА МЕТОДА ДЛЯ РАБОТЫ С AccessabilityService ***


    public void onClickCheckAccessibilityService (View v) {
        Log.e(TAG, "onClickCheckAccessibilityService");
        if (!isAccessibilitySettingsOn(getApplicationContext())) {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }
    }



    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + MyAccessibilityService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        Toast.makeText(mContext, "ACCESSIBILITY IS ENABLED", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }


    public void onCheckboxAccessabilityClicked(View view) {
        CheckBox chBox = (CheckBox) view;
        useAccessibility = chBox.isChecked();
        btncheckAccessibilityService.setVisibility(useAccessibility ? View.VISIBLE : View.GONE);

        SharedPreferences sp = getSharedPreferences(ConfigActivity.WIDGET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(USE_ACCESSIBILITY_SERVICE_PREF, useAccessibility);
        editor.commit();

    }
}