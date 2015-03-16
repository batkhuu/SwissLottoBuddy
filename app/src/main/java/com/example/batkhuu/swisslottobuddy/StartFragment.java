package com.example.batkhuu.swisslottobuddy;


import android.database.Cursor;
import android.graphics.Typeface;
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

    TextView drawdate, nextdrawdate, jackpot, numbers, luckynumber, replaynumber, tips_nd;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        // DB-Instance
        DatabaseHandler dbh = new DatabaseHandler(super.getActivity());
        Cursor lastDraw = dbh.getLastDraw();

        // Set Text for Startpage
        if (lastDraw.moveToFirst()){
            drawdate = (TextView) view.findViewById(R.id.draw_date);
            drawdate.setText(lastDraw.getString(1));
            drawdate.setTypeface(null, Typeface.BOLD);

            nextdrawdate = (TextView) view.findViewById(R.id.next_draw_date);
            nextdrawdate.setText(lastDraw.getString(2));
            nextdrawdate.setTypeface(null, Typeface.BOLD);

            jackpot = (TextView) view.findViewById(R.id.jackpot);
            jackpot.setText(lastDraw.getString(3));
            jackpot.setTypeface(null, Typeface.BOLD);

            numbers = (TextView) view.findViewById(R.id.numbers);
            numbers.setText(lastDraw.getString(4)+", "+lastDraw.getString(5)+", "+lastDraw.getString(6)+", "+lastDraw.getString(7)+", "+lastDraw.getString(8)+", "+lastDraw.getString(9));
            numbers.setTypeface(null, Typeface.BOLD);

            luckynumber = (TextView) view.findViewById(R.id.lucky_number);
            luckynumber.setText(lastDraw.getString(10));
            luckynumber.setTypeface(null, Typeface.BOLD);

            replaynumber = (TextView) view.findViewById(R.id.replay_number);
            replaynumber.setText(lastDraw.getString(11));
            replaynumber.setTypeface(null, Typeface.BOLD);
        }

        Cursor ndTips = dbh.getNdTips();
        tips_nd = (TextView) view.findViewById(R.id.tips_nd);

        if (ndTips.moveToFirst()){
            String tips = "";
            do {
                tips = tips+ndTips.getString(2)+" - "+ndTips.getString(3)+" - "+ndTips.getString(4)+" - "
                        +ndTips.getString(5)+" - "+ndTips.getString(6)+" - "+ndTips.getString(7)+"   LN: "+
                        ndTips.getString(8)+"\n";
            } while (ndTips.moveToNext());

            tips_nd.setText("Tipps für die nächste Ziehung:\n"+tips);
        } else {
            tips_nd.setText("Keine Tipps für die nächste Ziehung");
        }

        return view;
    }
}
