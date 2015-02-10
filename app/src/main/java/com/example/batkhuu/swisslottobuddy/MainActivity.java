package com.example.batkhuu.swisslottobuddy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    // static variables
    public static final String XMLURL = "http://www.swisslos.ch/swisslotto/lottonormal_teaser_getdata.do";

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
                    Fragment fragmentStart = new StartFragment();
                    return fragmentStart;
                case 1:
                    Fragment fragmentTipp = new TippFragment();
                    return fragmentTipp;
                case 2:
                    Fragment fragmentSaldo = new SaldoFragment();
                    return fragmentSaldo;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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
    }

    /**
     * ################################################################################
     * Custom methods start here
     * ################################################################################
     */

    private void refresh() throws ExecutionException, InterruptedException {
        if (refreshNeeded()){
            fetchXml();
        } else {
            toast = Toast.makeText(this, "Refresh not needed!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean refreshNeeded() {
        // data from the last draw
        Cursor lastDraw = dbh.getLastDraw();

        if (lastDraw.moveToFirst()){
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");

            try {
                Date dateNextDraw = sdf.parse(lastDraw.getString(2)+" 23:59");
                if (now.after(dateNextDraw)){
                    return true;
                } else {
                    return false;
                }
            } catch (ParseException e) {
                return false;
            }
        } else {
            return true; // if empty, then refresh
        }
    }

    public void backup() {
        Cursor cs = dbh.getNdTips();
        Log.v("SLB", String.valueOf(cs.getCount()));

        if (cs.moveToFirst()) {
            Log.v("SLB", " Tipps: " + cs.getString(1));
            while (cs.moveToNext()) {
                Log.v("SLB", " Tipps: " + cs.getString(1));
            }
        }
    }

    public void restore() {

    }

    public void exit() {
        finish();
    }

    public void fetchXml() throws ExecutionException, InterruptedException {
        // check network connection before download
        if (isOnline()) {
            ContentValues result = new XmlHandler().execute().get();
            if(result.size()>0) {
                dbh.addDraw(result);
                toast = Toast.makeText(this, "Refresh successful", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                toast = Toast.makeText(this, "Oops! There was an error!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            toast = Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
