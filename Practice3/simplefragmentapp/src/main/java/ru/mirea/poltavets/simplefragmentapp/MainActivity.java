package ru.mirea.poltavets.simplefragmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Fragment fragment1, fragment2;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment1 = new BlankFragment1();
        fragment2 = new BlankFragment2();
    }

    public void onClick(View view) {
        fragmentManager = getSupportFragmentManager();
        switch (view.getId()){
            case R.id.btnFragment1:
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                        fragment1).commit();
                break;
            case R.id.btnFragment2:
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                        fragment2).commit();
                break;
            default:
                break;
        }
    }
}