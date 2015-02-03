package com.example.batkhuu.swisslottobuddy;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TippFragment extends Fragment {


    public TippFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tipp, container, false);

        final Spinner spinner = (Spinner) view.findViewById(R.id.number_tips);
        Button generate_btn = (Button) view.findViewById(R.id.generate_btn);

        generate_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Anzahl zu generierende Tipps
                int i = spinner.getSelectedItemPosition()+2;
                List<ContentValues> tips = new ArrayList<>();
                TipGenerator tipGenerator = new TipGenerator();

                for (int pos=0; pos<i; pos++){
                    ContentValues tip = null;
                    tip = tipGenerator.createTip();
                    tips.add(pos,tip);
                }

                for (ContentValues tip: tips){
                    Log.v("SLB", "Nrs: " + tip.getAsString("number1") + ", " +
                            tip.getAsString("number2") + ", " + tip.getAsString("number3") + ", " +
                            tip.getAsString("number4") + ", " + tip.getAsString("number5") + ", " +
                            tip.getAsString("number6") + " LN: " + tip.getAsString("lucky_number"));
                }
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(super.getActivity(),
                R.array.number_tips, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        return view;
    }
}