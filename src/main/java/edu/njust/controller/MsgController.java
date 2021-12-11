package edu.njust.controller;

import edu.njust.api.CommonResult;
//import edu.njust.api.CommonResult;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/msg")
@CrossOrigin
public class MsgController {

    @GetMapping(value = "/get")
    public CommonResult<String> getMessage() {
        String pathName = "udpData.txt";
        String msg = "";
        try (FileReader reader = new FileReader(pathName);
             BufferedReader br = new BufferedReader(reader)) {
            msg = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.success(msg);
    }

    @PostMapping(value = "/json")
    public String recJson(@RequestBody Map<String, String> data) {
//        JSONObject object = new JSONObject(data);
        // json转字符串输出
        return data.toString();
    }
    @PostMapping(value = "/write")
    public void writeText(@RequestBody Map<String, String> set) {
        try {
            File file = new File("udpData.txt");
            if(!file.isFile()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(set.get("line")+"\r\n");
            bw.flush();
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
