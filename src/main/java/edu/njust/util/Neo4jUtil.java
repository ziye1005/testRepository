package edu.njust.util;

import com.alibaba.fastjson.JSON;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.neo4j.driver.v1.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * v1: 增加注释
 *
 * @author gudongxian
 */
@Component
public class Neo4jUtil {

    private final Logger logger = LoggerFactory.getLogger(Neo4jUtil.class);

    // 常用查询语句
    public static final String CALL_SCHEMA = "CALL db.schema.visualization()";

    private Driver neo4jDriver;

    public Neo4jUtil(Driver neo4jDriver) {
        this.neo4jDriver = neo4jDriver;
    }

    /**
     * Neo4j能否连接成功
     *
     * @return 连接标识
     */
    public boolean isNeo4jOpen() {
        try (Session session = neo4jDriver.session()) {
            logger.info("连接成功：" + session.isOpen());
            return session.isOpen();
        } catch (Exception e) {
            return false;
        }
    }

    public String buildCypherByMap(Map<String, Object> data, String label) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("MERGE (node:%s {", label));
        for (String key : data.keySet()) {
            sb.append(key).append(":$").append(key).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}) RETURN id(node)");
        return sb.toString();
    }

    /**
     * 执行cypher语句
     *
     * @param cypherSql
     * @return 执行结果
     */
    public StatementResult executeCypherSql(String cypherSql) {
        StatementResult result = null;
        try (Session session = neo4jDriver.session()) {
            logger.info("执行Cypher语句:{}", cypherSql);
            result = session.run(cypherSql);
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * 执行Cypher语句
     *
     * @param cypherSql 语句
     * @param map       参数
     */
    public void runCypher(String cypherSql, Map<String, Object> map) {
        StatementResult result = null;
        try (Session session = neo4jDriver.session()) {
            logger.info("执行Cypher语句:{}", cypherSql);
            for (Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof BigDecimal) {
                    map.put(entry.getKey(), ((BigDecimal) entry.getValue()).floatValue());
                }
            }
            session.run(cypherSql, map);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 创建实体
     *
     * @param map   实体数据
     * @param label 实体类型
     * @return 实体Id
     */
    public long createEntity(Map<String, Object> map, String label) {
        String cypherSql = buildCypherByMap(map, label);
        StatementResult result = null;
        try (Session session = neo4jDriver.session()) {
            logger.info("执行Cypher语句:{}", cypherSql);
            for (Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof BigDecimal) {
                    map.put(entry.getKey(), ((BigDecimal) entry.getValue()).floatValue());
                }
            }
            StatementResult cypherResult = session.run(cypherSql, map);
            if (cypherResult.hasNext()) {
                Record record = cypherResult.next();
                for (Value value : record.values()) {
                    return value.asLong();
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return -1;
    }

    public static final String CREATE_R_ID = "MATCH (h),(t) WHERE id(h)=$hid AND id(t)=$tid " +
            "MERGE (h)-[r:%s]->(t) RETURN id(r)";

    /**
     * 创建关系
     *
     * @param headId 头实体Id
     * @param tailId 尾实体Id
     * @param rel    关系名
     * @return 关系Id
     */
    public long createRelation(long headId, long tailId, String rel) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("hid", headId);
        map.put("tid", tailId);
        StatementResult result = null;
        try (Session session = neo4jDriver.session()) {
            StatementResult cypherResult = session.run(String.format(CREATE_R_ID, rel), map);
            if (cypherResult.hasNext()) {
                Record record = cypherResult.next();
                for (Value value : record.values()) {
                    return value.asLong();
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return -1;
    }
    /**
     *给节点添加属性
     *
     */
    public void setProperty(String cypher) {
        executeCypherSql(cypher);
    }
    /**
     * 执行cypher，解析单个实体数据
     *
     * @param cypherSql 查询语句
     * @return 实体的Map格式
     */
    public HashMap<String, Object> GetEntityMap(String cypherSql) {
        HashMap<String, Object> rss = new HashMap<>();
        try {
            StatementResult result = executeCypherSql(cypherSql);
            if (result.hasNext()) {
                List<Record> records = result.list();
                for (Record recordItem : records) {
                    for (Value value : recordItem.values()) {
                        if (value.type().name().equals("NODE")) {
                            Node noe4jNode = value.asNode();
                            Map<String, Object> map = noe4jNode.asMap();
                            for (Entry<String, Object> entry : map.entrySet()) {
                                String key = entry.getKey();
                                if (rss.containsKey(key)) {
                                    String oldValue = rss.get(key).toString();
                                    String newValue = oldValue + "," + entry.getValue();
                                    rss.replace(key, newValue);
                                } else {
                                    rss.put(key, entry.getValue());
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rss;
    }

    /**
     * 解析多个知识图谱中节点
     *
     * @param cypherSql
     * @return Map形式的列表
     */
    public List<HashMap<String, Object>> GetGraphNode(String cypherSql) {
        List<HashMap<String, Object>> ents = new ArrayList<>();
        try {
            StatementResult result = executeCypherSql(cypherSql);
            if (result.hasNext()) {
                List<Record> records = result.list();
                // 遍历每个记录
                for (Record recordItem : records) {
                    // 获取每条记录的键值对列表
                    List<Pair<String, Value>> f = recordItem.fields();
                    for (Pair<String, Value> pair : f) {
                        HashMap<String, Object> rss = new HashMap<>();
                        String typeName = pair.value().type().name();
                        // 当前数据尾节点数据
                        if ("NODE".equals(typeName)) {
                            Node noe4jNode = pair.value().asNode();
                            String uuid = String.valueOf(noe4jNode.id());
                            Map<String, Object> map = noe4jNode.asMap();
                            for (Entry<String, Object> entry : map.entrySet()) {
                                String key = entry.getKey();
                                rss.put(key, entry.getValue());
                            }
                            // 知识图谱的id作为uuid
                            rss.put("uuid", uuid);
                            ents.add(rss);
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ents;
    }

    /**
     * 解析查询结果中的关系数据
     *
     * @param cypherSql
     * @return 关系列表
     */
    public List<HashMap<String, Object>> GetGraphRelationShip(String cypherSql) {
        List<HashMap<String, Object>> ents = new ArrayList<>();
        try {
            StatementResult result = executeCypherSql(cypherSql);
            if (result.hasNext()) {
                List<Record> records = result.list();
                for (Record recordItem : records) {
                    List<Pair<String, Value>> f = recordItem.fields();
                    for (Pair<String, Value> pair : f) {
                        HashMap<String, Object> rss = new HashMap<>();
                        String typeName = pair.value().type().name();
                        if ("RELATIONSHIP".equals(typeName)) {
                            Relationship rship = pair.value().asRelationship();
                            String uuid = String.valueOf(rship.id());
                            String sourceId = String.valueOf(rship.startNodeId());
                            String targetId = String.valueOf(rship.endNodeId());
                            Map<String, Object> map = rship.asMap();
                            for (Entry<String, Object> entry : map.entrySet()) {
                                String key = entry.getKey();
                                rss.put(key, entry.getValue());
                            }
                            rss.put("uuid", uuid);
                            rss.put("sourceId", sourceId);
                            rss.put("targetId", targetId);
                            ents.add(rss);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ents;
    }

    /**
     * 解析查询结果中的节点和关系数据
     *
     * @param cypherSql 查询语句
     * @return 数据列表
     */
    public List<HashMap<String, Object>> GetGraphItem(String cypherSql) {
        List<HashMap<String, Object>> ents = new ArrayList<>();
        List<String> nodeids = new ArrayList<>();
        List<String> shipids = new ArrayList<>();
        try {
            StatementResult result = executeCypherSql(cypherSql);
            if (result.hasNext()) {
                List<Record> records = result.list();
                for (Record recordItem : records) {
                    List<Pair<String, Value>> f = recordItem.fields();
                    HashMap<String, Object> rss = new HashMap<>();
                    for (Pair<String, Value> pair : f) {
                        String typeName = pair.value().type().name();
                        // 实体节点
                        if ("NODE".equals(typeName)) {
                            Node neo4jNode = pair.value().asNode();
                            String uuid = String.valueOf(neo4jNode.id());
                            if (!nodeids.contains(uuid)) {
                                Map<String, Object> map = neo4jNode.asMap();
                                for (Entry<String, Object> entry : map.entrySet()) {
                                    String key = entry.getKey();
                                    rss.put(key, entry.getValue());
                                }
                                rss.put("uuid", uuid);
                            }
                        } else if ("RELATIONSHIP".equals(typeName)) {
                            // 关系节点
                            Relationship rship = pair.value().asRelationship();
                            String uuid = String.valueOf(rship.id());
                            if (!shipids.contains(uuid)) {
                                String sourceId = String.valueOf(rship.startNodeId());
                                String targetId = String.valueOf(rship.endNodeId());
                                Map<String, Object> map = rship.asMap();
                                for (Entry<String, Object> entry : map.entrySet()) {
                                    String key = entry.getKey();
                                    rss.put(key, entry.getValue());
                                }
                                rss.put("uuid", uuid);
                                rss.put("sourceId", sourceId);
                                rss.put("targetId", targetId);
                            }
                        } else {
                            rss.put(pair.key(), pair.value().toString());
                        }
                    }
                    ents.add(rss);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ents;
    }


    /**
     * 获取值类型的结果,如count,uuid
     *
     * @param cypherSql
     * @return 1 2 3 等数字类型
     */
    public long GetGraphValue(String cypherSql) {
        long val = 0;
        try {
            StatementResult cypherResult = executeCypherSql(cypherSql);
            if (cypherResult.hasNext()) {
                Record record = cypherResult.next();
                for (Value value : record.values()) {
                    val = value.asLong();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }

    /**
     * 获取图谱节点和关系
     *
     * @param cypherSql
     * @return 节点和关系数据
     */
    public HashMap<String, Object> GetGraphNodeAndShip(String cypherSql) {
        HashMap<String, Object> mo = new HashMap<>(2);
        try {
            StatementResult result = executeCypherSql(cypherSql);
            if (result.hasNext()) {
                List<Record> records = result.list();
                List<HashMap<String, Object>> ents = new ArrayList<>();
                List<HashMap<String, Object>> ships = new ArrayList<>();
                List<Long> uuids = new ArrayList<>();
                List<Long> relIDS = new ArrayList<>();
                for (Record recordItem : records) {
                    List<Pair<String, Value>> f = recordItem.fields();
                    for (Pair<String, Value> pair : f) {
                        HashMap<String, Object> rships = new HashMap<>();
                        HashMap<String, Object> rss = new HashMap<>();
                        String typeName = pair.value().type().name();
                        if ("NULL".equals(typeName)) {
                        } else if ("NODE".equals(typeName)) {
                            Node neo4jNode = pair.value().asNode();
                            Map<String, Object> map = neo4jNode.asMap();
                            Long uuid = neo4jNode.id();
                            rss.put("type", neo4jNode.labels());
                            if (!uuids.contains(uuid)) {
                                rss.putAll(map);
                                rss.put("uuid", uuid);
                                uuids.add(uuid);
                            }
                            if (!rss.isEmpty()) {
                                ents.add(rss);
                            }
                        } else if ("RELATIONSHIP".equals(typeName)) {
                            Relationship rship = pair.value().asRelationship();
                            Long uuid = rship.id();
                            if (!relIDS.contains(uuid)) {
                                String sourceId = String.valueOf(rship.startNodeId());
                                String targetId = String.valueOf(rship.endNodeId());
                                Map<String, Object> map = rship.asMap();
                                rships.putAll(map);
                                rships.put("uuid", uuid);
                                rships.put("sourceId", sourceId);
                                rships.put("targetId", targetId);
                                rships.put("type", rship.type());
                                relIDS.add(uuid);
                                if (!rships.isEmpty()) {
                                    ships.add(rships);
                                }
                            }

                        } else if ("PATH".equals(typeName)) {
                            // 解析路径
                            Path path = pair.value().asPath();
                            Node startNode = path.start();
                            Map<String, Object> startNodeMap = startNode.asMap();

                            // 解析起点
                            Long startNodeUuid = path.start().id();
                            if (!uuids.contains(startNodeUuid)) {
                                rss = new HashMap<>(startNodeMap);
                                rss.put("uuid", startNodeUuid);
                                rss.put("type", startNode.labels());
                                uuids.add(startNodeUuid);
                                if (!rss.isEmpty()) {
                                    ents.add(rss);
                                }
                            }
                            // 解析终点
                            Node endNode = path.end();
                            Map<String, Object> endNodeMap = endNode.asMap();
                            Long endNodeUuid = path.end().id();
                            if (!uuids.contains(endNodeUuid)) {
                                rss = new HashMap<>(endNodeMap);
                                rss.put("uuid", endNodeUuid);
                                rss.put("type", endNode.labels());
                                uuids.add(endNodeUuid);
                                if (!rss.isEmpty()) {
                                    ents.add(rss);
                                }
                            }
                            // 获取路径上的节点
                            for (Node next : path.nodes()) {
                                Long uuid = next.id();
                                if (!uuids.contains(uuid)) {
                                    rss = new HashMap<>(next.asMap());
                                    rss.put("uuid", uuid);
                                    rss.put("type", next.labels());
                                    uuids.add(uuid);
                                    if (!rss.isEmpty()) {
                                        ents.add(rss);
                                    }
                                }
                            }

                            // 遍历关系
                            for (Relationship next : path.relationships()) {
                                Long uuid = next.id();
                                if (!relIDS.contains(uuid)) {
                                    rships = new HashMap<>(next.asMap());
                                    Long sourceId = next.startNodeId();
                                    Long targetId = next.endNodeId();
                                    String type = String.valueOf(next.type());
                                    rships.put("uuid", uuid);
                                    rships.put("sourceId", sourceId);
                                    rships.put("targetId", targetId);
                                    rships.put("type", type);
                                    relIDS.add(uuid);
                                    if (!rships.isEmpty()) {
                                        ships.add(rships);
                                    }
                                }
                            }
                        } else if (typeName.contains("LIST")) {
                            Iterable<Value> val = pair.value().values();
                            Iterator<Value> iterator = val.iterator();
                            while (iterator.hasNext()) {
                                Value next = iterator.next();
                                String type = next.type().name();
                                // 对 node进行解析
                                if ("NODE".equals(type)) {
                                    Node neo4jNode = next.asNode();
                                    Map<String, Object> map = neo4jNode.asMap();
                                    Long uuid = neo4jNode.id();
                                    rss.put("type", neo4jNode.labels());
                                    if (!uuids.contains(uuid)) {
                                        rss.putAll(map);
                                        rss.put("uuid", uuid);
                                        uuids.add(uuid);
                                    }
                                    if (!rss.isEmpty()) {
                                        ents.add(new HashMap<>(rss));
                                    }
                                } else if ("RELATIONSHIP".equals(type)) {
                                    Relationship rship = next.asRelationship();
                                    Long uuid = rship.id();
                                    if (!relIDS.contains(uuid)) {
                                        Long sourceId = rship.startNodeId();
                                        Long targetId = rship.endNodeId();
                                        rships.putAll(rship.asMap());
                                        rships.put("uuid", uuid);
                                        rships.put("sourceId", sourceId);
                                        rships.put("targetId", targetId);
                                        rships.put("type", rship.type());
                                        relIDS.add(uuid);

                                        if (!rships.isEmpty()) {
                                            ships.add(new HashMap<>(rships));
                                        }
                                    }
                                }
                            }

                        } else if (typeName.contains("MAP")) {
                            rss.put(pair.key(), pair.value().asMap());
                        } else {
                            rss.put(pair.key(), pair.value().toString());
                            if (!rss.isEmpty()) {
                                ents.add(rss);
                            }
                        }

                    }
                }
                mo.put("node", ents);
                mo.put("relationship", ships);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return mo;
    }

    /**
     * 匹配所有类型的节点,可以是节点,关系,数值,路径
     *
     * @param cypherSql
     * @return
     */
    public List<HashMap<String, Object>> GetEntityList(String cypherSql) {
        List<HashMap<String, Object>> ents = new ArrayList<>();
        try {
            StatementResult result = executeCypherSql(cypherSql);
            if (result.hasNext()) {
                List<Record> records = result.list();
                for (Record recordItem : records) {
                    HashMap<String, Object> rss = new HashMap<>();
                    List<Pair<String, Value>> f = recordItem.fields();
                    for (Pair<String, Value> pair : f) {
                        String typeName = pair.value().type().name();
                        if (typeName.equals("NULL")) {
                            continue;
                        } else if (typeName.equals("NODE")) {
                            Node noe4jNode = pair.value().asNode();
                            Map<String, Object> map = noe4jNode.asMap();
                            for (Entry<String, Object> entry : map.entrySet()) {
                                String key = entry.getKey();
                                rss.put(key, entry.getValue());
                            }
                        } else if (typeName.equals("RELATIONSHIP")) {
                            Relationship rship = pair.value().asRelationship();
                            Map<String, Object> map = rship.asMap();
                            for (Entry<String, Object> entry : map.entrySet()) {
                                String key = entry.getKey();
                                rss.put(key, entry.getValue());
                            }
                        } else if (typeName.equals("PATH")) {

                        } else if (typeName.contains("LIST")) {
                            rss.put(pair.key(), pair.value().asList());
                        } else if (typeName.contains("MAP")) {
                            rss.put(pair.key(), pair.value().asMap());
                        } else {
                            rss.put(pair.key(), pair.value().toString());
                        }
                    }
                    ents.add(rss);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ents;
    }

    public <T> List<T> GetEntityItemList(String cypherSql, Class<T> type) {
        List<HashMap<String, Object>> ents = GetGraphNode(cypherSql);
        List<T> model = HashMapToObject(ents, type);
        return model;
    }

    public <T> T GetEntityItem(String cypherSql, Class<T> type) {
        HashMap<String, Object> rss = new HashMap<>();
        try {
            StatementResult result = executeCypherSql(cypherSql);
            if (result.hasNext()) {
                Record record = result.next();
                for (Value value : record.values()) {
                    if (value.type().name().equals("NODE")) {// 结果里面只要类型为节点的值
                        Node noe4jNode = value.asNode();
                        Map<String, Object> map = noe4jNode.asMap();
                        for (Entry<String, Object> entry : map.entrySet()) {
                            String key = entry.getKey();
                            if (rss.containsKey(key)) {
                                String oldValue = rss.get(key).toString();
                                String newValue = oldValue + "," + entry.getValue();
                                rss.replace(key, newValue);
                            } else {
                                rss.put(key, entry.getValue());
                            }
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        T model = HashMapToObjectItem(rss, type);
        return model;
    }

    public HashMap<String, Object> GetEntity(String cypherSql) {
        HashMap<String, Object> rss = new HashMap<>();
        try {
            StatementResult result = executeCypherSql(cypherSql);
            if (result.hasNext()) {
                Record record = result.next();
                for (Value value : record.values()) {
                    String t = value.type().name();
                    if (value.type().name().equals("NODE")) {// 结果里面只要类型为节点的值
                        Node noe4jNode = value.asNode();
                        Map<String, Object> map = noe4jNode.asMap();
                        for (Entry<String, Object> entry : map.entrySet()) {
                            String key = entry.getKey();
                            if (rss.containsKey(key)) {
                                String oldValue = rss.get(key).toString();
                                String newValue = oldValue + "," + entry.getValue();
                                rss.replace(key, newValue);
                            } else {
                                rss.put(key, entry.getValue());
                            }
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rss;
    }

    public Integer executeScalar(String cypherSql) {
        Integer count = 0;
        try {
            StatementResult result = executeCypherSql(cypherSql);
            if (result.hasNext()) {
                Record record = result.next();
                for (Value value : record.values()) {
                    String t = value.type().name();
                    if (t.equals("INTEGER")) {
                        count = Integer.valueOf(value.toString());
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public HashMap<String, Object> GetRelevantEntity(String cypherSql) {
        HashMap<String, Object> rss = new HashMap<>();
        try {
            StatementResult resultNode = executeCypherSql(cypherSql);
            if (resultNode.hasNext()) {
                List<Record> records = resultNode.list();
                for (Record recordItem : records) {
                    Map<String, Object> r = recordItem.asMap();
                    System.out.println(JSON.toJSONString(r));
                    String key = r.get("key").toString();
                    if (rss.containsKey(key)) {
                        String oldValue = rss.get(key).toString();
                        String newValue = oldValue + "," + r.get("value");
                        rss.replace(key, newValue);
                    } else {
                        rss.put(key, r.get("value"));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rss;
    }


    public String getFilterPropertiesJson(String jsonStr) {
        String propertiesString = jsonStr.replaceAll("\"(\\w+)\"(\\s*:\\s*)", "$1$2"); // 去掉key的引号
        return propertiesString;
    }

    public <T> String getkeyvalCyphersql(T obj) {
        Map<String, Object> map = new HashMap<>();
        List<String> sqlList;
        sqlList = new ArrayList<String>();
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            Class type = f.getType();

            f.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(obj);
                if (val == null) {
                    val = "";
                }
                String sql = "";
                String key = f.getName();
                System.out.println("key:" + key + "type:" + type);
                if (val instanceof Integer) {
                    // 得到此属性的值
                    map.put(key, val);// 设置键值
                    sql = "n." + key + "=" + val;
                } else if (val instanceof String[]) {
                    //如果为true则强转成String数组
                    String[] arr = (String[]) val;
                    String v = "";
                    for (int j = 0; j < arr.length; j++) {
                        arr[j] = "'" + arr[j] + "'";
                    }
                    v = String.join(",", arr);
                    sql = "n." + key + "=[" + val + "]";
                } else if (val instanceof List) {
                    //如果为true则强转成String数组
                    List<String> arr = (ArrayList<String>) val;
                    List<String> aa = new ArrayList<>();
                    String v = "";
                    for (String s : arr) {
                        s = "'" + s + "'";
                        aa.add(s);
                    }
                    v = String.join(",", aa);
                    sql = "n." + key + "=[" + v + "]";
                } else {
                    // 得到此属性的值
                    map.put(key, val);// 设置键值
                    sql = "n." + key + "='" + val + "'";
                }

                sqlList.add(sql);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String finasql = String.join(",", sqlList);
        System.out.println("单个对象的所有键值==反射==" + map.toString());
        return finasql;
    }

    public <T> List<T> HashMapToObject(List<HashMap<String, Object>> maps, Class<T> type) {
        try {
            List<T> list = new ArrayList<T>();
            for (HashMap<String, Object> r : maps) {
                T t = type.newInstance();
                Iterator iter = r.entrySet().iterator();// 该方法获取列名.获取一系列字段名称.例如name,age...
                while (iter.hasNext()) {
                    Entry entry = (Entry) iter.next();// 把hashmap转成Iterator再迭代到entry
                    String key = entry.getKey().toString(); // 从iterator遍历获取key
                    Object value = entry.getValue(); // 从hashmap遍历获取value
                    if ("serialVersionUID".toLowerCase().equals(key.toLowerCase())) continue;
                    Field field = type.getDeclaredField(key);// 获取field对象
                    if (field != null) {
                        field.setAccessible(true);
                        if (field.getType() == int.class || field.getType() == Integer.class) {
                            if (value == null || StringUtil.isBlank(value.toString())) {
                                field.set(t, 0);// 设置值
                            } else {
                                field.set(t, Integer.parseInt(value.toString()));// 设置值
                            }
                        } else if (field.getType() == long.class || field.getType() == Long.class) {
                            if (value == null || StringUtil.isBlank(value.toString())) {
                                field.set(t, 0);// 设置值
                            } else {
                                field.set(t, Long.parseLong(value.toString()));// 设置值
                            }

                        } else {
                            field.set(t, value);// 设置值
                        }
                    }

                }
                list.add(t);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T HashMapToObjectItem(HashMap<String, Object> map, Class<T> type) {
        try {
            T t = type.newInstance();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Entry entry = (Entry) iter.next();// 把hashmap转成Iterator再迭代到entry
                String key = entry.getKey().toString(); // 从iterator遍历获取key
                Object value = entry.getValue(); // 从hashmap遍历获取value
                if ("serialVersionUID".toLowerCase().equals(key.toLowerCase())) continue;
                Field field = type.getDeclaredField(key);// 获取field对象
                if (field != null) {
                    field.setAccessible(true);
                    if (field.getType() == int.class || field.getType() == Integer.class) {
                        if (value == null || StringUtil.isBlank(value.toString())) {
                            field.set(t, 0);// 设置值
                        } else {
                            field.set(t, Integer.parseInt(value.toString()));// 设置值
                        }
                    } else if (field.getType() == long.class || field.getType() == Long.class) {
                        if (value == null || StringUtil.isBlank(value.toString())) {
                            field.set(t, 0);// 设置值
                        } else {
                            field.set(t, Long.parseLong(value.toString()));// 设置值
                        }

                    } else {
                        field.set(t, value);// 设置值
                    }
                }

            }

            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
