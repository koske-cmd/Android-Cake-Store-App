package com.metropolitan.prodavnica_pz.repository;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.module.AppGlideModule;
import com.google.gson.Gson;
import com.metropolitan.prodavnica_pz.R;
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
import java.util.UUID;

public class TortaRepository {

    private MutableLiveData<List<Product>> mutableProductList;

    public LiveData<List<Product>> getTorte() {
        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            new LoadTorteTask().execute();
        }
        return mutableProductList;
    }

    private class LoadTorteTask extends AsyncTask<Void, Void, List<Product>> {

        Context context = MainActivity.getAppContext();                                             // Potreban context za lokaciju slike sa uređaja

        @Override
        protected List<Product> doInBackground(Void... voids) {                                     // Metoda za preuzimanje podataka koja se izvršava u pozadini
            List<Product> productList = new ArrayList<>();
            try {
                URL url = new URL("https://api.npoint.io/35af05cf1bed1ccf7829");               // Lokacija JSON fajla za listu torti
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
                    JSONArray jsonArray = jsonObject.getJSONArray("Torte");
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
