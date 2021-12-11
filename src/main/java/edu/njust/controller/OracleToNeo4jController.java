package edu.njust.controller;

import com.alibaba.fastjson.JSON;
import edu.njust.api.CommonResult;
import edu.njust.service.IKnowGraphControlService;
import edu.njust.vo.NodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/RE")
@CrossOrigin
public class OracleToNeo4jController {
    private static String USERNAMR = "scott";
    private static String PASSWORD = "120308";
    private static String DRVIER = "oracle.jdbc.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@localhost:1521:orcl";

    Connection conn = null;
    // 创建预编译语句对象，一般都是用这个而不用Statement
    PreparedStatement pstm = null;
    ResultSet rs = null;

    @Autowired
    IKnowGraphControlService service;

    /**
     * 获取Connection对象
     *
     * @return
     */
    public Connection getConnection() {
        try {
            Class.forName(DRVIER);
            conn = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
            System.out.println("成功连接数据库");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("class not find !", e);
        } catch (SQLException e) {
            throw new RuntimeException("get connection error!", e);
        }

        return conn;
    }

    /**
     * 释放资源
     */
    public void ReleaseResource() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @GetMapping("/createNode1")
    public void createNode1(@RequestParam(value = "TableName") String TableName,
                            @RequestParam(value = "type") String type
    ) throws SQLException {
        String sql = "select * from " + TableName + " where 1 = 1";
        String domain = "知识图谱";
        String name = null;
        conn = getConnection();
        pstm = conn.prepareStatement(sql);
        rs = pstm.executeQuery();
        ResultSetMetaData data = rs.getMetaData();
        List<List<String>> colValueTotal = new ArrayList<>();
        int count = 0;
        while (rs.next()) {  //循环遍历结果集
            count++;
            String property = "{";
            List<String> colNameTotal = new ArrayList<>();
            List<String> colValueTemp = new ArrayList<>();
            for (int i = 1; i <= data.getColumnCount(); i++) {
                int columnCount = data.getColumnCount();//获得总列数
                String columnName = data.getColumnName(i);//获得指定列的列名
                String columnValue = rs.getString(i);//获得指定列的列值
                colNameTotal.add(columnName);       //列名list
                colValueTemp.add(columnValue);      //查询内容的list
                if (i == 1)
                    name = columnValue;
                else if (i > 1 && i < columnCount) {
                    property += "\"" + columnName + "\":\"" + columnValue + "\",";
                } else {
                    property += "\"" + columnName + "\":\"" + columnValue + "\"}";
                }
            }
            System.out.println(domain + "    " + name + "    " + property);
            Map<String, Object> maps = (Map) JSON.parse(property);
            maps.put("name", name);
            Integer newId = service.createNode(domain, type, maps);
            NodeVO nodeVO = service.queryNode(newId);
            System.out.println(CommonResult.success(nodeVO));

            if (count == 1) {
                for (String string : colNameTotal) {
                    System.out.print(string + " ");
                }
                System.out.println();
            }
            for (String string : colValueTemp) {
                System.out.print(string + " ");
            }
            System.out.println();
            colValueTotal.add(colValueTemp);
        }
        System.out.println("查询到" + count + "条结果");
        ReleaseResource();
    }
}

