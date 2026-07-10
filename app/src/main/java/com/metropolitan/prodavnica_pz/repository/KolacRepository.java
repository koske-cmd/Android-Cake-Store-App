package com.metropolitan.prodavnica_pz.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.metropolitan.prodavnica_pz.models.Product;
import com.metropolitan.prodavnica_pz.views.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class KolacRepository {

    private MutableLiveData<List<Product>> mutableProductList;

    public LiveData<List<Product>> getKolace() {
        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            new LoadKolaciTask().execute();
        }
        return mutableProductList;
    }

    private class LoadKolaciTask extends AsyncTask<Void, Void, List<Product>> {

        Context context = MainActivity.getAppContext();                                             // Potreban context za lokaciju slike sa uređaja

        @Override
        protected List<Product> doInBackground(Void... voids) {                                     // Metoda za preuzimanje podataka koja se izvršava u pozadini
            List<Product> productList = new ArrayList<>();
            try {
                URL url = new URL("https://api.npoint.io/7a7f97b455e701512449");               // Lokacija JSON fajla za listu kolača
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                Log.d("TAG", "Status konekcije: " + httpURLConnection.getResponseCode());
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                String data = "";

                while ((line = bufferedReader.readLine()) != null) {
                    data = data + line;
                }

                if (!data.isEmpty()) {                                                              // Čitanje iz JSON fajla i postavljanje podataka u objekat
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("Kolaci");
                    productList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("name");
                        double price = object.getDouble("price");
                        boolean availability = object.getBoolean("availability");
                        String description = object.getString("description");
                        int imageId = context.getResources().getIdentifier(object.getString("imageId"), "drawable", context.getPackageName());
                        Log.d("imageId", String.valueOf(imageId));

                        productList.add(new Product(name, price, availability, imageId, description)); // Dodavanje objekata u listu
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return productList;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            mutableProductList.setValue(products);
        }
    }
}
