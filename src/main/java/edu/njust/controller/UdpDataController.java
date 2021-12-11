package edu.njust.controller;

import edu.njust.model.UdpDataModel;
//import edu.njust.service.UdpDataService;
import edu.njust.udp.UdpPortListener;
import edu.njust.udp.UdpSender;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

//import java.sql.Timestamp;
//import java.util.Date;
import java.util.List;

@RestController
@Scope("prototype")
@RequestMapping("/udp")//一级URL
@CrossOrigin //允许跨域
public class UdpDataController {
    //调用service
//    @Autowired
//    private UdpDataService udpDataService;
    //查
//    @GetMapping(value = "/select")
//    public List<UdpDataModel> selectData() throws Exception {
//        return udpDataService.selectData();
//    }
    //增
//    @PostMapping(value = "/insert")
//    public void insert(@RequestParam(value="origin")String origin) throws Exception {
//        udpDataService.insertData(origin, new Timestamp(new Date().getTime()));
//    }

    //查
    @GetMapping(value = "/select")
    public List<UdpDataModel> select() throws Exception {
        return UdpPortListener.udpDataModelList;
    }

    //发送
    @PostMapping(value = "/send")
    public void send(@RequestParam(value="sendData")String sendData) throws Exception {
        UdpSender udpSender = new UdpSender();
        udpSender.send(sendData);
    }


}
