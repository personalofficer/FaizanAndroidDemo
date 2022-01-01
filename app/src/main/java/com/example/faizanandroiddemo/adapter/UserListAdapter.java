package com.example.faizanandroiddemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faizanandroiddemo.R;
import com.example.faizanandroiddemo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    Context context;

    List<User> data = new ArrayList<>();

    public UserListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.data = userList;
    }

    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.cust_userlist_layout, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {

        holder.txt_userName.setText(data.get(position).getFirstName());
        holder.txt_userCelcius.setText(data.get(position).getCelcius());
        holder.txt_userDescription.setText(data.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_userName, txt_userCelcius, txt_userDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_userName = itemView.findViewById(R.id.txt_userName);
            txt_userCelcius = itemView.findViewById(R.id.txt_userCelcius);
            txt_userDescription = itemView.findViewById(R.id.txt_userDescription);
        }
    }
}