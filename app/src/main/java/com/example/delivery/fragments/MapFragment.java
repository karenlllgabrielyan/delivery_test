package com.example.delivery.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.delivery.R;
import com.example.delivery.databases.DatabaseHelper;
import com.example.delivery.interfaces.OnMarkerChangedListener;
import com.example.delivery.templates.Mark;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.graphics.Bitmap.Config.ARGB_8888;

public class MapFragment extends Fragment implements OnMapReadyCallback, OnMarkerChangedListener {

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private final String TAG = "MapFragment";
    private MapView mapView;
    private GoogleMap gmap;
    private DatabaseHelper dbHelper;
    private ArrayList<Mark> marks;
    private int currentPosition;
    private String latLng;
    private OnMarkerChangedListener markerChangedListener;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment_layout, container, false);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = v.findViewById(R.id.map_view);
//        mapView.onCreate(savedInstanceState);

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        dbHelper = new DatabaseHelper(getContext());
        marks = new ArrayList<>(dbHelper.readMarksData());
        currentPosition = dbHelper.getCurrentMark();


        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);


    }
//--------------------------------------------------------------------------------------------------------MAP READY
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        latLng = marks.get(currentPosition).getDestination();
        String[] split0 = latLng.split(",");
        double lat0 = Double.parseDouble(split0[0]);
        double lng0 = Double.parseDouble(split0[1]);
        LatLng ny = new LatLng(lat0, lng0);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));



        for (int i = 0; i < marks.size(); i++) {
            latLng = marks.get(i).getDestination();
            String[] split = latLng.split(",");
            double lat = Double.parseDouble(split[0]);
            double lng = Double.parseDouble(split[1]);

            Bitmap bm;
            int numLength;
//-----------------------------------------------------------------------------------------------------------------------------IF FINISHED
            if (i < currentPosition) {
                if (i < 10) numLength = 0;
                else numLength = 1;

                bm = makeBitmap(getContext(), String.valueOf(i +1), numLength, R.drawable.blue_mark);

                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title(marks.get(i).getName())
                        .icon(BitmapDescriptorFactory.fromBitmap(bm));
                gmap.addMarker(marker);

            } else {
//-----------------------------------------------------------------------------------------------------------------------------IF CURRENT
                if (i == currentPosition) {
                    if (i < 10) numLength = 0;
                    else numLength = 1;

                    bm = makeBitmap(getContext(), String.valueOf(i +1), numLength, R.drawable.red_mark);
                    MarkerOptions marker = new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title(marks.get(i).getName())
                            .icon(BitmapDescriptorFactory.fromBitmap(bm));
                    gmap.addMarker(marker);
                } else {
//-----------------------------------------------------------------------------------------------------------------------------IF NEXT
                    if (i < 10) numLength = 0;
                    else numLength = 1;

                    bm = makeBitmap(getContext(), String.valueOf(i +1), numLength, R.drawable.black_mark);

                    MarkerOptions marker = new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title(marks.get(i).getName())
                            .icon(BitmapDescriptorFactory.fromBitmap(bm));
                    gmap.addMarker(marker);
                }
            }
        }


    }
//----------------------------------------------------------------------------------------------------BITMAP WITH TEXT
    public Bitmap makeBitmap(Context context, String text, int numLength, int icon)
    {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, icon);
        bitmap = bitmap.copy(ARGB_8888, true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE); // Text color
        paint.setTextSize(32 * scale); // Text size
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE); // Text shadow
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        if (numLength == 0){
            int x = bitmap.getWidth() - bounds.width() - 35; // 10 for padding from right
            int y = bounds.height() + 15;
            canvas.drawText(text, x, y, paint);
        }
        else {
            int x = bitmap.getWidth() - bounds.width() - 15; // 10 for padding from right
            int y = bounds.height() + 15;
            canvas.drawText(text, x, y, paint);
        }


        return  bitmap;
    }


    @Override
    public void onResume() {
        Log.d(TAG, "------------------------------------------------------------- ON Resume");
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        Log.d(TAG, "------------------------------------------------------------- ON Start");
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "------------------------------------------------------------- ON Stop");
        currentPosition = dbHelper.getCurrentMark();
        marks = dbHelper.readMarksData();
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "------------------------------------------------------------- ON Pause");
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "------------------------------------------------------------- ON Destroy");
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void markerStates(int position) {
    }
}
