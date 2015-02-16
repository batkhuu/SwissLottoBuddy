package com.example.batkhuu.swisslottobuddy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TippFragment extends Fragment {

    public TippFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tipp, container, false);
        final DatabaseHandler dbh = new DatabaseHandler(super.getActivity());
        final TextView tip1, tip2, tip3, tip4, tip5, tip6, tip7, tip8, tip9, tip10, tip11, tip12, tip13, tip14;

        final Context context = getActivity();
        final Toast[] toast = new Toast[1];

        final Spinner spinner = (Spinner) view.findViewById(R.id.number_tips);
        Button generate_btn = (Button) view.findViewById(R.id.generate_btn);
        final Button save_btn = (Button) view.findViewById(R.id.save_tips);
        tip1 = (TextView) view.findViewById(R.id.tip_1);
        tip2 = (TextView) view.findViewById(R.id.tip_2);
        tip3 = (TextView) view.findViewById(R.id.tip_3);
        tip4 = (TextView) view.findViewById(R.id.tip_4);
        tip5 = (TextView) view.findViewById(R.id.tip_5);
        tip6 = (TextView) view.findViewById(R.id.tip_6);
        tip7 = (TextView) view.findViewById(R.id.tip_7);
        tip8 = (TextView) view.findViewById(R.id.tip_8);
        tip9 = (TextView) view.findViewById(R.id.tip_9);
        tip10 = (TextView) view.findViewById(R.id.tip_10);
        tip11 = (TextView) view.findViewById(R.id.tip_11);
        tip12 = (TextView) view.findViewById(R.id.tip_12);
        tip13 = (TextView) view.findViewById(R.id.tip_13);
        tip14 = (TextView) view.findViewById(R.id.tip_14);

        generate_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // clear TextViews
                tip1.setText(" ");
                tip2.setText(" ");
                tip3.setText(" ");
                tip4.setText(" ");
                tip5.setText(" ");
                tip6.setText(" ");
                tip7.setText(" ");
                tip8.setText(" ");
                tip9.setText(" ");
                tip10.setText(" ");
                tip11.setText(" ");
                tip12.setText(" ");
                tip13.setText(" ");
                tip14.setText(" ");

                // TODO: maybe create objects outside method
                // Number of tips to generate
                int i = spinner.getSelectedItemPosition()+2;
                final List<ContentValues> tips = new ArrayList<>();
                TipGenerator tipGenerator = new TipGenerator();

                for (int pos=0; pos<i; pos++){
                    ContentValues tip;
                    tip = tipGenerator.createTip();
                    tips.add(pos,tip);
                    if(pos==0){
                        tip1.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==1){
                        tip2.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==2){
                        tip3.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==3){
                        tip4.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==4){
                        tip5.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==5){
                        tip6.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==6){
                        tip7.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==7){
                        tip8.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==8){
                        tip9.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==9){
                        tip10.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==10){
                        tip11.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==11){
                        tip12.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==12){
                        tip13.setText(tip.getAsString("number0")+" - "+tip.getAsString("number1")
                                +" - "+tip.getAsString("number2")+" - "+tip.getAsString("number3")
                                +" - "+tip.getAsString("number4")+" - "+tip.getAsString("number5")
                                +" LN: "+tip.getAsString("luckynumber"));
                    } else if(pos==13) {
                        tip14.setText(tip.getAsString("number0") + " - " + tip.getAsString("number1")
                                + " - " + tip.getAsString("number2") + " - " + tip.getAsString("number3")
                                + " - " + tip.getAsString("number4") + " - " + tip.getAsString("number5")
                                + " LN: " + tip.getAsString("luckynumber"));
                    }
                }

                save_btn.setVisibility(View.VISIBLE);
                save_btn.setClickable(true);

                save_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int dd = saveTipPossible();
                        if (dd != 0) {
                            for (ContentValues tip : tips) {
                                tip.put("draw_date",dd);
                                tip.put("spending", "2.50");
                                dbh.addTip(tip);
                                toast[0] = Toast.makeText(context, "Tips saved!", Toast.LENGTH_SHORT);
                                toast[0].show();
                            }
                        } else {
                            toast[0] = Toast.makeText(context, "Next Draw Date not known!", Toast.LENGTH_SHORT);
                            toast[0].show();
                        }

                        // clear TextViews
                        tip1.setText(" ");
                        tip2.setText(" ");
                        tip3.setText(" ");
                        tip4.setText(" ");
                        tip5.setText(" ");
                        tip6.setText(" ");
                        tip7.setText(" ");
                        tip8.setText(" ");
                        tip9.setText(" ");
                        tip10.setText(" ");
                        tip11.setText(" ");
                        tip12.setText(" ");
                        tip13.setText(" ");
                        tip14.setText(" ");

                        save_btn.setVisibility(View.INVISIBLE);
                        save_btn.setClickable(false);
                    }

                    private int saveTipPossible() {
                        Cursor cursor = dbh.getLastDraw();
                        if (cursor.moveToFirst()) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                            SimpleDateFormat sdf_wd = new SimpleDateFormat("dd.MM.yyyy");
                            Date weekday = new Date();
                            try {
                                weekday = sdf_wd.parse(cursor.getString(2));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Date now = new Date();
                            Date ndd = new Date();

                            // set last possible tip time
                            if (new SimpleDateFormat("EE").format(weekday).equals("Mi.")) {
                                try {
                                    ndd = sdf.parse(cursor.getString(2) + " 18:45");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else if (new SimpleDateFormat("EE").format(weekday).equals("Sa.")) {
                                try {
                                    ndd = sdf.parse(cursor.getString(2) + " 16:45");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (now.before(ndd)) {
                                return cursor.getInt(0);
                            } else {
                                return 0;
                            }
                        }
                        else {
                            return 0;
                        }
                    }
                });
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