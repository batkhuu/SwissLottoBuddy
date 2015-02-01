package com.example.batkhuu.swisslottobuddy;


import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class XmlHandler extends AsyncTask<Void, Void, String> {

    // static variables
    public static final String XMLURL = "http://www.swisslos.ch/swisslotto/lottonormal_teaser_getdata.do";

    @Override
    protected String doInBackground(Void... params) {
        try {
            ContentValues draw = downloadXml(XMLURL);
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

    public ContentValues downloadXml(String stringurl) throws IOException, XmlPullParserException {
        InputStream is = null;
        XmlParser xmlParser = new XmlParser();
        ContentValues contentValues = null;

        try {
            is = downloadUrl(stringurl);
            contentValues = xmlParser.parse(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return contentValues;
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }
}
