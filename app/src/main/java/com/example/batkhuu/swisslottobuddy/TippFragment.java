package com.example.batkhuu.swisslottobuddy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


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

        Spinner spinner = (Spinner) view.findViewById(R.id.number_tips);
        Button generat_btn = (Button) view.findViewById(R.id.generate_btn);

        generat_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("SLB", "Generate Button clicked");
                TipGenerator tipGenerator = new TipGenerator();
                tipGenerator.createTip();
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
