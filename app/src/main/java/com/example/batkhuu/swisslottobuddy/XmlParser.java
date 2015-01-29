package com.example.batkhuu.swisslottobuddy;


import android.content.ContentValues;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class XmlParser {

    // no need for namespace
    private static final String ns = null;

    // Variable for tags
    private static final String TAG_ROOT = "data-response";
    private static final String TAG_DD = "draw-date";
    private static final String TAG_NDD = "next-draw-date";
    private static final String TAG_SL = "swisslotto";
    private static final String TAG_JP = "jackpot";
    private static final String TAG_NRS = "numbers";
    private static final String TAG_NR = "number";
    private static final String TAG_LN = "luckynumber";
    private static final String TAG_RN = "replay-number";

    public ContentValues parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readXml(parser);
        } finally {
            in.close();
        }
    }

    private ContentValues readXml(XmlPullParser parser) throws IOException, XmlPullParserException {
        ContentValues contentValues = new ContentValues();

        parser.require(XmlPullParser.START_TAG, ns, TAG_ROOT);
        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if (parser.getName().equals(TAG_DD)) {
                readText(parser);
            } else if (parser.getName().equals(TAG_NDD)) {
                readText(parser);
            } else if (parser.getName().equals(TAG_JP)) {
                readText(parser);
            } else if (parser.getName().equals(TAG_LN)){
                readText(parser);
            } else {
                skip(parser);
            }

        }
        return null;
    }


    private String readDrawDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_DD);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_DD);
        return value;
    }

    private String readNextDrawDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_NDD);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_NDD);
        return value;
    }

    private String readSwissLotto(XmlPullParser parser) {
        return null;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
            Log.v("SLB", "Data: "+result);
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
