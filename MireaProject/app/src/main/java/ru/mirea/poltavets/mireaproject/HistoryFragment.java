package ru.mirea.poltavets.mireaproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = requireActivity().findViewById(R.id.recycleView);
        // Делаем фиксированный размер для улучшения производительности
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Тут мы создадим небольшие "истории"
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Elon Musk", "50 years old", R.drawable.ilon_musk));
        persons.add(new Person("Elon Musk", "50 years old", R.drawable.ilon_musk));
        persons.add(new Person("Elon Musk", "50 years old", R.drawable.ilon_musk));


        RVAdapter adapter = new RVAdapter(persons);
        recyclerView.setAdapter(adapter);


        Button btn = requireActivity().findViewById(R.id.floatingActionButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), activityForStory.class);
                startActivity(intent);

                Bundle args = requireActivity().getIntent().getExtras();
                persons.add(new Person(args.get("name").toString(), args.get("age").toString(), R.drawable.ilon_musk));
            }
        });

        return view;
    }

}