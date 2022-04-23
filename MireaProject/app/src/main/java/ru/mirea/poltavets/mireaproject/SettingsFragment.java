package ru.mirea.poltavets.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    Spinner spinnerLanguage;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String name, city, language;
    Integer age;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        name = requireActivity().findViewById(R.id.editTextName).toString();
        city = requireActivity().findViewById(R.id.editTextCity).toString();
        age = Integer.valueOf(requireActivity().findViewById(R.id.editTextAge).toString());




        // Делаем dropdown menu для выбора языка во фрагменте настроек. В файле res/values/strings/xml
        // хранится массив с 2мя переменными: "Russian" и "English"
        spinnerLanguage = requireActivity().findViewById(R.id.spinnerLanguage);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.language_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLanguage.setAdapter(adapter);


        // Начинаем работу с SharedPreferences
        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

        // Записываем в файл наши параметры
        editor.putString("NAME", name);
        editor.putString("CITY", city);
        editor.putInt("AGE", age);
        spinnerLanguage.setOnItemClickListener(this::onItemSelected);
        editor.apply();

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position){
            case 0:
                editor.putString("LANGUAGE", "Russian");
                editor.apply();
                break;
            case 1:
                editor.putString("LANGUAGE", "English");
                editor.apply();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}