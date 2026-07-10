package com.metropolitan.prodavnica_pz.models;

import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class CartItem {

    private Product product;
    private int kolicina;

    public CartItem(Product product, int kolicina) {                                                // Konstruktor sa parametrima
        this.product = product;
        this.kolicina = kolicina;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    @Override
    public String toString() {                                                                      // Metoda toString za eventualno debugovanje ili logovanje
        return "CartItem{" +
                "product=" + product +
                ", kolicina=" + kolicina +
                '}';
    }

    @Override
    public boolean equals(Object o) {                                                               // Metoda za poređenje dva proizvoda
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return getKolicina() == cartItem.getKolicina() && getProduct().equals(cartItem.getProduct());
    }

    public static DiffUtil.ItemCallback<CartItem> itemCallback = new DiffUtil.ItemCallback<CartItem>() { // Ugrađena klasa koja poredi stare i nove objekte radi automatskog ažuriranja u recycler view
        @Override
        public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getKolicina() == newItem.getKolicina();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:setVal")
    public static void getSelectedSpinnerValue(Spinner spinner, int kolicina) {                     // Binder za podešavanje količine proizvoda na spiner
        spinner.setSelection(kolicina - 1, true);
    }
}































