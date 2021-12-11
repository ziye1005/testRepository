package edu.njust.server;


//import java.net.sf.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/*
json数据监听端口
 */
public class port01 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12001);
            System.out.println("HTTP server is ready and is listening on port 10211\n");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String json = "";
                String temp = "";
                int i = 1001;
                while((temp=in.readLine())!=null) {
                    json = json + temp + "\n";
                    // 对json基本类型解析
//                    JSONObject object = JSONObject.fromObject(temp);
//                    String str = object.toString();
                    System.out.println(json);
                }
                in.close();
            }
        } catch(Exception e) {
            System.out.println("ERROR:" + e.getMessage());
            System.exit(1);
        }
    }
}

