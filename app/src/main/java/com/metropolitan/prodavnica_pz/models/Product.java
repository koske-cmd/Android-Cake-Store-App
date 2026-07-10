package com.metropolitan.prodavnica_pz.models;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;

import java.util.UUID;

public class Product {

    private String id;
    private String name;
    private double price;
    private boolean isAvailable;
    private int imageId;
    private String description;

    public Product(String name, double price, boolean isAvailable, int imageId, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imageId = imageId;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {                                                                      // Metoda toString za eventualno debugovanje ili logovanje
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", imageId=" + imageId +
                '}';
    }

    @Override
    public boolean equals(Object o) {                                                               // Metoda za poređenje dva proizvoda
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.getPrice(), getPrice()) == 0 && isAvailable() == product.isAvailable() && getImageId() == product.getImageId() && getId().equals(product.getId()) && getName().equals(product.getName());
    }

    public static DiffUtil.ItemCallback<Product> itemCallback = new DiffUtil.ItemCallback<Product>() {// Ugrađena klasa koja poredi stare i nove objekte radi automatskog ažuriranja u recycler view
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:productImage")
    public static void loadImage(ImageView imageView, int imageId) {                                // Metoda koja učitava sliku, koristi GLide biblioteku. Ovo je adapter koji se koristi da se poveže polje slike sa odgovarajućom ImageView kontrolom
        Glide.with(imageView)
                .load(imageId)
                .fitCenter()
                .into(imageView);
    }
}


















