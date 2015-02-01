package com.example.batkhuu.swisslottobuddy;

import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TipGenerator {
    private static final int min = 1;
    private static final int max_nr = 42;
    private static final int max_ln = 6;

    public TipGenerator() {
    }

    public ContentValues createTip(){
        ContentValues result = new ContentValues();
        List<Integer> numbers;
        int luckynumber = 0;
        int counter = 1;

        numbers = createNumbers();
        luckynumber = createLuckyNumber();

        for(int number: numbers){
            //Log.v("SLB", "Numbers: "+number);
            result.put("number"+counter,number);
            counter++;
        }
        //Log.v("SLB", "Luckynumber: "+luckynumber);
        result.put("lucky_number",luckynumber);

        return null;
    }

    private List<Integer> createNumbers() {
        List<Integer> values = new ArrayList<>();

        while (values.size()<=5){
            Random r = new Random();
            boolean duplicate = false;
            int zahl = r.nextInt(max_nr - min + 1) + min;

            //Log.v("SLB", "Number: "+zahl);

            if (values.size()==0) {
                values.add(zahl);
            } else {
                for (int value: values){
                    if (value==zahl) {
                        duplicate = true;
                    }
                }
                if(!duplicate){
                    values.add(zahl);
                }
            }

        }
        Collections.sort(values);
        return values;
    }

    private int createLuckyNumber() {
        int luckynumber = 0;
        Random r = new Random();
        luckynumber = r.nextInt(max_ln - min + 1) + min;
        return luckynumber;
    }
}