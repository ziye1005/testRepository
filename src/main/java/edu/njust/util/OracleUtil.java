//package edu.njust.util;
//
//import org.neo4j.driver.v1.Driver;
//import org.neo4j.driver.v1.Session;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class OracleUtil {
//
//    private final Logger logger = LoggerFactory.getLogger(OracleUtil.class);
//
//    private Driver oracleDriver;
//
//    public OracleUtil(Driver oracleDriver) {
//        this.oracleDriver = oracleDriver;
//    }
//
//    /**
//     * Oracle能否连接成功
//     *
//     * @return 连接标识
//     */
//    public boolean isOracleOpen() {
//        try (Session session = oracleDriver.session()) {
//            return session.isOpen();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//
//    public List<List<Object>> query(String sql) {
//        Connection conn = null;
//        Statement st = null;
//        ResultSet res = null;
//        List<List<Object>> list = new ArrayList<List<Object>>();
//        try {
//            conn = getConnection();
//            st = conn.createStatement();
//            res = st.executeQuery(sql);
//            int colnum = res.getMetaData().getColumnCount();
//            while (res.next()) {
//                List<Object> innerList = new ArrayList<Object>();
//                for (int i = 1; i <= colnum; i++) {
//                    innerList.add(res.getObject(i));
//                }
//                list.add(innerList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            closeAll(conn, st, res);
//        }
//        return list;
//    }
//
//    public List<String> queryColumn(String sql) {
//        Connection conn = null;
//        Statement st = null;
//        ResultSet res = null;
//        List<String> list = new ArrayList<String>();
//        try {
//            conn = getConnection();
//            st = conn.createStatement();
//            res = st.executeQuery(sql);
//            ResultSetMetaData rsm = res.getMetaData();
//            int columnNum = rsm.getColumnCount();
//            for (int i = 1; i <= columnNum; i++) {
//                list.add(rsm.getColumnName(i));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            closeAll(conn, st, res);
//        }
//        return list;
//    }
//
//    public String queryTableName(String sql) {
//        Connection conn = null;
//        Statement st = null;
//        ResultSet res = null;
//        String tableName = "";
//        try {
//            conn = getConnection();
//            st = conn.createStatement();
//            res = st.executeQuery(sql);
//            ResultSetMetaData rsm = res.getMetaData();
//            tableName = rsm.getTableName(0);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            closeAll(conn, st, res);
//        }
//        return tableName;
//    }
//
//    public List<String> queryColumnType(String sql) {
//        Connection conn = null;
//        Statement st = null;
//        ResultSet res = null;
//        List<String> list = new ArrayList<String>();
//        try {
//            conn = getConnection();
//            st = conn.createStatement();
//            res = st.executeQuery(sql);
//            ResultSetMetaData rsm = res.getMetaData();
//            int columnNum = rsm.getColumnCount();
//            for (int i = 1; i <= columnNum; i++) {
//                list.add(rsm.getColumnTypeName(i));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            closeAll(conn, st, res);
//        }
//        return list;
//    }
//}
