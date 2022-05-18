package ru.mirea.poltavets.mireaproject;

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

public class MapsFragment extends Fragment implements GoogleMap.OnMapClickListener {
    private GoogleMap map;
    private ArrayList<MarkerOptions> mapMarker =  new ArrayList<>();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            map.setOnMapClickListener(MapsFragment.this::onMapClick);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            LatLng headQuarters = new LatLng(55.670005, 37.479894);
            googleMap.addMarker(new MarkerOptions().position(headQuarters).title("РТУ МИРЭА. " +
                    "Год создания: 1947г. Адрес: г.Москва, ул. Вернадского, д. 78. " +
                    "Координаты: 55.670005, 37.479894."));

            LatLng instituteOfCybersecurityAndDigitalTechnologies = new LatLng(55.794259,
                    37.701448);
            googleMap.addMarker(new MarkerOptions().position(instituteOfCybersecurityAndDigitalTechnologies)
            .title("РТУ МИРЭА. Год создания: 1947г. Адрес: г.Москва, ул. Стромынка, д. 20. " +
                    "Координаты: 55.794259, 37.701448"));

            LatLng chemical = new LatLng(55.661445, 37.477049);
            googleMap.addMarker(new MarkerOptions().position(chemical)
                    .title("РТУ МИРЭА. Год создания: 1947г. Адрес: г.Москва, ул. Вернадского, д. 86. " +
                            "Координаты: 55.661445, 37.477049"));

            LatLng homeTown = new LatLng(69.074878, 33.433085);
            googleMap.addMarker(new MarkerOptions().position(homeTown)
                    .title("Столица северного флота — Североморск. Год создания: 1896г. Адрес: " +
                            "г.Североморск, ул. Морская, д. 7. " +
                            "Координаты: 69.074878, 33.433085"));

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        Date currentTime = Calendar.getInstance().getTime();
        latLng.toString();
        Log.d("MAP", String.valueOf(latLng));
        Log.d("TIME", String.valueOf(currentTime));

        addNewMarker(currentTime, latLng);
    }

    public void addNewMarker(Date date, LatLng latLng){
        MarkerOptions marker = new MarkerOptions().title("отметочка была сделана" + date).position(latLng);
        map.addMarker(marker);
        mapMarker.add(marker);
    }
}