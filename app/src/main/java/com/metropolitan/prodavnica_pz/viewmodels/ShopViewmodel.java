package com.metropolitan.prodavnica_pz.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.metropolitan.prodavnica_pz.models.CartItem;
import com.metropolitan.prodavnica_pz.models.Product;
import com.metropolitan.prodavnica_pz.repository.CartRepository;
import com.metropolitan.prodavnica_pz.repository.KolacRepository;
import com.metropolitan.prodavnica_pz.repository.TortaRepository;

import java.util.List;

public class ShopViewmodel extends ViewModel {

    KolacRepository kolacRepository = new KolacRepository();
    TortaRepository tortaRepository = new TortaRepository();
    CartRepository cartRepository = new CartRepository();

    MutableLiveData<Product> mutableProduct = new MutableLiveData<>();

    public LiveData<List<Product>> getKolace() {
        return kolacRepository.getKolace();
    }

    public LiveData<List<Product>> getTorte() {
        return tortaRepository.getTorte();
    }

    public void setProduct(Product product) {
        mutableProduct.setValue(product);
    }

    public LiveData<Product> getProduct() {
        return mutableProduct;
    }

    public LiveData<List<CartItem>> getCart() {
        return cartRepository.getCart();
    }

    public boolean addItemToCart(Product product) {
        return cartRepository.addItemToCart(product);
    }

    public void removeItemFromCart(CartItem cartItem) {
        cartRepository.removeItemFromCart(cartItem);
    }

    public void promeniKolicinu(CartItem cartItem, int kolicina) {
        cartRepository.promeniKolicinu(cartItem, kolicina);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepository.getTotalPrice();
    }

    public void resetCart() {
        cartRepository.initCart();
    }
}
