package com.metropolitan.prodavnica_pz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.prodavnica_pz.databinding.CartRowBinding;
import com.metropolitan.prodavnica_pz.models.CartItem;

public class CartListAdapter extends ListAdapter<CartItem, CartListAdapter.CartViewHolder> {

    private CartInterface cartInterface;

    public CartListAdapter(CartInterface cartInterface) {
        super(CartItem.itemCallback);
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CartRowBinding cartRowBinding = CartRowBinding.inflate(layoutInflater, parent, false);
        return new CartViewHolder(cartRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.cartRowBinding.setCartItem(getItem(position));
        holder.cartRowBinding.executePendingBindings();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        CartRowBinding cartRowBinding;

        public CartViewHolder(@NonNull CartRowBinding cartRowBinding) {
            super(cartRowBinding.getRoot());
            this.cartRowBinding = cartRowBinding;

            cartRowBinding.deleteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartInterface.deleteItem(getItem(getAdapterPosition()));
                }
            });

            cartRowBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int kolicina = position + 1;
                    if (kolicina == getItem(getAdapterPosition()).getKolicina()) {
                        return;
                    }
                    cartInterface.promeniKolicinu(getItem(getAdapterPosition()), kolicina);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public interface CartInterface {
        void deleteItem(CartItem cartItem);

        void promeniKolicinu(CartItem cartItem, int kolicina);
    }
}
