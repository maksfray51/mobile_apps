package ru.mirea.poltavets.mieraprojectnew;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Objects;

import ru.mirea.poltavets.mieraprojectnew.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private SharedPreferences preferences;
    private FragmentSettingsBinding binding;
    private SharedPreferences.Editor editor;
    private static final String TAG = SettingsFragment.class.getSimpleName();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // In most cases, view binding replaces findViewById.
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        preferences = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.language_array,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        binding.spinnerLanguage.setAdapter(adapter);

        binding.settingsBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(),
                        "You've selected name: " + binding.editTextName.getText().toString(),
                        Toast.LENGTH_LONG).show();

                Toast.makeText(requireContext(),
                        "You've selected city: " + binding.editTextCity.getText().toString(),
                        Toast.LENGTH_LONG).show();


                // Начинаем работу с SharedPreferences
                preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
                editor = preferences.edit();

                // Записываем наши параметры в файл
                editor.putString("NAME", binding.editTextName.getText().toString());
                editor.putString("CITY", binding.editTextCity.getText().toString());
                editor.apply();

                Log.i("NAME", binding.editTextName.getText().toString());
                Log.i("CITY", binding.editTextName.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position){
            case 0:
                editor.putString("LANGUAGE", "Russian");
                editor.apply();
                Toast.makeText(requireContext(),
                        "You've selected language: Russian",
                        Toast.LENGTH_LONG).show();
                break;
            case 1:
                editor.putString("LANGUAGE", "English");
                editor.apply();
                Toast.makeText(requireContext(),
                        "You've selected language: English",
                        Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
