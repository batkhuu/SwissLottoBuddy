package com.example.batkhuu.swisslottobuddy;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    TextView drawdate, nextdrawdate, jackpot, numbers, luckynumber, replaynumber;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        // DB-Instance
        DatabaseHandler dbh = new DatabaseHandler(super.getActivity());
        Cursor cursor = dbh.getLastDraw();


        // Set Text for Startpage
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            drawdate = (TextView) view.findViewById(R.id.drawdate);
            drawdate.setText(cursor.getString(1));
            nextdrawdate = (TextView) view.findViewById(R.id.nextdrawdate);
            nextdrawdate.setText(cursor.getString(2));
            jackpot = (TextView) view.findViewById(R.id.jackpot);
            jackpot.setText(cursor.getString(3));
            numbers = (TextView) view.findViewById(R.id.numbers);
            numbers.setText(cursor.getString(4)+", "+cursor.getString(5)+", "+cursor.getString(6)+", "+cursor.getString(7)+", "+cursor.getString(8)+", "+cursor.getString(9));
            luckynumber = (TextView) view.findViewById(R.id.luckynumber);
            luckynumber.setText(cursor.getString(10));
            replaynumber = (TextView) view.findViewById(R.id.replaynumber);
            replaynumber.setText(cursor.getString(0));
        }
        return view;
    }


}
