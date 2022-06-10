package ru.mirea.poltavets.mieraprojectnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.poltavets.mieraprojectnew.RoomDataBase.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private final Context context;
    private List<User> userList;

    public UserListAdapter(Context context){
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUserList(List<User> userList){
        this.userList = userList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserViewHolder holder, int position) {
        holder.tvFirstName.setText(this.userList.get(position).firstName);
        holder.tvLastName.setText(this.userList.get(position).lastName);
    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvFirstName, tvLastName;
        private UserViewHolder(View view){
            super(view);
            tvFirstName =  view.findViewById(R.id.tvFirstName);
            tvLastName =  view.findViewById(R.id.tvLastName);
        }
    }
}
