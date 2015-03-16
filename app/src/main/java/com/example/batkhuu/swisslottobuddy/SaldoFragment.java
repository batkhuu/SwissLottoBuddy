package com.example.batkhuu.swisslottobuddy;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class SaldoFragment extends Fragment {

    public SaldoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saldo, container, false);
        final LinearLayout pieChartCont = (LinearLayout) view.findViewById(R.id.piechart);
        final GraphicalView[] graphicalView = new GraphicalView[1];
        final DatabaseHandler dbh = new DatabaseHandler(super.getActivity());
        final Spinner spinner = (Spinner) view.findViewById(R.id.saldo_filter);
        final TextView ausgaben, gewinn;

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
                Cursor cs = dbh.get(spinner.getSelectedItem().toString());

                CategorySeries series = new CategorySeries("pie");
                int []colors = new int[]{Color.RED, Color.GREEN};
                DefaultRenderer renderer = new DefaultRenderer();

                if (cs == null) {
                    Log.v("SLB", "hat null");
                } else if (cs.moveToFirst()) {
                    ausgaben.setText(cs.getString(0));
                    gewinn.setText(cs.getString(1));
                    Log.v("SLB", cs.getInt(0)+" "+cs.getInt(1));
                    series.add("Ausgaben", cs.getInt(0));
                    series.add("Gewinn", cs.getInt(1));
                }

                for(int color : colors){
                    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
                    r.setColor(color);
                    // r.setDisplayBoundingPoints(true);
                    // r.setDisplayChartValuesDistance(5);
                    r.setDisplayChartValues(true);
                    r.setChartValuesTextSize(15);
                    renderer.addSeriesRenderer(r);
                }

                renderer.isInScroll();
                //renderer.setZoomButtonsVisible(false); //setting zoom button of Graph
                //renderer.setApplyBackgroundColor(false);
                //renderer.setBackgroundColor(Color.BLACK); //setting background color of chart
                renderer.setPanEnabled(false);
                renderer.setZoomEnabled(false);
                //renderer.setChartTitle("Result Chart"); //setting title of chart
                renderer.setChartTitleTextSize((float) 50);
                renderer.setShowLabels(false);
                renderer.setLabelsTextSize(20);
                renderer.setLegendTextSize(25);

                graphicalView[0] = ChartFactory.getPieChartView(getActivity(),series,renderer);
                // Adding the pie chart to the custom layout
                pieChartCont.addView(graphicalView[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
