package com.example.carloscosta.camandroid;

import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {

    int cPort = 8080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ReceiveMessage p = null;
        try {
            p = new ReceiveMessage(new DatagramSocket(8080));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new Thread(p).start();



        }

}

