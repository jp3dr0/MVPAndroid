package com.example.joaop.mvpindiano.login.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joaop.mvpindiano.R;
import com.example.joaop.mvpindiano.network.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> usuarios;
    private List<String> usernames;

    public UserAdapter(List<User> usuarios, List<String> usernames){
        this.usuarios = usuarios;
        this.usernames = usernames;
    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        if (usuarios != null){
            User user = usuarios.get(position);
            holder.email.setText(user.getEmail());
            holder.phone.setText(user.getPhone());
            holder.name.setText(user.getUsername());
        }
        else {
            holder.name.setText(usernames.get(position));
            holder.email.setVisibility(View.GONE);
            holder.phone.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return usuarios == null ? usernames.size() : usuarios.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone, email;

        public UserViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
        }
    }

}