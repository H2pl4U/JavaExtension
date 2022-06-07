package com.h2pl4u.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serv = new ServerSocket(8888)) {
            System.out.printf("Bind Port %d\n", serv.getLocalPort());
            Socket socket = null;
            while (true) {
                // 接收连接，如果没有连接，accept() 方法会阻塞
                socket = serv.accept();

                // 获取输入流，并使用 BufferedInputStream 和 InputStreamReader 装饰，方便以字符流的形式处理，方便一行行读取内容
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String msg = null;
                    char[] cbuf = new char[1024];
                    int len = 0;
                    // 循环读取输入流中的内容
                    while ((len = in.read(cbuf, 0, 1024)) != -1) {
                        msg = new String(cbuf, 0, len);
                        // 如果检测到 "Bye" ，则跳出循环，不再读取输入流中内容。
                        System.out.printf("Received Message --> %s \n", msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
