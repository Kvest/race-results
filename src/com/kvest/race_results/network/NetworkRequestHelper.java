package com.kvest.race_results.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 22:46
 * To change this template use File | Settings | File Templates.
 */
public class NetworkRequestHelper {
    private static final String TAG = "NetworkRequestHelper";
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 120000;

    public static final int LOAD_FORMAT_JSON = 0;
    public static final int LOAD_FORMAT_XML= 1;
    public static final int LOAD_FORMAT_TXT = 2;

    public static String loadRaceResults(int format) {
        //get url
        String url = null;
        switch (format) {
            case LOAD_FORMAT_JSON :
                url = Urls.LOAD_RESULTS_JSON_URL;
                break;
            case LOAD_FORMAT_XML :
                url = Urls.LOAD_RESULTS_XML_URL;
                break;
            case LOAD_FORMAT_TXT :
                url = Urls.LOAD_RESULTS_TXT_URL;
                break;
            default : return null;
        }

        //make request
        return get(url);
    }

    private static String get(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return readStreamAsString(conn.getInputStream());
            }
        } catch (Exception e) {
            Log.e(TAG, "get error " + e + "(" + urlString + ")");
        }
        return null;
    }

    private static String readStreamAsString(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
}
