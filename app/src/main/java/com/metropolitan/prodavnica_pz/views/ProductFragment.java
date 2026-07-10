package com.metropolitan.prodavnica_pz.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metropolitan.prodavnica_pz.R;
import com.metropolitan.prodavnica_pz.databinding.FragmentProductBinding;
import com.metropolitan.prodavnica_pz.viewmodels.ShopViewmodel;

public class ProductFragment extends Fragment {

    FragmentProductBinding fragmentProductBinding;
    ShopViewmodel shopViewmodel;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProductBinding = FragmentProductBinding.inflate(inflater, container, false);
        return fragmentProductBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopViewmodel = new ViewModelProvider(requireActivity()).get(ShopViewmodel.class);
        fragmentProductBinding.setShopViewmodel(shopViewmodel);
    }
}