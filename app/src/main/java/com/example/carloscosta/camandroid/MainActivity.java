package com.example.carloscosta.camandroid;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;


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
import java.util.Locale;


public class MainActivity extends AppCompatActivity  {

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
    TextToSpeech textSpeech;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
       // setToastandAudio();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
       // mapFragment.getMapAsync(this);
        progressBar.setVisibility(View.VISIBLE);


        ReceiveMessage p = null;
        //setToast();
        try {
            p = new ReceiveMessage(new DatagramSocket(8080), MainActivity.this, progressBar, mapFragment);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new Thread(p).start();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            LayoutInflater inflater = getLayoutInflater();
            final View checkboxLayout = inflater.inflate(R.layout.settings_layout, null);
            final CheckBox cb = (CheckBox)checkboxLayout.findViewById(R.id.checkBox);
            final EditText et = (EditText)checkboxLayout.findViewById(R.id.timemessage);
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Settings")
                    .setView(checkboxLayout)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    System.out.println(cb.isChecked());
                                    System.out.println(et.getText().toString());
                                    

                                }
                            })

                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });


            AlertDialog dialog = builder.create();
            dialog.show();





















        }

        return super.onOptionsItemSelected(item);
    }









    public void setToastandAudio()
    {
            // Retrieve the Layout Inflater and inflate the layout from xml
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        // get the reference of TextView and ImageVIew from inflated layout
        TextView toastTextView = (TextView) layout.findViewById(R.id.toastTextView);
        ImageView toastImageView = (ImageView) layout.findViewById(R.id.toastImageView);
        // set the text in the TextView
        toastTextView.setText("Risk of Collision, Reduce Speed!");
        // set the Image in the ImageView
        // toastImageView.setImageResource(R.drawable.ic_launcher);
        // create a new Toast using context
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG); // set the duration for the Toast
        toast.setView(layout); // set the inflated layout
        toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT| Gravity.LEFT ,0, 0);
        toast.show(); // display the custom Toast
        textSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                textSpeech.setLanguage(Locale.US);
                textSpeech.speak("To avoid a colission reduce speed", TextToSpeech.QUEUE_FLUSH, null);
            }

        });
    }
}

//"StationId", "Timestamp", "Latitude", "Longitude", "Heading", "Speed", "Acceleration", "YawRate",