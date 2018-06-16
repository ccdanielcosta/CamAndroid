package com.example.carloscosta.camandroid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveMessage implements Runnable {
    DatagramSocket datagramSocket;

    public ReceiveMessage(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void run() {
        byte[] data = new byte[2000];
        DatagramPacket datagramPacket;
        System.out.println("Waiting for data");
        byte[] recData;
        int len;
        while (true) {
            try {
                System.out.println("RECEBIDO");
                datagramPacket = new DatagramPacket(data, data.length);
                datagramSocket.receive(datagramPacket);
                len = datagramPacket.getLength();
                recData = new byte[len];
                System.arraycopy(datagramPacket.getData(), 0, recData, 0, datagramPacket.getLength());
                Message.MessageP2A messageP2A = Message.MessageP2A.parseFrom(recData);
                System.out.println("Received P2A Message: " + messageP2A.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
