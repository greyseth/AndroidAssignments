package com.example.schoolstuff.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {
    //Unused class

    public Optional<String> postReq(String m_url, HashMap<String, String> params) {
        try {
            //Creates URL connection
            URL url = new URL(m_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Connection configuration, basic shit
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Gets request output
            OutputStream output = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
            writer.write(getPostDataString(params));
            writer.flush();
            writer.close();
            output.close();

            //Handles the output
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_ACCEPTED) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder str = new StringBuilder();
                String response;
                while((response = reader.readLine()) != null) str.append(response);

                return Optional.of(str.toString());
            }else return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder res = new StringBuilder();
        boolean firstParam = true;
        for (Map.Entry<String, String> p : params.entrySet()) {
            if(firstParam) firstParam = false;
            else res.append("&");

            res.append(URLEncoder.encode(p.getKey(), "UTF-8"));
            res.append("=");
            res.append(URLEncoder.encode(p.getValue(), "UTF-8"));
        }

        return res.toString();
    }

    public String getRequest(String m_url) {
        StringBuilder str = new StringBuilder();

        try {
            //Initializes connection to url
            URL url = new URL(m_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Reads stuff and stuff
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String s;
            while((s = reader.readLine()) != null) str.append(s+"\n");
        }catch(Exception e) {
            e.printStackTrace();
        }

        return str.toString();
    }

    public String getRequest(String m_url, String id) {
        StringBuilder str = new StringBuilder();

        try {
            //Initializes connection to url
            URL url = new URL(m_url+id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Reads stuff and stuff
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String s;
            while((s = reader.readLine()) != null) str.append(s+"\n");
        }catch(Exception e) {
            e.printStackTrace();
        }

        return str.toString();
    }
}
