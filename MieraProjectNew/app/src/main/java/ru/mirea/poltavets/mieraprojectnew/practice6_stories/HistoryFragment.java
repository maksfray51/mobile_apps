package ru.mirea.poltavets.mieraprojectnew.practice6_stories;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.poltavets.mieraprojectnew.R;
import ru.mirea.poltavets.mieraprojectnew.databinding.FragmentHistoryBinding;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private List<PersonForStory> personsForStories = new ArrayList<>();
    private RVAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // In most cases, view binding replaces findViewById.
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        // Делаем кнопочку для добавления историй
//        binding.addStoryButton.setEnabled(false);
        binding.addStoryButton.setOnClickListener(this::onClickAddStory);


        // RecycleView Adapter для показа историй
        binding.recycleView.setLayoutManager(new LinearLayoutManager(requireContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);
        binding.recycleView.addItemDecoration(dividerItemDecoration);


        recyclerViewAdapter = new RVAdapter(requireContext());
        binding.recycleView.setAdapter(recyclerViewAdapter);

        // Делаем фиксированный размер для улучшения производительности
        binding.recycleView.setHasFixedSize(true);

        // Создаём истории
        initPersonsForStories();

        // Загружаем историю
        loadPersonForStories();

        return binding.getRoot();
    }

    // Тут мы создаём небольшие истории
    private void initPersonsForStories(){
        PersonForStory person = new PersonForStory("Elon Musk", "50 years old",
                R.drawable.elon_musk);
        personsForStories.add(person);
        personsForStories.add(person);
        personsForStories.add(person);
    }

    private void loadPersonForStories(){
        recyclerViewAdapter.setPersonsList(personsForStories);
    }

    private void onClickAddStory(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
        final EditText personName = new EditText(getContext());
        personName.setSingleLine(true);
        alert.setTitle("Создаём историю!");
        alert.setMessage("Введите имя");
        alert.setView(personName);

        alert.setPositiveButton("Next", (dialogInterface, i) -> {
            String personValue = personName.getText().toString();
            acceptStoryContent(personValue);
        });

        alert.setNegativeButton("Cancel", (dialogInterface, i) -> {});

        alert.show();
    }

    private void acceptStoryContent(String personName) {
        AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
        final EditText agePerson = new EditText(getContext());
        alert.setTitle("Создаём историю!");
        alert.setMessage("Введите возраст");
        alert.setView(agePerson);

        alert.setPositiveButton("Добавить", (dialogInterface, i) -> {
            String ageValue = agePerson.getText().toString();
            personsForStories.add(new PersonForStory(personName, ageValue, R.drawable.elon_musk));
            Log.i("PERSON","New person added\nName: " + personName + ageValue);
        });

        alert.setNegativeButton("Cancel", (dialogInterface, i) -> {});
        alert.show();
    }
}