package ru.mirea.poltavets.mieraprojectnew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MapsFragment extends Fragment{
    private GoogleMap map;
    private ArrayList<MarkerOptions> mapMarker =  new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment supportMapFragment =(SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.google_map);


        Objects.requireNonNull(supportMapFragment).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng headQuarters = new LatLng(55.670005, 37.479894);
                googleMap.addMarker(new MarkerOptions().position(headQuarters).title("РТУ МИРЭА. " +
                        "Год создания: 1947г. Адрес: г.Москва, ул. Вернадского, д. 78. " +
                        "Координаты: 55.670005, 37.479894."));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(headQuarters,2));

                LatLng instituteOfCybersecurityAndDigitalTechnologies = new LatLng(55.794259,
                        37.701448);
                googleMap.addMarker(new MarkerOptions().position(instituteOfCybersecurityAndDigitalTechnologies)
                        .title("РТУ МИРЭА. Год создания: 1947г. Адрес: г.Москва, ул. Стромынка, д. 20. " +
                                "Координаты: 55.794259, 37.701448"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(instituteOfCybersecurityAndDigitalTechnologies,2));

                LatLng chemical = new LatLng(55.661445, 37.477049);
                googleMap.addMarker(new MarkerOptions().position(chemical)
                        .title("РТУ МИРЭА. Год создания: 1947г. Адрес: г.Москва, ул. Вернадского, д. 86. " +
                                "Координаты: 55.661445, 37.477049"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chemical,2));

                LatLng homeTown = new LatLng(69.074878, 33.433085);
                googleMap.addMarker(new MarkerOptions().position(homeTown)
                        .title("Столица северного флота — Североморск. Год создания: 1896г. Адрес: " +
                                "г.Североморск, ул. Морская, д. 7. " +
                                "Координаты: 69.074878, 33.433085"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeTown,2));




                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        //googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });
        return view;
    }
}

