package com.example.batkhuu.swisslottobuddy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    // keeps fragments in memory
    SectionsPagerAdapter mSectionsPagerAdapter;

    // host of the contents
    ViewPager mViewPager;

    // DB-Instance
    DatabaseHandler dbh = new DatabaseHandler(this);

    // for all Toasts
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        // always check oncreate if refresh needed
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_refresh:
                try {
                    refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.action_backup:
                backup();
                return true;
            case R.id.action_restore:
                restore();
                return true;
            case R.id.action_exit:
                exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // this code refreshes the fragments
        mViewPager.setCurrentItem(tab.getPosition());
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    return new StartFragment();
                case 1:
                    return new TippFragment();
                case 2:
                    return new SaldoFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

/*    *//**
     * A placeholder fragment containing a simple view.
     *//*
    public static class PlaceholderFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_start, container, false);
            return rootView;
        }
    }*/

    /**
     * ################################################################################
     * Custom methods start here
     * ################################################################################
     */

    private void refresh() throws ExecutionException, InterruptedException {
        if (refreshNeeded()){
            String msg = fetchXml();
            mViewPager.setCurrentItem(0);
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast = Toast.makeText(this, "Data up to date!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean refreshNeeded() {
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
            Date tip_adoption = new Date();
            Date drawing = new Date();

            // set last possible tip time
            if (new SimpleDateFormat("EE").format(weekday).equals("Mi.")) {
                try {
                    drawing = sdf.parse(cursor.getString(2) + " 21:55");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (new SimpleDateFormat("EE").format(weekday).equals("Sa.")) {
                try {
                    drawing = sdf.parse(cursor.getString(2) + " 19:35");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (now.after(drawing)) {
                Log.v("SLB","now: "+now);
                Log.v("SLB","tip_adoption: "+tip_adoption);
                Log.v("SLB","drawing: "+drawing);
                return true;
            } else {
                return false;
            }
        }
        else {
            return true;
        }
    }

    public void backup() {

    }

    public void restore() {

    }

    public void exit() {
        finish();
    }

    public String fetchXml() throws ExecutionException, InterruptedException {
        // check network connection before download
        if (isOnline()) {
            ContentValues result = new XmlHandler().execute().get();
            if(result.size()>0) {
                checkWinning(result);
                dbh.addDraw(result);
                return "Refresh successful";
            }
            else {
                return "Oops! There was an error!";
            }
        } else {
            return "No Connection";
        }
    }

    public void checkWinning(ContentValues result) {
        List<String> numbers = new ArrayList<>();
        numbers.add(result.getAsString("number0"));
        numbers.add(result.getAsString("number1"));
        numbers.add(result.getAsString("number2"));
        numbers.add(result.getAsString("number3"));
        numbers.add(result.getAsString("number4"));
        numbers.add(result.getAsString("number5"));

        String luckynumber = result.getAsString("luckynumber");

        Cursor tips = dbh.getNdTips();

        if (tips.moveToFirst()) {
            do {
                int i = 0;
                if (numbers.contains(tips.getString(2))) { i++; }
                if (numbers.contains(tips.getString(3))) { i++; }
                if (numbers.contains(tips.getString(4))) { i++; }
                if (numbers.contains(tips.getString(5))) { i++; }
                if (numbers.contains(tips.getString(6))) { i++; }
                if (numbers.contains(tips.getString(7))) { i++; }

                String winning = "0";

                // erst ab 3 getroffenen Zahlen machen wir weiter
                if (i>2 && luckynumber.equals(tips.getString(8))) {
                    if (i==3) {
                        winning = result.getAsString("win_class_index6");
                    } else if (i==4) {
                        winning = result.getAsString("win_class_index4");
                    } else if (i==5) {
                        winning = result.getAsString("win_class_index2");
                    } else if (i==6) {
                        winning = result.getAsString("win_class_index0");
                    }
                    dbh.updateTip(winning, tips.getString(0));
                } else if (i>2 && !luckynumber.equals(tips.getString(8))) {
                    if (i==3) {
                        winning = result.getAsString("win_class_index3");
                    } else if (i==4) {
                        winning = result.getAsString("win_class_index5");
                    } else if (i==5) {
                        winning = result.getAsString("win_class_index3");
                    } else if (i==6) {
                        winning = result.getAsString("win_class_index1");
                    }
                    dbh.updateTip(winning, tips.getString(0));
                }
            } while (tips.moveToNext());
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
