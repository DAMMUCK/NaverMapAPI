package com.kunsan.ac.kr.mapsosua;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AsyncTaskThread extends AsyncTask<URL,Void,JSONObject> {
    @Override
    protected JSONObject doInBackground(URL...params){
        HttpURLConnection con = null;
        try{
            con = (HttpURLConnection)params[0].openConnection();
            int response = con.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK){
                StringBuilder builder = new StringBuilder();
                try(BufferedReader reader = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))){
                    String line;
                    while((line = reader.readLine())!= null){
                        builder.append(line);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                return new JSONObject(builder.toString());
            }else{
                Log.e(TAG,"Connection Error!");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject){

    }
    /*@Override
    protected String doInBackground(String...strCoord) {

        //String strCoord = String.valueOf(latLngs[0].longitude) + "," + String.valueOf(latLngs[0].latitude);
        //StringBuilder sb = new StringBuilder();

        StringBuilder urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords=" +strCoord+ "&sourcecrs=epsg:4326&output=json&orders=addr"); *//* URL *//*



        StringBuffer page = null;
        BufferedReader bufreader = null;
        try {
            URL url = new URL(urlBuilder.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "d6nvpc15uv");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", "fzGdGvib2YvAL8I3KAnS4PGCubwhLNsq8yHIO8o5");
            InputStream contentStream = conn.getInputStream();

            bufreader = new BufferedReader(new InputStreamReader(contentStream, "UTF-8"));
            page = new StringBuffer();
            String line = null;

            while ((line = bufreader.readLine()) != null) {
                Log.d("line:", line);
                page.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                bufreader.close();
                //conn.disconnect();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return page.toString();
    }*/
}
