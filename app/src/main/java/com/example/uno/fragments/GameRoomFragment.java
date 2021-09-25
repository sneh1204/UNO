package com.example.uno.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.uno.MainActivity;
import com.example.uno.R;
import com.example.uno.adapters.DeckAdapter;
import com.example.uno.database.Firestore;
import com.example.uno.databinding.FragmentGameRoomBinding;
import com.example.uno.databinding.FragmentLobbyBinding;
import com.example.uno.helpers.Utils;
import com.example.uno.models.Game;
import com.example.uno.models.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.HashMap;

public class GameRoomFragment extends Fragment {

    FragmentGameRoomBinding binding;

    IGameRoom am;

    NavController navController;

    User user;

    Game game;

    Firestore db;

    DocumentReference dbref;

    ColorStateList oldcolor;

    ListenerRegistration listener;

    public interface IGameRoom{

        User getUser();

        Firestore getDb();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IGameRoom) {
            am = (IGameRoom) context;
        } else {
            throw new RuntimeException(context.toString());
        }
        db = am.getDb();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(game == null)    navController.popBackStack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Game Room");
        binding = FragmentGameRoomBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        user = am.getUser();
        game = user.getGame();
        navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView2);
        if(game == null)    navController.popBackStack();
        dbref = db.firestore.collection(Firestore.DB_GAME).document(game.getId());

        binding.deckView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.deckView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.deckView.getContext(),
                llm.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.space_rect));
        binding.deckView.addItemDecoration(dividerItemDecoration);

        oldcolor = binding.player1.getTextColors();

        binding.player1.setText(game.getPlayer1().getFirstname());
        binding.player2.setText(game.getPlayer2().getFirstname());

        Utils.setImage(view, binding.imageView4, game.getPlayer1().getId(), game.getPlayer2().getPhotoref());
        Utils.setImage(view, binding.imageView5, game.getPlayer2().getId(), game.getPlayer2().getPhotoref());

        listener = dbref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value == null) {
                    return;
                }
                game = value.toObject(Game.class);
                game.setId(value.getId());

                if(!game.isActive()){
                    listener.remove();
                }

                if(game.isPlayer1Turn()){
                    if(game.isPlayer1(user)) binding.textView8.setText("Your Turn");
                    else binding.textView8.setText("Their Turn");

                    binding.player2.setText(game.getPlayer2().getFirstname());
                    binding.player1.setTextColor(Color.parseColor(Utils.COLOR_ACTIVE));
                    binding.player2.setTextColor(oldcolor);
                } else{
                    if(!game.isPlayer1(user)) binding.textView8.setText("Your Turn");
                    else binding.textView8.setText("Their Turn");

                    binding.player1.setText(game.getPlayer1().getFirstname());
                    binding.player2.setTextColor(Color.parseColor(Utils.COLOR_ACTIVE));
                    binding.player1.setTextColor(oldcolor);
                }

                binding.include.textView4.setTextColor(Color.parseColor(Utils.getCardColor(game.getTopCard())));
                binding.include.textView4.setText(Utils.getCardDisplay(game.getTopCard()));
                if(Utils.isSkip(game.getTopCard()))  binding.include.textView4.setTextSize(30);
                binding.include.imageView6.setImageDrawable(ContextCompat.getDrawable(getActivity(), Utils.getCardDrawable(game.getTopCard())));

                if(game.isPlayer1(user)){
                    binding.deckView.setAdapter(new DeckAdapter(game.getPlayer1hand()));
                }else{
                    binding.deckView.setAdapter(new DeckAdapter(game.getPlayer2hand()));
                }
            }
        });

        return view;
    }
}