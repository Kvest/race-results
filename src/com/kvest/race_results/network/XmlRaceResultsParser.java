package com.kvest.race_results.network;

import android.util.Xml;
import com.kvest.race_results.datamodel.RaceResult;
import com.kvest.race_results.datamodel.RunnerResult;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: roman
 * Date: 1/13/14
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlRaceResultsParser implements RaceResultsParser {
    private static final String RACE_TAG = "Race";
    private static final String RACE_NAME_TAG = "Name";
    private static final String RUNNERS_TAG = "Runners";
    private static final String RUNNER_TAG = "Runner";
    private static final String NAME_TAG = "Name";
    private static final String TIME_TAG = "Time";
    private static final String AGE_TAG = "Age";

    @Override
    public RaceResult parse(String rawData) {
        XmlPullParser parser = Xml.newPullParser();
        try
        {
            RaceResult result = new RaceResult();

            parser.setInput(new StringReader(rawData));

            //go to root tag
            parser.next();
            parser.require(XmlPullParser.START_TAG, "", RACE_TAG);

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();

                    if (tagName.equals(RACE_NAME_TAG)) {
                        parseRaceName(parser, result);
                    } else if (tagName.equals(RUNNERS_TAG)) {
                        parseRunners(parser, result);
                    }
                }
            }

            return result;
        } catch(Exception ex) {
            return null;
        }
    }

    private void parseRaceName(XmlPullParser parser, RaceResult result) throws XmlPullParserException, IOException {
        result.Name = getText(parser);
    }

    private void parseRunners(XmlPullParser parser, RaceResult result) throws XmlPullParserException, IOException {
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                parser.require(XmlPullParser.START_TAG, "", RUNNER_TAG);

                result.Runners.add(parseRunner(parser));
            }
        }
    }

    private RunnerResult parseRunner(XmlPullParser parser) throws XmlPullParserException, IOException {
        RunnerResult result = new RunnerResult();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                String tagName = parser.getName();

                if (tagName.equals(NAME_TAG)) {
                    result.Name = getText(parser);
                } else if (tagName.equals(TIME_TAG)) {
                    result.Time = getInt(parser);
                } else if (tagName.equals(AGE_TAG)) {
                    result.Age = getInt(parser);
                }
            }
        }

        return result;
    }

    private static String getText(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        //Go to Text
        parser.next();

        //Get text
        String result = parser.getText();

        //if text is null - set to empty string
        if (result == null) {
            result = "";
        }

        //Go to next tag
        if (parser.getEventType() != XmlPullParser.END_TAG) {
            parser.next();
        }

        return result;
    }

    private static int getInt(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        //Go to Text
        parser.next();

        //Get int as text
        String resultStr = parser.getText();

        int result;
        //convert
        try {
            result = Integer.parseInt(resultStr);
        } catch(Exception ex) {
            result = 0;
        }

        //Go to next tag
        if (parser.getEventType() != XmlPullParser.END_TAG) {
            parser.next();
        }

        return result;
    }
}
