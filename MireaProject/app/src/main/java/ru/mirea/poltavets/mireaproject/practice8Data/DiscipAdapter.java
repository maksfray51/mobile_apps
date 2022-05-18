package ru.mirea.poltavets.mireaproject.practice8Data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.poltavets.mireaproject.R;

public class DiscipAdapter extends RecyclerView.Adapter<DiscipAdapter.DiscipViewHolder> {
    private List<Subjects> subjects;

    public DiscipAdapter(List<Subjects> subjects) {
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public DiscipAdapter.DiscipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjects_list, parent, false);

        return new DiscipAdapter.DiscipViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscipAdapter.DiscipViewHolder holder, int position) {
        Subjects subjects = this.subjects.get(position);
        holder.name.setText(subjects.name);
        holder.lecturersName.setText(subjects.lecturersName);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class DiscipViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView lecturersName;

        public DiscipViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.subjectName);
            lecturersName = (TextView) itemView.findViewById(R.id.lecturersName);
        }
    }
}
