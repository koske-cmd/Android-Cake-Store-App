package com.metropolitan.prodavnica_pz.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metropolitan.prodavnica_pz.R;
import com.metropolitan.prodavnica_pz.adapters.CartListAdapter;
import com.metropolitan.prodavnica_pz.databinding.FragmentCartBinding;
import com.metropolitan.prodavnica_pz.models.CartItem;
import com.metropolitan.prodavnica_pz.viewmodels.ShopViewmodel;

import java.util.List;

public class CartFragment extends Fragment implements CartListAdapter.CartInterface {

    ShopViewmodel shopViewmodel;
    FragmentCartBinding fragmentCartBinding;
    NavController navController;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        CartListAdapter cartListAdapter = new CartListAdapter(this);
        fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
        fragmentCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        shopViewmodel = new ViewModelProvider(requireActivity()).get(ShopViewmodel.class);
        shopViewmodel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartListAdapter.submitList(cartItems);
                fragmentCartBinding.placeOrderButton.setEnabled(cartItems.size() > 0);              // Omogućuje Naruči button kada korpa sadrži više od 0 proizvoda
            }
        });

        shopViewmodel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {     // Observer na ukupnu cenu
            @Override
            public void onChanged(Double aDouble) {
                fragmentCartBinding.orderTotalTextView.setText("Ukupno: " + String.format("%.2f", aDouble) + " din.");
            }
        });

        fragmentCartBinding.placeOrderButton.setOnClickListener(new View.OnClickListener() {        // Listener na Naruči button koji pokreće intent za slanje email-a sa podacima o narudžbini
            @Override
            public void onClick(View v) {
                List<CartItem> narudzbe = shopViewmodel.getCart().getValue();
                double ukupnaCena = shopViewmodel.getTotalPrice().getValue();
                StringBuilder message = new StringBuilder();
                message.append("Naručeno: ");
                for (CartItem item : narudzbe) {
                    message.append(item.getProduct().getName() + " " + item.getKolicina() + " kom " + item.getProduct().getPrice() + " din.\n");
                }
                message.append("Ukupna cena: " + ukupnaCena + " din.");
                String[] emailAdresses = {"srdjan.kosanovic83@gmail.com"};
                String[] carbonCopies = null;
                String subject = "Narudžbenica";
                sendEmail(emailAdresses, carbonCopies, subject, message.toString());

                navController.navigate(R.id.action_cartFragment_to_orderFragment);
            }
        });
    }

    private void sendEmail(String[] emailAdresses, String[] carbonCopies, String subject, String message) {// Metoda za slanje email-a
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        String[] to = emailAdresses;
        String[] cc = carbonCopies;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email"));
    }

    @Override
    public void deleteItem(CartItem cartItem) {
        shopViewmodel.removeItemFromCart(cartItem);
    }

    @Override
    public void promeniKolicinu(CartItem cartItem, int kolicina) {
        shopViewmodel.promeniKolicinu(cartItem, kolicina);
    }
}
