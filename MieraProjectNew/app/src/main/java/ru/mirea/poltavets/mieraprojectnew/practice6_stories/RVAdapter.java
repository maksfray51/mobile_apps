package ru.mirea.poltavets.mieraprojectnew.practice6_stories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.poltavets.mieraprojectnew.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    private List<PersonForStory> persons;
    private final Context context;

    RVAdapter (Context context){
        this.context = context;
    }

    public void setPersonsList(List<PersonForStory> persons){
        this.persons = persons;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(this.persons.get(i).name);
        personViewHolder.personAge.setText(this.persons.get(i).age);
        personViewHolder.personPhoto.setImageResource(R.drawable.elon_musk);
    }

    @Override
    public int getItemCount() {
        return this.persons.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView personName, personAge;
        ImageView personPhoto;
        PersonViewHolder(View view) {
            super(view);
            personName = view.findViewById(R.id.personName);
            personAge = view.findViewById(R.id.personAge);
            personPhoto = view.findViewById(R.id.personPhoto);
        }
    }
}
