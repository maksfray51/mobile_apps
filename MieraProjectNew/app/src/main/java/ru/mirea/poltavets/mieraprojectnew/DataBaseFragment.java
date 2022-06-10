package ru.mirea.poltavets.mieraprojectnew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import ru.mirea.poltavets.mieraprojectnew.RoomDataBase.AppDataBase;
import ru.mirea.poltavets.mieraprojectnew.RoomDataBase.User;
import ru.mirea.poltavets.mieraprojectnew.databinding.FragmentDataBaseBinding;

public class DataBaseFragment extends Fragment {
    private static final String TAG = DataBaseFragment.class.getSimpleName();
    private UserListAdapter userListAdapter;
    private FragmentDataBaseBinding binding;
    private ActivityResultLauncher<Intent> addNewUserActivityResultLauncher;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // функция для очистки базы данных
//        clearDataBase();

        binding = FragmentDataBaseBinding.inflate(inflater, container, false);
        binding.addNewUserBtn.setOnClickListener(this:: onClick);




        // В следующих строках мы инициализируем RecyckerView для отображения DataBase
        binding.userRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);
        binding.userRecycleView.addItemDecoration(dividerItemDecoration);

        userListAdapter = new UserListAdapter(this.getContext());
        binding.userRecycleView.setAdapter(userListAdapter);


        addNewUserActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            loadUserList();
                        }
                    }
                }
        );


        loadUserList();


        return binding.getRoot();
    }

    private void clearDataBase(){
        AppDataBase db = AppDataBase.getDPInstance(requireContext());
        db.userDao().deleteAll();
    }


    private void loadUserList(){
        AppDataBase db = AppDataBase.getDPInstance(requireContext());
        List<User> usersList = db.userDao().getAllUsers();
        userListAdapter.setUserList(usersList);
    }

    public void onClick(View view) {
        Log.d(TAG, "Клик на кнопку " + binding.addNewUserBtn.getText().toString() +
                " произошёл сто пудово");
        Intent intent = new Intent(requireActivity(), AddNewUserDBActivity.class);
        addNewUserActivityResultLauncher.launch(intent);
    }
}