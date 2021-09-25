package com.example.uno.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.database.Firestore;
import com.example.uno.databinding.CardLayoutBinding;
import com.example.uno.helpers.Utils;
import com.example.uno.models.Game;
import com.example.uno.models.User;

import java.util.ArrayList;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.UViewHolder> {

    ArrayList<String> deck;

    CardLayoutBinding binding;

    IDeckAdapter am;

    Firestore db;

    User user;

    public DeckAdapter(ArrayList<String> deck) {
        this.deck = deck;
    }

    @NonNull
    @Override
    public UViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        am = (IDeckAdapter) parent.getContext();
        return new UViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UViewHolder holder, int position) {
        String card = deck.get(position);

        user = am.getUser();
        db = am.getDb();
        Game game = user.getGame();

        binding.textView4.setText(Utils.getCardDisplay(card));
        binding.textView4.setTextColor(Color.parseColor(Utils.getCardColor(card)));

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // playing their turn, write game logic
            }
        });

    }

    public interface IDeckAdapter {

        User getUser();

        Firestore getDb();

    }

    @Override
    public int getItemCount() {
        return this.deck.size();
    }


    public static class UViewHolder extends RecyclerView.ViewHolder {

        CardLayoutBinding binding;

        public UViewHolder(@NonNull CardLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
