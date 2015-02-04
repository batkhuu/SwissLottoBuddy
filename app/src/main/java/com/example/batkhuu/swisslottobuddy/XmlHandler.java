package com.example.batkhuu.swisslottobuddy;


import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class XmlHandler {

    // static variables
    public static final String XMLURL = "http://www.swisslos.ch/swisslotto/lottonormal_teaser_getdata.do";

    public XmlHandler(){
        // Required empty public constructor
    }

    public String handle() {
        try {
            ContentValues draw = downloadXml();
            //DatabaseHandler dbh = new DatabaseHandler(super);
            //dbh.addDraw(draw);
            //Log.v("SLB", "Result: " + draw.getAsString("win_class_index7"));
            return "Successfully refreshed";
        } catch (IOException e) {
            return "Unable to load content. Check your network connection.";
        } catch (XmlPullParserException e) {
            return "XML Parse Error";
        }
    }

    private ContentValues downloadXml() throws IOException, XmlPullParserException {
        InputStream is = null;
        XmlParser xmlParser = new XmlParser();
        ContentValues contentValues = null;

        try {
            is = downloadUrl();
            contentValues = xmlParser.parse(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }

        return contentValues;
    }

    private InputStream downloadUrl() throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(XMLURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            Log.d("SLB", "conn.connect");
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("SLB", "The response is: " + response);
            is = conn.getInputStream();

            return is;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
