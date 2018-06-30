package com.example.carloscosta.camandroid;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.DatagramSocket;
import java.net.SocketException;



public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback {

    int cPort = 8080;
    Button customToast;
    TextView textLat, textLong;
    //View view = inflater.inflate(R.layout.testclassfragment, container, false);
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);private GoogleMap googleMap;
    static final LatLng DerekPos = new LatLng(40, -79);
    Resources res;
    Drawable drawable;
    ProgressBar progressBar;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        progressBar.setVisibility(View.VISIBLE);


        ReceiveMessage p = null;
        //setToast();
        try {
            p = new ReceiveMessage(new DatagramSocket(8080), MainActivity.this, progressBar);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new Thread(p).start();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }









    public void setToast()
    {

        // Retrieve the Layout Inflater and inflate the layout from xml
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));


        // get the reference of TextView and ImageVIew from inflated layout
        TextView toastTextView = (TextView) layout.findViewById(R.id.toastTextView);
        ImageView toastImageView = (ImageView) layout.findViewById(R.id.toastImageView);
        // set the text in the TextView
        toastTextView.setText("Custom Toast In Android");
        // set the Image in the ImageView
        // toastImageView.setImageResource(R.drawable.ic_launcher);
        // create a new Toast using context
        Toast toast = new Toast(getApplicationContext());

        toast.setDuration(Toast.LENGTH_LONG); // set the duration for the Toast
        toast.setView(layout); // set the inflated layout


        toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT| Gravity.LEFT ,0, 0);
        toast.show(); // display the custom Toast

    }


}

//"StationId", "Timestamp", "Latitude", "Longitude", "Heading", "Speed", "Acceleration", "YawRate",