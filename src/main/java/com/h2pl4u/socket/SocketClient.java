package com.h2pl4u.socket;

import io.netty.util.internal.StringUtil;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) throws Exception {
        Scanner cin=new Scanner(System.in);
        while (true) {
            System.out.println("input text:");
            String nextLine = cin.nextLine();
            sendTcpMessage(nextLine);
        }
    }

    public static void sendTcpMessage(String message) throws Exception {
        if (StringUtil.isNullOrEmpty(message)) {
            return;
        }
        Socket s = new Socket(InetAddress.getByName("localhost"), 8888);
        OutputStream os = s.getOutputStream();
        os.write(message.getBytes());
        s.close();
    }
}
