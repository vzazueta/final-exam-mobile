package com.example.final_exam;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapFragment extends Fragment  implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Marker marker;
    private FusedLocationProviderClient fusedLocationClient;
    private static final String LogTag = "MAP_FRAGMENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);

    }

    @Override
    public void onViewCreated(View v, Bundle savedState){
        super.onViewCreated(v,savedState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d(LogTag, "map ready");
        fusedLocationClient.getLastLocation()
            .addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.d(LogTag, "success finding location");
                    Log.d(LogTag, location.toString());
                    String userLocation = location.getLatitude() + ", " + location.getLongitude();
                    String hospitalLocation = 20.6857977 + ", " + -103.3463132;
                    if (location != null) {
                        drawRoute(googleMap, userLocation, hospitalLocation);
                    }
                }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(LogTag, e.toString());
                e.printStackTrace();
            }
        });

    }

    private void setMarkerOnMap(double ns, double we){
        LatLng latLng = new LatLng(ns, we);
        marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker()).position(latLng));
    }

    @Override
    public void onDestroy() {
        mMap = null;
        super.onDestroy();
    }


    private void drawRoute(GoogleMap googleMap, String start, String end){
        Log.d(LogTag, "drawing route");

        googleMap.clear();

        Log.d("MAPPING_KEY", getResources().getString(R.string.maps_key));
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(getResources().getString(R.string.maps_key))
                .build();

        DirectionsApiRequest req = DirectionsApi.getDirections(context, start, end);
        ArrayList<LatLng> path = new ArrayList();
        Double distance = 0.0;
        Location last = null;
        try {
            DirectionsResult res = req.await();
            if (res.routes != null && res.routes.length >0) {
                DirectionsRoute route = res.routes[0];
                for (int i = 0; i < route.legs.length; i++) {
                    DirectionsLeg leg  = route.legs[i];
                    if (leg.steps != null) {
                        for (int j = 0; j < leg.steps.length; j++ ) {
                            DirectionsStep step = leg.steps[j];
                            if (step.steps != null && step.steps.length > 0) {
                                for (int k = 0; k < step.steps.length; k++) {
                                    DirectionsStep step1 = step.steps[k];
                                    EncodedPolyline points1 = step1.polyline;
                                    if (points1 != null) {
                                        List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords1) {
                                            Location l1 = new Location("");
                                            l1.setLatitude(coord.lat);
                                            l1.setLongitude(coord.lng);
                                            if(last != null){
                                                distance += last.distanceTo(l1);
                                            }
                                            path.add(new LatLng(coord.lat,coord.lng));
                                            last = l1;
                                        }
                                    }
                                }
                            } else {
                                EncodedPolyline points = step.polyline;
                                if (points != null) {
                                    List<com.google.maps.model.LatLng> coords =
                                            points.decodePath();
                                    for (com.google.maps.model.LatLng coord : coords) {
                                        Location l1 = new Location("");
                                        l1.setLatitude(coord.lat);
                                        l1.setLongitude(coord.lng);
                                        if(last != null){
                                            distance += last.distanceTo(l1);
                                        }
                                        path.add(new LatLng(coord.lat,coord.lng));
                                        last = l1;
                                    }
                                }
                            }
                        }
                    }
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                if (path.size() > 0) {
                    PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5f);
                    mMap.addPolyline(opts);
                    for(LatLng p: path){
                        builder.include(p);
                    }
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                googleMap.addMarker(new MarkerOptions().position(path.get(0)));
                String hospitalTag =
                        getResources().getString(R.string.maps_hospital_tag) +
                        String.format(Locale.getDefault(), "%.00f", distance/1000) +
                        getResources().getString(R.string.maps_km);
                googleMap.addMarker(new MarkerOptions().position(path.get(path.size()-1)).title(hospitalTag)).showInfoWindow();
                googleMap.animateCamera(cu);
            }
            else {
                Toast.makeText(getActivity(), getResources().getString(R.string.maps_error), Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            Log.e(LogTag, "ALGO FALLO");
            Log.e(LogTag, e.toString());
        }

    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){//Can add more as per requirement

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    123);
        }
    }
}
