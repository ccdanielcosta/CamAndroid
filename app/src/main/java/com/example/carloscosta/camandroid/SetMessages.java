package com.example.carloscosta.camandroid;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;

import java.net.DatagramSocket;

public class SetMessages {

    Activity a;
    TextView textStatid, textLat, textLong, textTime,textIpPort;

    public SetMessages(Activity act) {
        this.a = act;
        textStatid = (TextView) act.findViewById(R.id.textStationID);
        textLat = (TextView) act.findViewById(R.id.textLatitude);
        textLong = (TextView) act.findViewById(R.id.textLongitude);
        textTime = (TextView) act.findViewById(R.id.textTimestamp);
        textIpPort = (TextView) act.findViewById(R.id.textIP);
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


    }



}
