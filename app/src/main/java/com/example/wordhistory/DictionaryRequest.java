package com.example.wordhistory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DictionaryRequest extends AsyncTask<String, Integer, String> {

    Context context;
    TextView history;

    DictionaryRequest(Context context, TextView tV)
    {
        this.context = context;
        history = tV;
    }
    @Override
    protected String doInBackground(String... params) {

        final String app_id = "6aeaa784";
        final String app_key = "1d53f14550adbaffbfc015fb48822c1e";
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        String origin;
        try {
            JSONObject js = new JSONObject(result);
            JSONArray results = js.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray laArray = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = laArray.getJSONObject(0);
            JSONArray e = entries.getJSONArray("entries");

            JSONObject de = e.getJSONObject(0);
            JSONArray d = de.getJSONArray("etymologies");

            origin = d.getString(0);
            history.setText(origin);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("DictionaryRequest","OnPostExecute:" +result);
    }

}
