package com.example.batkhuu.swisslottobuddy;


import android.content.ContentValues;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class XmlHandler extends AsyncTask<ContentValues, Void, ContentValues> {

    public static final String XMLURL = "http://www.swisslos.ch/swisslotto/lottonormal_teaser_getdata.do";
    public InputStream inputStream = null;

    @Override
    protected ContentValues doInBackground(ContentValues... params) {
        try {
            inputStream = downloadUrl();
            XmlParser parser = new XmlParser();
            parser.parse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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
        InputStream stream = conn.getInputStream();
        return stream;
    }
}
