package com.example.carloscosta.camandroid;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.DatagramSocket;

public class SetMessages implements OnMapReadyCallback {

    Activity a;
    TextView textStatid, textLat, textLong, textTime,textIpPort;
    GoogleMap map;
    ProgressBar pb;

    public SetMessages(Activity act, SupportMapFragment mapFragment, ProgressBar pb) {
        this.a = act;
        this.pb = pb;
        mapFragment.getMapAsync(this);
        textStatid = (TextView) act.findViewById(R.id.textStationID);
        textLat = (TextView) act.findViewById(R.id.textLatitude);
        textLong = (TextView) act.findViewById(R.id.textLongitude);
        textTime = (TextView) act.findViewById(R.id.textTimestamp);
        textIpPort = (TextView) act.findViewById(R.id.textIP);
    }


    Handler mHandlerTime = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            pb.setVisibility(View.INVISIBLE);
            String text = (String)msg.obj;
            textTime.setText(text);
        }
    };

    Handler mHandlerLat = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            String text = (String)msg.obj;
            textLat.setText(text);
        }
    };

    Handler mHandlerLong = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            String text = (String)msg.obj;
            textLong.setText(text);
        }
    };

    Handler mHandlerIpPort = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            String text = (String)msg.obj;
            textIpPort.setText(text);

        }
    };


    public void setValues( int acceleration, double heading, double latitude, double longitude, double speed, int id, double timestamp,  int yawRate, int alert, String ipServer, int portServer){
        android.os.Message msgTime = new android.os.Message();
        msgTime.obj = Double.toString(timestamp);
        mHandlerTime.sendMessage(msgTime);

        android.os.Message msgLat = new android.os.Message();
        msgLat.obj = Double.toString(latitude);
        mHandlerLat.sendMessage(msgLat);

        android.os.Message msgLong = new android.os.Message();
        msgLong.obj = Double.toString(longitude);
        mHandlerLong.sendMessage(msgLong);

        android.os.Message msgIpPort = new android.os.Message();
        msgIpPort.obj = ipServer+":"+portServer;
        mHandlerIpPort.sendMessage(msgIpPort);

        setLocations(latitude,longitude,id);







    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng car = new LatLng(-34, 151);
        map = googleMap;
    }



    void setLocations(final Double dLatitude, final Double dLongitude, final int typecar)
    {
        a.runOnUiThread(new Runnable(){

            public void run(){
                map.addMarker(new MarkerOptions().position(new LatLng(dLatitude, dLongitude))
                        .title("Cars").icon(BitmapDescriptorFactory.fromResource(R.drawable.car1)));

                if (typecar == 1)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), 17));

            }
        });
    }




}
