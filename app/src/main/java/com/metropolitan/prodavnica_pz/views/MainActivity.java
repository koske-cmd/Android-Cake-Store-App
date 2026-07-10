package com.metropolitan.prodavnica_pz.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.metropolitan.prodavnica_pz.R;
import com.metropolitan.prodavnica_pz.models.CartItem;
import com.metropolitan.prodavnica_pz.viewmodels.ShopViewmodel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    ShopViewmodel shopViewmodel;
    private int cartKolicina = 0;
    private static Context context;                                                                 // Context, (kasnije potreban u Repository)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();                                             // Dobijanje contexta na nivou aplikacije

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView3);
        navController = navHostFragment.getNavController();                                         // Postavljanje navigacije u activity_main fragment kontejner
        NavigationUI.setupActionBarWithNavController(this, navController);                   // Postavlja UI navigacije tako da nazivi fragmenata iz my_nav budu u zaglavlju aplikacije

        shopViewmodel = new ViewModelProvider(this).get(ShopViewmodel.class);
        shopViewmodel.getCart().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                int kolicina = 0;
                for (CartItem cartItem : cartItems) {
                    kolicina += cartItem.getKolicina();
                }
                cartKolicina = kolicina;
                invalidateOptionsMenu();
            }
        });
    }

    public static Context getAppContext() {                                                         // Metoda koja vraća context aplikacije
        return MainActivity.context;
    }

    @Override
    public boolean onSupportNavigateUp() {                                                          // Omogućava vraćanje nazad pritiskom na strelicu nazad na toolbaru
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                                                 // Dodaje my_menu
        getMenuInflater().inflate(R.menu.my_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.cartFragment);
        View actionView = menuItem.getActionView();
        TextView cartBadgeTextView = actionView.findViewById(R.id.cartBadgeTextView);
        cartBadgeTextView.setText(String.valueOf(cartKolicina));
        cartBadgeTextView.setVisibility(cartKolicina == 0 ? View.GONE : View.VISIBLE);              // Ako je broj proizvoda u korpi 0, onemogućuje prikazivanje bedža

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nasaLokacija) {                                                              // Menu opcija Nasa lokacija koja prikazuje lokaciju na adresi u webbrowseru
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/%D0%9A%D0%B0%D1%84%D0%B5+%D1%81%D0%BB%D0%B0%D1%81%D1%82%D0%B8%D1%87%D0%B0%D1%80%D0%BD%D0%B0+%D0%9C%D0%90%D0%8A%D0%90/@44.7697205,17.1890063,18.75z/data=!4m5!3m4!1s0x0:0xcf382b120cc9d0ec!8m2!3d44.7697048!4d17.1888319?hl=sr"));
            startActivity(browserIntent);
            return true;
        } else if (id == R.id.cartFragment) {
            return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}




















