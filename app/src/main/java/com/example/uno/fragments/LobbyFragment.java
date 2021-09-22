package com.example.uno.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uno.database.Firestore;
import com.example.uno.databinding.FragmentLobbyBinding;


public class LobbyFragment extends Fragment {

    FragmentLobbyBinding binding;

    ILobby am;

    Firestore db;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ILobby) {
            am = (ILobby) context;
        } else {
            throw new RuntimeException(context.toString());
        }
        db = am.getDb();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Game Lobby");

        binding = FragmentLobbyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    interface ILobby{
        void alert(String msg);
        Firestore getDb();
    }
}