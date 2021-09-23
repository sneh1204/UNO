package com.example.uno.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.uno.R;
import com.example.uno.adapters.RequestsAdapter;
import com.example.uno.database.Firestore;
import com.example.uno.databinding.FragmentLobbyBinding;
import com.example.uno.models.Request;
import com.example.uno.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class LobbyFragment extends Fragment {

    FragmentLobbyBinding binding;

    ILobby am;

    private static final int SECS = 30;

    Firestore db;
    private final int interval = 1000 * SECS;
    NavController navController;
    AlertDialog dialog;
    User user;
    Runnable runnable;
    private int count = SECS;
    private Handler handler = new Handler();

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
    public void onResume() {
        super.onResume();
        if (db.getCurrentUser() == null) {
            navController.navigate(R.id.action_lobbyFragment_to_loginFragment);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Game Lobby");
        binding = FragmentLobbyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        user = am.getUser();

        navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView2);

        binding.requestsview.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        binding.requestsview.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.requestsview.getContext(),
                llm.getOrientation());
        binding.requestsview.addItemDecoration(dividerItemDecoration);

        am.toggleDialog(true);

        CollectionReference ref = db.firestore.collection(Firestore.DB_REQUESTS);
        Query query = ref.orderBy("created_at", Query.Direction.DESCENDING);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value == null) {
                    return;
                }
                am.toggleDialog(false);
                ArrayList<Request> requests = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    Request request = doc.toObject(Request.class);
                    request.setId(doc.getId());
                    requests.add(request);
                }
                binding.requestsview.setAdapter(new RequestsAdapter(requests));
            }
        });

        binding.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> request = new HashMap<>();
                request.put("created_at", FieldValue.serverTimestamp());
                request.put("requester", new ArrayList<>(Arrays.asList(user.getId(), user.getDisplayName(), user.getPhotoref())));

                db.firestore.collection(Firestore.DB_REQUESTS).add(request).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getContext(), "Requested a game! Waiting...", Toast.LENGTH_SHORT).show();

                            runnable = () -> {
                                --count;
                                if (count <= 0) {
                                    count = SECS;
                                    Toast.makeText(getContext(), "No one joined the request! Removed.", Toast.LENGTH_SHORT).show();
                                    db.firestore.collection(Firestore.DB_REQUESTS).document(task.getResult().getId()).delete();
                                } else {
                                    if (dialog == null) {
                                        dialog = new AlertDialog.Builder(getActivity())
                                                .setTitle("Game Request")
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Toast.makeText(getContext(), "Request removed.", Toast.LENGTH_SHORT).show();
                                                        db.firestore.collection(Firestore.DB_REQUESTS).document(task.getResult().getId()).delete();
                                                    }
                                                })
                                                .show();
                                    }
                                    dialog.setMessage("Waiting for another player to join... " + count);
                                }
                            };
                            handler.postAtTime(runnable, System.currentTimeMillis() + interval);
                            handler.postDelayed(runnable, 1000);

                        } else {
                            task.getException().printStackTrace();
                        }
                    }
                });
            }
        });


        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                db.logout();
                navController.navigate(R.id.action_lobbyFragment_to_loginFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu1, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public interface ILobby {
        void alert(String msg);

        Firestore getDb();

        User getUser();

        void toggleDialog(boolean show);
    }
}