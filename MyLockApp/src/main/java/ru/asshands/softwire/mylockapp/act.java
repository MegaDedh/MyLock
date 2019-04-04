package ru.asshands.softwire.mylockapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.Window;

public class act extends Activity implements YesNoDialogFragment.NoticeDialogListener{

    public DevicePolicyManager devicePolicyManager;
    private ComponentName compName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //скрываем заголовок activity
        setContentView(R.layout.act);

        YesNoDialogFragment YesNoDialog = new YesNoDialogFragment();
        YesNoDialog.show(getFragmentManager(), "YesNoDialogFragment");
        //диалог нельзя закрыть кнопкой назад(иначе диалог закроется а activity нет)
        YesNoDialog.setCancelable(false);
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        compName = new ComponentName(this, MyAdmin.class);



    }


    @Override
    public void onDialogPositiveClick(DialogFragment YesNoDialogFragment) {
        devicePolicyManager.removeActiveAdmin(compName);
//        Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment YesNoDialogFragment) {
//        Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        finish();
    }

}
