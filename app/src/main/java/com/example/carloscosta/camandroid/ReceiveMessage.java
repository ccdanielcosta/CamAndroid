package com.example.carloscosta.camandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
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


    public ReceiveMessage(DatagramSocket datagramSocket, Activity act) {
        this.datagramSocket = datagramSocket; this.a = act;
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
        System.out.println("Waiting for data");
        byte[] recData;
        int len;
        while (true) try {
            System.out.println("RECEBIDO");
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

            setValues(cam.getAcceleration(),cam.getHeading(),cam.getLatitude(),cam.getLongitude(),cam.getSpeed(),cam.getStationId(),cam.getTimestamp(),cam.getYaw_rate(),cam.getAlert());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Handler mHandlerTime = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
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


    public void setValues( int acceleration, double heading, double latitude, double longitude, double speed, int id, double timestamp,  int yawRate, int alert){

        android.os.Message msgTime = new android.os.Message();
        msgTime.obj = Double.toString(timestamp);
        mHandlerTime.sendMessage(msgTime);

        android.os.Message msgLat = new android.os.Message();
        msgLat.obj = Double.toString(latitude);
        mHandlerLat.sendMessage(msgLat);

        android.os.Message msgLong = new android.os.Message();
        msgLong.obj = Double.toString(longitude);
        mHandlerLong.sendMessage(msgLong);


    }








}
