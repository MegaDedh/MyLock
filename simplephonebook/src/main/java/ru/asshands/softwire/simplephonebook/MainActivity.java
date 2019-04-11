package ru.asshands.softwire.simplephonebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG,"*********************ON CREATE**********************");

    }

    public void onClickbtnStart(View view) {

        List<Persone> ArrayPersone= new ArrayList<Persone>();

        ArrayPersone.add(new Persone("Max","Kovalev","10001"));
        ArrayPersone.add(new Persone("Alexey","Voronin","10003"));
        ArrayPersone.add(new Persone("Alexander","Bobkov","10003"));
        ArrayPersone.add(new Persone("Konstantin","Murzin","10004"));


        String findStr = "ko";
        List<String> findList= new ArrayList<String>();
        for (Persone tmpPersone: ArrayPersone) {
            String fullStrPersone = new String(tmpPersone.getName()+" "+tmpPersone.getSurName()+" "+tmpPersone.getTelNum());
            String fullStrPersoneLowCase = fullStrPersone.toLowerCase();
            int findIndex = fullStrPersoneLowCase.indexOf(findStr);
            if(findIndex != -1){
                findList.add(fullStrPersone);
            }
        }
        if (findList.size()>0){
            Log.e(TAG,"Найденные совпадения:");
            for(String tmpStr : findList){
                Log.e(TAG,tmpStr);
            }
        }
        else{
            Log.e(TAG,"Поиск ничего не нашел :(");
        }


    }


}
