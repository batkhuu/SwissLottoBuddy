package com.example.batkhuu.swisslottobuddy;


import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Slice;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaldoFragment extends Fragment {

    public SaldoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_saldo, container, false);
        final DatabaseHandler dbh = new DatabaseHandler(super.getActivity());
        final Spinner spinner = (Spinner) view.findViewById(R.id.saldo_filter);
        final TextView ausgaben, gewinn;
        final ImageView imgView = (ImageView) view.findViewById(R.id.pie_Chart);


        ausgaben = (TextView) view.findViewById(R.id.ausgaben);
        gewinn = (TextView) view.findViewById(R.id.gewinn);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(super.getActivity(), R.array.saldo_filter, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cs = null;
                if (spinner.getSelectedItemPosition() == 0) {
                    cs = dbh.get0();
                } else if (spinner.getSelectedItemPosition() == 1) {
                    cs = dbh.get1();
                } else if (spinner.getSelectedItemPosition() == 2) {
                    cs = dbh.get2();
                } else if (spinner.getSelectedItemPosition() == 3) {
                    cs = dbh.get3();
                } else if (spinner.getSelectedItemPosition() == 4) {
                    cs = dbh.get4();
                }

                if (cs == null) {
                    Log.v("SLB", "hat null");
                } else if (cs.moveToFirst()) {
                    ausgaben.setText(cs.getString(0));
                    gewinn.setText(cs.getString(1));

                    Slice s1 = Slice.newSlice(cs.getInt(0), Color.newColor("CC0000"), "Ausgaben");
                    Slice s2 = Slice.newSlice(cs.getInt(1), Color.newColor("669900"), "Gewinn");
                    PieChart chart = GCharts.newPieChart(s1, s2);
                    chart.setSize(500, 200);
                    chart.setThreeD(true);
                    String refURL = chart.toURLString();

                    /*
                    try {
                        InputStream is = (InputStream) new URL(refURL).getContent();
                        imgView.setImageDrawable(Drawable.createFromStream(is, "src name"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
