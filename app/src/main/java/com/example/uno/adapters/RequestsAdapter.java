package com.example.uno.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.databinding.RequestLayoutBinding;
import com.example.uno.models.Request;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.UViewHolder> {

    ArrayList<Request> requests;

    RequestLayoutBinding binding;

    public RequestsAdapter(ArrayList<Request> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public UViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RequestLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UViewHolder holder, int position) {
        Request chatroom = requests.get(position);


        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.requests.size();
    }


    public static class UViewHolder extends RecyclerView.ViewHolder {

        RequestLayoutBinding binding;

        public UViewHolder(@NonNull RequestLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
