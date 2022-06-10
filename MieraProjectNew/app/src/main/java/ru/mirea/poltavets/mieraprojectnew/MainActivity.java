package ru.mirea.poltavets.mieraprojectnew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.poltavets.mieraprojectnew.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        // Это floating action button
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, // init
                R.id.calculatorFragment, R.id.webViewFragment, //practice 3
                R.id.musicFragment, // practice 4
                R.id.threeSensorsFragment, R.id.cameraFragment, R.id.recorderFragment, // practice 5
                R.id.settingsFragment, R.id.historyFragment, // practice 6
                R.id.infoFromNetFragment, // practice 7 и тут ещё firebase
                R.id.dataBaseFragment, // practice 8
                R.id.mapsFragment2 // practice 9
        )
                .setOpenableLayout(drawer)
                .build();

        // navController — основной механизм Navigation Component, отображающий
        //на экране фрагменты. Для этого он должен обладать спискам фрагментов
        //и контейнер, в котором он будет эти фрагменты отображать.

        // NavGraph – предназначен для хранения списка фрагментов. NavController
        //отображает фрагменты только из этого списка. Далее обозначается как граф.
        //(res>layout>content_main.xml)

        // NavHostFragment - это контейнер. Внутри него NavController отображает
        //фрагменты.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);


        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}