package ru.mirea.poltavets.mireaproject.practice8Data;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.poltavets.mireaproject.R;


public class DataDiscipFragment extends Fragment {
    private List<Subjects> disciplines = new ArrayList<>();
    private SubjectsViewModel subjectsViewModel;
    private DiscipAdapter discipAdapter = new DiscipAdapter(disciplines);
    private RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> launcher;
    public static final int ADD_CAT_RESULT_CODE=1;
    public static final String NAME_LABEL="name";
    public static final String TYPE_LABEL="lecturersName";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_data_discip, container, false);

        if (getActivity() != null){
            subjectsViewModel = new ViewModelProvider(getActivity()).get(SubjectsViewModel.class);
            subjectsViewModel.getDiscipLiveData().observe(getActivity(), this::onChanged);
        }
        view.findViewById(R.id.btnAddDiscip).setOnClickListener(this::onNewSubjectClicked);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.discipRecyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(discipAdapter);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                (result) -> {
                    if (result.getResultCode() == ADD_CAT_RESULT_CODE
                            && result.getData() != null){
                        Subjects disciplines = new Subjects();
                        disciplines.name = result.getData().getStringExtra(NAME_LABEL);
                        disciplines.lecturersName = result.getData().getStringExtra(TYPE_LABEL);
                        subjectsViewModel.addSubject(disciplines);
                        discipAdapter.notifyDataSetChanged();
                    }
                });
        return view;
    }

    public void onChanged(List<Subjects> brawlerfromDB) {
        if (!disciplines.isEmpty()){
            disciplines.clear();
        }
        disciplines.addAll(brawlerfromDB);
        discipAdapter.notifyDataSetChanged();
    }

    private void onNewSubjectClicked(View view){
        Intent intent = new Intent(getActivity(), AddSubject.class);
        launcher.launch(intent);
    }
}