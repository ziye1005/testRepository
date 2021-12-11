package edu.njust.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpSender {
    public static final String SERVER_HOSTNAME = "localhost";
    // 目标端口
    public static final int SERVER_PORT = 4001;
    // 本地端口（不能跟用springboot一样的端口）
    public static final int LOCAL_PORT = 8082;

    // 发送String
    public void send(String data) {
        try {
            // 1，创建udp服务。通过DatagramSocket对象。
            DatagramSocket socket = new DatagramSocket(LOCAL_PORT);
            // 2，确定数据，并封装成数据包。DatagramPacket(byte[] buf, int length, InetAddress, int port)
            byte[] buf = data.getBytes();
            DatagramPacket dp = new DatagramPacket(
                    buf, buf.length, InetAddress.getByName(SERVER_HOSTNAME), SERVER_PORT
            );
            // 3，通过socket服务，将已有的数据包发送出去。通过send方法。
            socket.send(dp);
            // 4，关闭资源。
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
