package com.metropolitan.prodavnica_pz.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.metropolitan.prodavnica_pz.R;
import com.metropolitan.prodavnica_pz.adapters.ProductListAdapter;
import com.metropolitan.prodavnica_pz.databinding.FragmentTortaBinding;
import com.metropolitan.prodavnica_pz.models.Product;
import com.metropolitan.prodavnica_pz.viewmodels.ShopViewmodel;

import java.util.List;

public class TortaFragment extends Fragment implements ProductListAdapter.ProductInterface {

    FragmentTortaBinding fragmentTortaBinding;
    private NavController navController;
    private ProductListAdapter productListAdapter;
    private ShopViewmodel shopViewmodel;
    private ProgressBar progressBar;                                                                // Novija verzija ProgressDialog-a
    private TextView ucitavanjeTortaTextView;

    public TortaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTortaBinding = FragmentTortaBinding.inflate(inflater, container, false);
        return fragmentTortaBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = fragmentTortaBinding.tortaProgressBar;                                        // ProgressBar tortaProgressBar
        ucitavanjeTortaTextView = fragmentTortaBinding.ucitavanjeTortaTextView;                     // TextView ucitavanjeTortaTextView
        productListAdapter = new ProductListAdapter(this);
        fragmentTortaBinding.tortaRecyclerView.setAdapter(productListAdapter);

        shopViewmodel = new ViewModelProvider(requireActivity()).get(ShopViewmodel.class);
        shopViewmodel.getTorte().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                ucitavanjeTortaTextView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);                                               // Gasi progresBar kada se popuni lista
                productListAdapter.submitList(products);
            }
        });

        navController = Navigation.findNavController(view);
    }

    @Override
    public void addItem(Product product) {                                                          // Dodaje proizvod iz ovog fragmenta u korpu
        boolean isAdded = shopViewmodel.addItemToCart(product);
        if (isAdded) {
            Snackbar.make(requireView(), product.getName() + " torta dodata u korpu", Snackbar.LENGTH_SHORT)
                    .setAction("Korpa", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {                                               // Snackbar koji sadrži onClick metodu koja vas šalje u korpu
                            navController.navigate(R.id.action_tortaFragment_to_cartFragment);
                        }
                    }).show();
        } else {
            Snackbar.make(requireView(), "Maksimalno torti je dodato", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Product product) {                                                      // Metoda koja na klik proizvoda aktivira fragment proizvoda za kliknuti proizvod
        shopViewmodel.setProduct(product);
        navController.navigate(R.id.action_tortaFragment_to_productFragment);
    }
}