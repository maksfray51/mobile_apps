package ru.mirea.poltavets.mieraprojectnew.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.poltavets.mieraprojectnew.AddNewUserDBActivity;
import ru.mirea.poltavets.mieraprojectnew.FireBaseAuth;
import ru.mirea.poltavets.mieraprojectnew.R;
import ru.mirea.poltavets.mieraprojectnew.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.authBtn.setOnClickListener(this::onClickToAuth);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onClickToAuth(View view){
        // Это для вызова activity  из фрагмета
        Intent intent = new Intent(requireContext(), FireBaseAuth.class);
        startActivity(intent);
    }
}