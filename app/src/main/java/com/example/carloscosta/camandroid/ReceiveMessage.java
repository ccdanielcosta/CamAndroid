package com.example.carloscosta.camandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.protobuf.format.JsonFormat;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class ReceiveMessage implements Runnable {
    DatagramSocket datagramSocket;
    Activity a;
    TextView textStatid, textLat, textLong, textTime;
    private TextView txtProgress;
    private ProgressBar progressBar;
    Resources res = a.getResources();
    Drawable drawable = res.getDrawable(R.drawable.custom_progressbar_drawable);

    public ReceiveMessage(DatagramSocket datagramSocket, Activity act) {
        this.datagramSocket = datagramSocket; this.a = act;
        txtProgress = (TextView) act.findViewById(R.id.txtProgress);

        final ProgressBar progressBar = (ProgressBar) act.findViewById(R.id.progressBar);

        textStatid = (TextView) act.findViewById(R.id.textStationID);
         textLat = (TextView) act.findViewById(R.id.textLatitude);
         textLong = (TextView) act.findViewById(R.id.textLongitude);
        textTime = (TextView) act.findViewById(R.id.textTimestamp);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void run() {
        byte[] data = new byte[2000];
        DatagramPacket datagramPacket;
        int i  = 0;

        System.out.println("Waiting for data");


        progressBar.setProgress(0);   // Main Progress
        progressBar.setSecondaryProgress(100); // Secondary Progress
        progressBar.setMax(100); // Maximum Progress
        progressBar.setProgressDrawable(drawable);
        progressBar.setVisibility(View.VISIBLE);
        // progressBar.setProgress(i++);
        txtProgress.setText(i + "Waiting for data");
        System.out.println("RECEBIDO");
        byte[] recData;
        int len;


        while (true) try {



            datagramPacket = new DatagramPacket(data, data.length);
            datagramSocket.receive(datagramPacket);




            len = datagramPacket.getLength();
            recData = new byte[len];
            System.arraycopy(datagramPacket.getData(), 0, recData, 0, datagramPacket.getLength());

            Message.MessageP2A messageP2A = Message.MessageP2A.parseFrom(recData);
           // JsonFormat.printToString(messageP2A);

          //  System.out.println("UU: " + s);


            //System.out.println("Received P2A Message: " + messageP2A.toString());
            //  System.out.println("3: " +  JsonFormat.printToString(protoMessage));

            String jsonString = "";
            JsonFormat jsonFormat = new JsonFormat();
            jsonString = jsonFormat.printToString(messageP2A);

            System.out.println("Received P2A Message: " + jsonString);

            JSONObject jsonObj = new JSONObject(jsonString.toString());
            System.out.println("---------------------------");
            System.out.println(jsonObj.get("send_request"));

            JSONObject jObj = (JSONObject) jsonObj.get("send_request");
            JSONObject cams = (JSONObject) jObj.get("cam");

            //int acceleration, int heading, double latitude, double longitude, double speed, int id, long timestamp,  int yawRate, int alert
            CAM cam = new CAM();
            cam.setStationId(cams.getInt("station_id"));
            cam.setTimestamp(cams.getDouble("timestamp"));
            cam.setLatitude(cams.getDouble("latitude"));
            cam.setLongitude(cams.getDouble("longitude"));
            cam.setAcceleration(cams.getInt("acceleration"));
            cam.setHeading(cams.getDouble("heading"));
            cam.setSpeed(cams.getDouble("speed"));
            cam.setYaw_rate(cams.getInt("yaw_rate"));
            cam.setAlert(cams.getInt("alert"));

            //Updating messages on Android
            SetMessages sm = new SetMessages(a);
            sm.setValues(cam.getAcceleration(),cam.getHeading(),cam.getLatitude(),cam.getLongitude(),cam.getSpeed(),cam.getStationId(),cam.getTimestamp(),cam.getYaw_rate(),cam.getAlert());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
