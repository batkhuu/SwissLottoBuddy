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

public class XmlHandler extends AsyncTask<Void, Void, ContentValues> {
    // Swiss Lotto XML URL
    public static final String XMLURL = "http://www.swisslos.ch/swisslotto/lottonormal_teaser_getdata.do";

    @Override
    protected ContentValues doInBackground(Void... params) {
        ContentValues result = new ContentValues();
        try {
            result = downloadXml();
            //return draw;
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    private ContentValues downloadXml() throws IOException, XmlPullParserException {
        InputStream is = null;
        XmlParser xmlParser = new XmlParser();
        ContentValues result = null;

        try {
            is = downloadUrl();
            result = xmlParser.parse(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return result;
    }

    private InputStream downloadUrl() throws IOException {
        URL url = new URL(XMLURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream is = conn.getInputStream();
        return is;
    }
}
