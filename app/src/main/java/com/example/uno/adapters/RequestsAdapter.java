package com.example.uno.adapters;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uno.databinding.RequestLayoutBinding;
import com.example.uno.helpers.Utils;
import com.example.uno.models.Request;
import com.example.uno.models.User;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.UViewHolder> {

    ArrayList<Request> requests;

    RequestLayoutBinding binding;

    IReqAdapter am;

    User user;

    public RequestsAdapter(ArrayList<Request> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public UViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RequestLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        am = (IReqAdapter) parent.getContext();
        return new UViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UViewHolder holder, int position) {
        Request request = requests.get(position);

        User requester = request.getRequester();

        binding.textView5.setText(requester.getDisplayName());

        user = am.getUser();

        Utils.setImage(binding.getRoot(), binding.imageView, requester.getId(), requester.getPhotoref());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (requester.getId().equals(user.getId())) return;
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.binding.getRoot().getContext());
            }
        });
    }

    public interface IReqAdapter {

        User getUser();

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
