package com.metropolitan.prodavnica_pz.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metropolitan.prodavnica_pz.R;
import com.metropolitan.prodavnica_pz.databinding.FragmentStartBinding;


public class StartFragment extends Fragment {

    FragmentStartBinding fragmentStartBinding;
    NavController navController;

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentStartBinding = FragmentStartBinding.inflate(inflater, container, false);
        return fragmentStartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        fragmentStartBinding.kolaciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_startFragment_to_kolacFragment);
            }
        });

        fragmentStartBinding.kolacImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_startFragment_to_kolacFragment);
            }
        });

        fragmentStartBinding.torteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_startFragment_to_tortaFragment);
            }
        });

        fragmentStartBinding.tortaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_startFragment_to_tortaFragment);
            }
        });
    }
}