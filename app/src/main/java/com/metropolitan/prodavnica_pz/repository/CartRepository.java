package com.metropolitan.prodavnica_pz.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.metropolitan.prodavnica_pz.models.CartItem;
import com.metropolitan.prodavnica_pz.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();
    private MutableLiveData<Double> mutableTotalPrice = new MutableLiveData<>();

    public LiveData<List<CartItem>> getCart() {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        return mutableCart;
    }

    public void initCart() {
        mutableCart.setValue(new ArrayList<CartItem>());
        calculateCartTotal();
    }

    public boolean addItemToCart(Product product) {                                                 // Metoda koja dodaje proizvod u korpu
        if (mutableCart.getValue() == null) {
            initCart();
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                if (cartItem.getKolicina() == 50) {
                    return false;
                }
                int index = cartItemList.indexOf(cartItem);
                cartItem.setKolicina(cartItem.getKolicina() + 1);
                cartItemList.set(index, cartItem);

                mutableCart.setValue(cartItemList);
                calculateCartTotal();
                return true;
            }
        }
        CartItem cartItem = new CartItem(product, 1);
        cartItemList.add(cartItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
        return true;
    }

    public void removeItemFromCart(CartItem cartItem) {                                             // Metoda koja uklanja proizvod iz korpe
        if (mutableCart.getValue() == null) {
            return;
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        cartItemList.remove(cartItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
    }

    public void promeniKolicinu(CartItem cartItem, int kolicina) {                                  // Metoda koja menja količinu proizvoda u korpi
        if (mutableCart.getValue() == null) {
            return;
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        CartItem updatedItem = new CartItem(cartItem.getProduct(), kolicina);
        cartItemList.set(cartItemList.indexOf(cartItem), updatedItem);

        mutableCart.setValue(cartItemList);
        calculateCartTotal();
    }

    public LiveData<Double> getTotalPrice() {                                                       // Metoda koja vraća ukupnu cenu svih proizvoda
        if (mutableTotalPrice.getValue() == null) {
            mutableTotalPrice.setValue(0.0);
        }
        return mutableTotalPrice;
    }

    private void calculateCartTotal() {                                                             // Metoda za računanje ukupne cene proizvoda
        if (mutableCart.getValue() == null) {
            return;
        }
        double total = 0.0;
        List<CartItem> cartItemList = mutableCart.getValue();
        for (CartItem cartItem : cartItemList) {
            total += cartItem.getProduct().getPrice() * cartItem.getKolicina();
        }
        mutableTotalPrice.setValue(total);
    }
}








