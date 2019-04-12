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

        PhoneBook myPhoneBook = new PhoneBook(new ArrayList<PhonePersone>());

        Log.e(TAG,myPhoneBook.toString());
        myPhoneBook = myPhoneBook.addPhonePerson("Max","Kovalev","10001");
        Log.e(TAG,myPhoneBook.toString());
        myPhoneBook = myPhoneBook.addPhonePerson("Alexey","Voronin","10002");
        Log.e(TAG,myPhoneBook.toString());
        myPhoneBook = myPhoneBook.addPhonePerson("Alexander","Bobkov","10003");
        Log.e(TAG,myPhoneBook.toString());
        myPhoneBook = myPhoneBook.addPhonePerson("Konstantin","Murzin","10003");
        Log.e(TAG,myPhoneBook.toString());
        myPhoneBook = myPhoneBook.addPhonePerson("Artem","Morozov","10004");
        Log.e(TAG,myPhoneBook.toString());



        myPhoneBook.showAll();
        String toFind="ko";   //<<< будем искать такой фрагмент
        Log.e(TAG,"*************Поиск совпадений строки: "+toFind+"*******************");
        myPhoneBook.find(toFind);
        Log.e(TAG,"******Результат работы equal и hashCode() для 2 и 3 записи: *******");
        myPhoneBook.testEqual();



    }


}
