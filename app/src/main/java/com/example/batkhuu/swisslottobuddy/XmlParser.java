package com.example.batkhuu.swisslottobuddy;


import android.content.ContentValues;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    private static final String TAG_WRS = "win-ranks";
    private static final String TAG_WR = "win-rank";
    private static final String TAG_AM = "amount";
    private static final String TAG_WCI = "win-class-index";

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

    // reads everything under root-tag
    // for deeper level this method calls another method
    // at the end this method returns a ContentValues-Object
    private ContentValues readXml(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_ROOT);
        ContentValues result = new ContentValues();

        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if (parser.getName().equals(TAG_DD)) {
                result.put("draw_date", readDrawDate(parser));
            } else if (parser.getName().equals(TAG_NDD)) {
                result.put("next_draw_date", readNextDrawDate(parser));
            } else if (parser.getName().equals(TAG_SL)) {
                ContentValues sl = readSwissLotto(parser);
                result.put(TAG_JP, sl.getAsString(TAG_JP));
                result.put(TAG_NR+0, sl.getAsString(TAG_NR+0));
                result.put(TAG_NR+1, sl.getAsString(TAG_NR+1));
                result.put(TAG_NR+2, sl.getAsString(TAG_NR+2));
                result.put(TAG_NR+3, sl.getAsString(TAG_NR+3));
                result.put(TAG_NR+4, sl.getAsString(TAG_NR+4));
                result.put(TAG_NR+5, sl.getAsString(TAG_NR+5));
                result.put(TAG_LN, sl.getAsString(TAG_LN));
                result.put("replay_number", sl.getAsString(TAG_RN));
                result.put("win_class_index0", sl.getAsString(TAG_WCI+0));
                result.put("win_class_index1", sl.getAsString(TAG_WCI+1));
                result.put("win_class_index2", sl.getAsString(TAG_WCI+2));
                result.put("win_class_index3", sl.getAsString(TAG_WCI+3));
                result.put("win_class_index4", sl.getAsString(TAG_WCI+4));
                result.put("win_class_index5", sl.getAsString(TAG_WCI+5));
                result.put("win_class_index6", sl.getAsString(TAG_WCI+6));
                result.put("win_class_index7", sl.getAsString(TAG_WCI+7));
            } else {
                skip(parser);
            }
        }
        return result;
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

    private ContentValues readSwissLotto(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_SL);
        ContentValues result = new ContentValues();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if (parser.getName().equals(TAG_JP)) {
                result.put(TAG_JP, readJackpot(parser));
            } else if (parser.getName().equals(TAG_NRS)) {
                List<String> numbers = readNumbers(parser);
                for (int i = 0; i < numbers.size(); i++) {
                    result.put(TAG_NR+i, numbers.get(i));
                }
            } else if (parser.getName().equals(TAG_LN)) {
                result.put(TAG_LN, readLuckyNumber(parser));
            } else if (parser.getName().equals(TAG_RN)) {
                result.put(TAG_RN, readReplayNumber(parser));
            } else if (parser.getName().equals(TAG_WRS)) {
                List<String> wrs = readWinRanks(parser);
                for (int i = 0; i < wrs.size(); i++) {
                    result.put(TAG_WCI+i, wrs.get(i));
                }
            } else {
                skip(parser);
            }
        }
        return result;
    }

    private String readJackpot(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_JP);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_JP);
        return value;
    }

    private String readLuckyNumber(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_LN);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_LN);
        return value;
    }

    private String readReplayNumber(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_RN);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_RN);
        return value;
    }

    private List<String> readNumbers(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_NRS);
        List<String> result = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if (parser.getName().equals(TAG_NR)) {
                result.add(readNumber(parser));
            } else {
                skip(parser);
            }
        }
        return result;
    }

    private String readNumber(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_NR);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_NR);
        return value;
    }

    private List<String> readWinRanks(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_WRS);
        List<String> result = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if (parser.getName().equals(TAG_WR)) {
                List<String> winrank;
                winrank = readWinRank(parser);
                int pos = Integer.parseInt(winrank.get(1));
                String wr = winrank.get(0);
                result.add(pos, wr);
            } else {
                skip(parser);
            }
        }
        return result;
    }

    private List<String> readWinRank(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_WR);
        List<String> result = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if (parser.getName().equals(TAG_AM)) {
                String am = readAmount(parser).replaceAll("'","");
                result.add(0, am);
            } else if (parser.getName().equals(TAG_WCI)) {
                result.add(1, readWinClassIndex(parser));
            } else {
                skip(parser);
            }
        }
        return result;
    }

    private String readAmount(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_AM);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_AM);
        return value;
    }

    private String readWinClassIndex(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_WCI);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_WCI);
        return value;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
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
