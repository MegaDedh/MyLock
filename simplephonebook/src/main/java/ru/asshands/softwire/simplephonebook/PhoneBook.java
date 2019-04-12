package ru.asshands.softwire.simplephonebook;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class PhoneBook {
   private final List<PhonePersone> ArrayPhonePersone;

    public PhoneBook(List<PhonePersone> arrayPhonePersone) {
        this.ArrayPhonePersone = arrayPhonePersone;
    }


    public final PhoneBook addPhonePerson(String name, String surName, String telNum) {
        final String TAG = "TAG";
        PhonePersone addedPhonePersone = new PhonePersone(name,surName,telNum);

        for (PhonePersone tmpPersone : ArrayPhonePersone) {
            if (addedPhonePersone.equals(tmpPersone)){
                Log.e(TAG,"Ошибка добавления "+name+" "+surName+" "+telNum+". Такой номер уже есть.");
                return new PhoneBook(ArrayPhonePersone);
            }
        }
        ArrayPhonePersone.add(new PhonePersone(name,surName,telNum));
        return new PhoneBook(ArrayPhonePersone);

    }

    public final void showAll () {
        final String TAG = "TAG";
        Log.e(TAG,"*********************Show All PhoneBook**********************");
        for (PhonePersone tmpPersone: ArrayPhonePersone) {
                Log.e(TAG,tmpPersone.getName()+" "+tmpPersone.getSurName()+" "+tmpPersone.getTelNum());
            }
    }

    public final void find (String findStr){
        final String TAG = "TAG";

        List<String> findList= new ArrayList<String>();
        for (PhonePersone tmpPersone: ArrayPhonePersone) {
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



    public void testEqual() { // для теста
        final String TAG = "TAG";
        boolean equalResult = ArrayPhonePersone.get(2).equals(ArrayPhonePersone.get(3));
        Log.e(TAG,Boolean.toString(equalResult));
        int hashCode2 = ArrayPhonePersone.get(2).hashCode();
        int hashCode3 = ArrayPhonePersone.get(3).hashCode();

        Log.e(TAG,Integer.toString(hashCode2));
        Log.e(TAG,Integer.toString(hashCode3));

    }
}