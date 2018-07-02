package com.example.carloscosta.camandroid;

import android.app.Activity;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

public class SetMessages implements OnMapReadyCallback {

    Activity a;
    TextView textStatid, textLat, textLong, textTime,textIpPort, textSpeed, textHeading;
    GoogleMap map;
    ProgressBar pb;
    TextToSpeech textSpeech;
    private int timestoSpeech = 0;

    public SetMessages(Activity act, SupportMapFragment mapFragment, ProgressBar pb) {
        this.a = act;
        this.pb = pb;
        mapFragment.getMapAsync(this);
        textStatid = (TextView) act.findViewById(R.id.textStationID);
        textLat = (TextView) act.findViewById(R.id.textLatitude);
        textLong = (TextView) act.findViewById(R.id.textLongitude);
        textTime = (TextView) act.findViewById(R.id.textTimestamp);
        textSpeed = (TextView) act.findViewById(R.id.textSpeed);
        textHeading = (TextView) act.findViewById(R.id.textYawRate);
        textIpPort = (TextView) act.findViewById(R.id.textIP);
    }


    Handler mHandlerStatId = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            String text = (String)msg.obj;
            textStatid.setText(text);
        }
    };

    Handler mHandlerTime = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            pb.setVisibility(View.INVISIBLE);
            String text = (String)msg.obj;
            textTime.setText(text);
        }
    };

    Handler mHandlerSpeed = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            String text = (String)msg.obj;
            textSpeed.setText(text);
        }
    };

    Handler mHandlerHeading = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            pb.setVisibility(View.INVISIBLE);
            String text = (String)msg.obj;
            textHeading.setText(text);
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
        android.os.Message msgStatId = new android.os.Message();
        msgStatId.obj = Integer.toString(1);
        mHandlerStatId.sendMessage(msgStatId);
        if(id == 1){

        android.os.Message msgTime = new android.os.Message();
        msgTime.obj = Double.toString(timestamp);
        mHandlerTime.sendMessage(msgTime);

        android.os.Message msgSpeed = new android.os.Message();
        msgSpeed.obj = Double.toString(speed*3.6) + " km/h";
        mHandlerSpeed.sendMessage(msgSpeed);

        android.os.Message msgHeading = new android.os.Message();
        msgHeading.obj = Double.toString(heading);
        mHandlerHeading.sendMessage(msgHeading);

        android.os.Message msgLat = new android.os.Message();
        msgLat.obj = Double.toString(latitude);
        mHandlerLat.sendMessage(msgLat);

        android.os.Message msgLong = new android.os.Message();
        msgLong.obj = Double.toString(longitude);
        mHandlerLong.sendMessage(msgLong);

        android.os.Message msgIpPort = new android.os.Message();
        msgIpPort.obj = ipServer+":"+portServer;
        mHandlerIpPort.sendMessage(msgIpPort);

        }

        setLocations(latitude,longitude,id, alert);



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng car = new LatLng(-34, 151);
        map = googleMap;
    }



    void setLocations(final Double dLatitude, final Double dLongitude, final int typecar, final int alert)
    {
        a.runOnUiThread(new Runnable(){

            public void run(){



                if (alert == 2 && typecar == 1){
                    System.out.println(timestoSpeech);
                    if(timestoSpeech>10 && timestoSpeech < 15){
                    setToastandAudio();
                    }
                    timestoSpeech++;
                }

                if (typecar == 1){
                    map.clear();
                    map.addMarker(new MarkerOptions().position(new LatLng(dLatitude, dLongitude))
                            .title("Car1").icon(BitmapDescriptorFactory.fromResource(R.drawable.car1)));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), 17));
                }

                if(typecar == 2){
                    map.addMarker(new MarkerOptions().position(new LatLng(dLatitude, dLongitude))
                        .title("Car2").icon(BitmapDescriptorFactory.defaultMarker()));
                }


            }
        });
    }

    public void setToastandAudio()
    {
        // Retrieve the Layout Inflater and inflate the layout from xml
        LayoutInflater inflater = a.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) a.findViewById(R.id.toast_layout_root));

        // get the reference of TextView and ImageVIew from inflated layout
        TextView toastTextView = (TextView) layout.findViewById(R.id.toastTextView);
        ImageView toastImageView = (ImageView) layout.findViewById(R.id.toastImageView);
        // set the text in the TextView
        toastTextView.setText("Risk of Collision, Reduce Speed!");
        // set the Image in the ImageView
        // toastImageView.setImageResource(R.drawable.ic_launcher);
        // create a new Toast using context
        Toast toast = new Toast(a.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG); // set the duration for the Toast
        toast.setView(layout); // set the inflated layout
        toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT| Gravity.LEFT ,0, 0);
        toast.show(); // display the custom Toast
        textSpeech = new TextToSpeech(a.getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                textSpeech.setLanguage(Locale.US);
                textSpeech.speak("To avoid a colission reduce speed", TextToSpeech.QUEUE_FLUSH, null);
            }

        });
    }




}
