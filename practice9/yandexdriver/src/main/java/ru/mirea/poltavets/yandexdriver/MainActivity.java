package ru.mirea.poltavets.yandexdriver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.Error;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DrivingSession.DrivingRouteListener{

    private final String MAPKIT_API_KEY = "49ef0018-5a7f-48bf-9bc9-47bc84fab22b";
    private final Point ROUTE_START_LOCATION = new Point(55, 37);
    private final Point ROUTE_END_LOCATION = new Point(55, 37);
    private final Point SCREEN_CENTER = new Point((ROUTE_START_LOCATION.getLatitude() + ROUTE_END_LOCATION.getLatitude()) / 2, (ROUTE_START_LOCATION.getLongitude() + ROUTE_END_LOCATION.getLongitude()) / 2);
    private MapView mapView;
    private MapObjectCollection mapObjects;
    private DrivingRouter drivingRouter;
    private DrivingSession drivingSession;
    private int[] colors = {0x5891ad, 0xcfdee5, 0x00FFBBBB, 0x65c796};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // установка ключа разработчика
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        DirectionsFactory.initialize(this);
        // Укажите имя activity
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        mapView = findViewById(R.id.mapView);
        // Устанавливаем начальную точку и масштаб
        mapView.getMap().move(new CameraPosition(
                SCREEN_CENTER, 10, 0, 0));
        // Ининциализируем объект для создания маршрута водителя
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
        mapObjects = mapView.getMap().getMapObjects().addCollection();
        submitRequest();
    }

    private void submitRequest() {
        DrivingOptions options = new DrivingOptions();

        options.setAlternativeCount(3);
        ArrayList<RequestPoint> requestPoints = new ArrayList<>();

        requestPoints.add(new RequestPoint(ROUTE_START_LOCATION, RequestPointType.WAYPOINT, null));
        requestPoints.add(new RequestPoint(ROUTE_END_LOCATION, RequestPointType.WAYPOINT, null));

        drivingSession = drivingRouter.requestRoutes(requestPoints, options, this);
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onDrivingRoutes(@NonNull @androidx.annotation.NonNull List<DrivingRoute> list) {
        int color;
        for (int i = 0; i < list.size(); i++) {
            color = colors[i];
            mapObjects.addPolyline(list.get(i).getGeometry()).setStrokeColor(color);
        }
    }

    @Override
    public void onDrivingRoutesError(@NonNull @androidx.annotation.NonNull Error error) {
        String errorMessage = "error";
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}