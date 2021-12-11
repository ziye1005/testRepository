package edu.njust.udp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Udp;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Map;

public class UdpServer {

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows.from(Udp.inboundAdapter(6000)).channel("udpChannel").get();
    }

    @Bean
    public UnicastReceivingChannelAdapter getUnicastReceivingChannelAdapter() {
        UnicastReceivingChannelAdapter adapter = new UnicastReceivingChannelAdapter(6000);//实例化一个udp 8081端口
        adapter.setOutputChannelName("udpChannel");
        System.out.println(adapter.getPort());
        return adapter;
    }

    @Transformer(inputChannel="udpChannel",outputChannel="udpString")
    public String transformer(Message<?> message) {
        //把接收的数据转化为字符串
        String s = new String((byte[])message.getPayload());
        System.out.println(s);
        return s;
    }

    @Filter(inputChannel="udpString",outputChannel="udpFilter")
    public boolean filter(String message) {
        return message.startsWith("#");//如果接收数据开头不是#直接过滤掉
    }

    @Router(inputChannel="udpFilter")
    public String routing(String message) {
        if(message.contains("1")) {//当接收数据包含数字1时
            return "udpRoute1";
        }
        else {
            return "udpRoute2";
        }
    }

    @ServiceActivator(inputChannel="udpRoute1")
    public void udpMessageHandle(String message) {
        System.out.println("udp1:" +message);
    }

    @ServiceActivator(inputChannel="udpRoute2")
    public void udpMessageHandle2(String message) {
        System.out.println("udp2:" +message);
    }

    //发送消息
    public void sendMessage(String message) {
        System.out.println("发送UDP: " + message);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 8082);
        byte[] udpMessage = message.getBytes();
        DatagramPacket datagramPacket = null;
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramPacket = new DatagramPacket(udpMessage, udpMessage.length, inetSocketAddress);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage() + e);
        }
        System.out.println("发送成功");
    }

}
