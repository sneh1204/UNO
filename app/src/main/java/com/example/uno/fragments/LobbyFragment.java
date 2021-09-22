package com.example.uno.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.uno.databinding.FragmentLobbyBinding;


public class LobbyFragment extends Fragment {

    FragmentLobbyBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Game Lobby");

        binding = FragmentLobbyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}