package edu.njust.util;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;
import java.util.Properties;

public class JdbcUtil {

    private static String driver; 	// 驱动名
    private static String url; 		// 数据库地址
    private static String userName; // 数据库用户名
    private static String password; // 数据库密码

    /*
     * 读取JDBC配置文件(写在静态代码块中，这样读取只进行一次)
     */
    static {
        // 1.创建Properties集合类
        Properties pro = new Properties();
        try {
            // 2.通过反射机制读取配置文件
//            pro.load(JdbcUtil.class.getClassLoader().getResourceAsStream("properties/jdbc.properties"));
            pro.load(JdbcUtil.class.getClassLoader().getResourceAsStream("generator.properties"));
            // 3.获取配置文件中的参数并赋值
            driver = pro.getProperty("jdbc.driverClass");
            url = pro.getProperty("jdbc.connectionURL");
            userName = pro.getProperty("jdbc.userId");
            password = pro.getProperty("jdbc.password");
            // 4.注册驱动
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /*
     * 获取连接
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    /*
     * 关闭连接
     */
    public static void close(ResultSet rs, PreparedStatement pst, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
