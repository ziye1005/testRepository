package edu.njust.util;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import edu.njust.vo.GraphVO;
import edu.njust.vo.NodeVO;
import edu.njust.vo.RelationVO;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.neo4j.driver.v1.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 图谱查询工具类
 *
 * @author gudongxian
 */
@Component
public class GraphQueryUtils {

    private final Logger logger = LoggerFactory.getLogger(GraphQueryUtils.class);

    /**
     * 常用查询语句
     * <p>
     * 查询图谱模式层
     */
    public static final String CALL_SCHEMA = "CALL db.schema()";
    /**
     * 查询图谱Node类型
     */
    public static final String CALL_LABEL = "CALL db.labels()";
    /**
     * 查询某个图谱Node类型
     */
    public static final String CALL_DOMAIN_LABEL = "MATCH (n:`%s`) RETURN labels(n)";
    /**
     * 查询某个图谱Node的property类型
     */
    public static final String CALL_DOMAIN_LABEL_PROPERTY = "MATCH (n:`%s`:`%s`) RETURN distinct keys(n)";
    /**
     * 查询图谱中的关系类型
     */
    public static final String CALL_REL = "CALL db.relationshipTypes()";
    public static final String LABEL_KEY = "CALL db.schema.nodeTypeProperties() yield nodeLabels,propertyName\n" +
            "WITH nodeLabels,propertyName WHERE \"%s\" in nodeLabels\n" +
            "RETURN propertyName";
    public static final String NODE_PROPERTY = "MATCH (n:`%s`) WHERE n.%s RETURN n;";
    public static final String NODE_PROPERTY_DOMAIN = "MATCH (n:`%s`:`%s`) WHERE n.`%s` = '%s' RETURN n;";
    public static final String NODE_PROPERTY_DOMAIN_NOVALUE = "MATCH (n:`%s`:`%s`) WHERE EXISTS(n.`%s`) RETURN n;";
    public static final String NODE_LABLE = "MATCH (n:`%s`) RETURN n;";
    public static final String REL_NEIGHBOUR = "MATCH p=(h)-[]-() WHERE id(h)=%d RETURN p";
    public static final String REL_TYPE_NEIGHBOUR = "MATCH p=(h)-[:%s]-() WHERE id(h)=%d RETURN p";
    public static final String SEARCH_BY_REL = "match(n:`%s`) -[m:`%s`]->(p) return n,p,m;";
    public static final String NODE_BY_ID = "MATCH (h) WHERE id(h)=%d RETURN h";
    public static final String LINKNODE_ID = "MATCH (n:`%s`) -[r]-(m) where id(n)=%s  return n,m limit 100";
    public static final String REL_TYPE_NODE = "MATCH (h)-[r]-() WHERE id(h)=%d RETURN distinct type(r)";
    public static final String REL_TYPE_AND_NODE_TYPE_NODE = "MATCH (h)-[r]-(t) WHERE id(h)=%d RETURN type(r), labels(t)";
    /**
     * 对某类Label的某个属性设置唯一约束
     * Cypher 需要三个模板参数：约束名，Label类型，Property
     */
    public static final String LABEL_CONSTRAINT_FIELD_UNIQUE = "CREATE CONSTRAINT %s ON (p:%s) ASSERT p.%s IS UNIQUE";
    /**
     * 根据时间区间查询当前节点连接的事件节点
     */
    public static final String REL_EVENT_TIME = "MATCH p=(h)-[:%s]-(event:事件) WHERE id(h)=%d AND event.time >= '%s' AND event.time <= '%s' RETURN p";

    /**
     * 事件节点Label
     */
    public static final String EVENT_LABEL = "事件";

    /**
     * 事理图谱relation
     */
    public static final String SL_RELATION = "MATCH (n:事理图谱) -[r]->(m:事理图谱)  return *";
    /**
     * 知识图谱relation
     */
    public static final String ZS_RELATION = "MATCH (n:知识图谱) -[r]->(m:知识图谱)  return *";
    /**
     * 事件节点Label
     */
    public static final String CREATE_NODE= "  return *";

    /**
     * 事理图谱地点
     */
//    public static final String DISTANCE_BY_NAEM= "MATCH (n:事理图谱:地点) RETURN n.name,n.latitude,n.longitude";
    public static final String DISTANCE_BY_NAEM= "MATCH (n:事理图谱:地点) RETURN n";

    public static final String FIND_EVENT= "MATCH w=(n:事理图谱:事件)-[b:`发生`]->(c:事理图谱:地点) WHERE c.name='%s' RETURN n";


    public static final String FIND_NEXT_EVENT= "MATCH p=(n:事理图谱:事件)-[r:`顺承`]->(m:事理图谱:事件),w=(n:事理图谱:事件)-[b:`发生`]->(c:事理图谱:地点) WHERE id(n)=%d and " +
            "c.name='%s' RETURN m";

    public static final String FIND_P= "MATCH p=(n:事理图谱:事件)-[r:`顺承`]->(m:事理图谱:事件) WHERE id(n)=%d and id(m)=%d RETURN r.p";

    public static final String FIND_PLACE="MATCH p=(n:事理图谱:事件)-[b:`发生`]->(c:事理图谱:地点) WHERE id(n)=%d RETURN c";


    private Driver driver;

    public GraphQueryUtils(Driver driver) {
        this.driver = driver;
    }

    public List<NodeVO> findEvent(String place_name){
        String cypher = String.format(FIND_EVENT, place_name);
        return findGraphNode(cypher);
    }

    /**
     * 查询意图节点
     *
     * @param event 事件id
     * @param place_name 地点名字
     * @return 节点VO
     */
    public List<NodeVO> findIntention(Integer event,String place_name){
        String cypher = String.format(FIND_NEXT_EVENT, event,place_name);
        return findGraphNode(cypher);
    }

    /**
     * 查询迁移概率
     *
     * @param event 事件id
     * @param next_event 事件id
     * @return 迁移概率
     */
    public double findP(Integer event,Integer next_event){
        String cypher = String.format(FIND_P, event,next_event);
        StatementResult result = runCypher(cypher);
        double p=0;
        if (result.hasNext()) {
            Record record = result.next();
            for (Value value : record.values()) {
                p = Double.parseDouble(value.asString());
            }
        }
//        System.out.println(result.next()+"!!!");
//        double p=Double.parseDouble(result.next().get("r.key").toString());
        return p;
    }
    public List<NodeVO> findEventPlace(Integer id){
        String cypher = String.format(FIND_PLACE, id);
        return findGraphNode(cypher);
    }
    /**
     * 查询节点
     *
     * @param id 节点id
     * @return 节点VO
     */
    public NodeVO findSingleNodeById(Integer id) {
        String cypher = String.format(NODE_BY_ID, id);
        return findFirstNode(cypher);
    }
    /**
     * 为某个标签创建唯一性约束
     *
     * @param label    Label
     * @param property 属性
     * @return 结果
     */
    public boolean createLabelPropertyUnique(String label, String property) {
        try (Session session = this.driver.session()) {
            return session.writeTransaction(tx -> {
                String cypher = String.format(LABEL_CONSTRAINT_FIELD_UNIQUE, "CONSTRAINT" + "_" + label.toUpperCase() + "_" + property.toUpperCase(), label, property);
                logger.info("创建 {}.{} 约束：{}", label, property, cypher);
                tx.run(cypher);
                return true;
            });
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 查询节点
     *
     * @param label    Label
     * @param property 属性
     * @return 节点VO
     */
    public NodeVO findSinglePlace(String label, String property, String value) {
        String cypher = String.format(NODE_PROPERTY, label,property,value);
        return findFirstNode(cypher);
    }

    /**
     * 查询节点
     *
     * @param label    Label
     * @param property 属性
     * @return 节点VO
     */
    public List<NodeVO> findNodeByName(String label, String property, String value) {
        String cypher = String.format(NODE_PROPERTY, label,property,value);
        return findGraphNode(cypher);
    }
    /**
     * 查询图谱中的所有Label
     *
     * @return label
     */
    public Set<String> findAllLabel() {
        Set<String> labels = new HashSet<>();
        StatementResult result = runCypher(CALL_LABEL);
        while (result.hasNext()) {
            Record record = result.next();
            labels.addAll(record.values().stream().map(Value::asString).collect(Collectors.toSet()));
        }
        return labels;
    }
    /**
     * 查询事理图谱关键地点的经纬度
     *
     * @return distance
     */
    public List<NodeVO> findPlace() {
        List<NodeVO> nodes = findGraphNode(DISTANCE_BY_NAEM);
//        System.out.println(nodes);
        return nodes;
    }

    /**
     * 查询domain图谱中的所有Label
     *
     * @return label
     */
    public Set<String> findDomainLabel(String domain) {
        Set<String> labels = new HashSet<>();
        StatementResult result = runCypher(String.format(CALL_DOMAIN_LABEL, domain));
        while (result.hasNext()) {
            Record record = result.next();
            //if(!record.values().get(0).get(0).toString().replace("\"", "").equals(domain))
            labels.add(record.values().get(0).get(0).toString().replace("\"", ""));
            //if(!record.values().get(0).get(1).toString().replace("\"", "").equals(domain))
            labels.add(record.values().get(0).get(1).toString().replace("\"", ""));
        }
        System.out.println(labels);
        return labels;
    }
    /**
     * 查询domain图谱中的所有Label
     *
     * @return label
     */
    public Set<String> findDomainLabelProperty(String domain, String label) {
        Set<String> labels = new HashSet<>();
        StatementResult result = runCypher(String.format(CALL_DOMAIN_LABEL_PROPERTY, domain, label));
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record.values());
            for(int i = 0; i <record.values().size(); i++){
                for(int j = 0; j < record.values().get(i).size(); j++){
                    labels.add(record.values().get(i).get(j).toString().replace("\"", ""));
                }
            }
                    /*
            if(!record.values().get(0).get(0).toString().replace("\"", "").equals(domain))
                labels.add(record.values().get(0).get(0).toString().replace("\"", ""));
            if(!record.values().get(0).get(1).toString().replace("\"", "").equals(domain))
                labels.add(record.values().get(0).get(1).toString().replace("\"", ""));

                     */
        }
        System.out.println(labels);
        return labels;
    }
    /**
     * 查询图谱中的所有关系
     *
     * @return 关系
     */
    public Set<String> findAllRel() {
        Set<String> labels = new HashSet<>();
        StatementResult result = runCypher(CALL_REL);
        while (result.hasNext()) {
            Record record = result.next();
            labels.addAll(record.values().stream().map(Value::asString).collect(Collectors.toSet()));
        }
        return labels;
    }

    /**
     * 发现节点相连的关系类型
     *
     * @param id 节点id
     * @return 返回关系集合
     */
    public Set<String> findNodeConnectedRel(Long id) {
        Set<String> rels = new HashSet<>();
        StatementResult result = runCypher(String.format(REL_TYPE_NODE, id));
        while (result.hasNext()) {
            Record record = result.next();
            rels.addAll(record.values().stream().map(Value::asString).collect(Collectors.toSet()));
        }
        return rels;
    }

    /**
     * 根据节点Id查询连接的关系名和另一个节点的类型
     *
     * @param id 节点id
     * @return map
     */
    public Map<String, Set<String>> findNodeConnectedRelAndNodeType(Long id) {
        Map<String, Set<String>> ret = new HashMap<>();
        StatementResult result = runCypher(String.format(REL_TYPE_AND_NODE_TYPE_NODE, id));
        while (result.hasNext()) {
            Record record = result.next();
            String rel = record.get(0).asString();
            List<String> nodeTypes = record.get(1).asList(Value::asString);
            Set<String> lastSet = ret.getOrDefault(rel, new HashSet<>());
            lastSet.addAll(nodeTypes);
            ret.put(rel, lastSet);
        }
        return ret;
    }


    /**
     * 查询当前节点的邻居信息
     *
     * @param id  节点id
     * @param rel 关系类型
     * @return 关系
     */
    public GraphVO findNeighbour(Long id, String rel) {
        return findAllGraph(String.format(REL_TYPE_NEIGHBOUR, rel, id));
    }
    public GraphVO findNeighbour(Long id) {
        return findAllGraph(String.format(REL_NEIGHBOUR, id));
    }
    /**
     * 根据关系名查询
     *
     * @param domain  领域
     * @param relName 关系名
     * @return 图
     */
    public GraphVO searchByRel(String domain, String relName) {
        return findAllGraph(String.format(SEARCH_BY_REL, domain, relName));
    }
    /**
     * 根据时间区间查询当前节点连接的事件节点
     *
     * @param id        节点
     * @param rel       关系
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 图数据
     */
    public GraphVO findEventByTimeAndNode(Long id, String rel, String startTime, String endTime) {
        GraphVO graph = findAllGraph(String.format(REL_EVENT_TIME, rel, id, startTime, endTime));
        return graph;
    }

    /**
     * 查询label的属性
     *
     * @param label label
     * @return label
     */
    public Set<String> findProperties(String label) {
        Set<String> res = new HashSet<>();
        StatementResult result = runCypher(String.format(LABEL_KEY, label));
        while (result.hasNext()) {
            Record record = result.next();
            for (Value value : record.values()) {
                res.add(value.asString());
//                res.addAll(value.asList(Value::asString));
            }
        }
        return res;
    }


    /**
     * Neo4j能否连接成功
     *
     * @return 连接标识
     */
    public boolean isNeo4jOpen() {
        try (Session session = driver.session()) {
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
     * @param cypher cypher语句
     * @return 执行结果
     */
    public StatementResult runCypher(String cypher) {
        StatementResult result = null;
        try (Session session = driver.session()) {
            logger.info("执行Cypher语句:{}", cypher);
            result = session.run(cypher);
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * 执行Cypher语句
     *
     * @param cypher 语句
     * @param map    参数
     */
    public void createNodeByMap(String cypher, Map<String, Object> map) {
        try (Session session = driver.session()) {
            logger.info("执行Cypher语句:{}", cypher);
            for (Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof BigDecimal) {
                    map.put(entry.getKey(), ((BigDecimal) entry.getValue()).floatValue());
                }
            }
            session.run(cypher, map);
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
        for (Entry<String, Object> item : map.entrySet()) {
            if (null == item.getValue()) {
                item.setValue("无");
            }
        }
        String cypher = buildCypherByMap(map, label);
        try (Session session = driver.session()) {
            logger.info("执行Cypher语句:{}", cypher);
            for (Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof BigDecimal) {
                    map.put(entry.getKey(), ((BigDecimal) entry.getValue()).floatValue());
                }
            }
            StatementResult cypherResult = session.run(cypher, map);
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
        try (Session session = driver.session()) {
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
     * 执行cypher，解析单个实体数据
     *
     * @param cypher 查询语句
     * @return 实体的Map格式
     */
    public NodeVO findFirstNode(String cypher) {
        try {
            StatementResult result = runCypher(cypher);
            if (result.hasNext()) {
                List<Record> records = result.list();
                for (Record recordItem : records) {
                    for (Value value : recordItem.values()) {
                        if ("NODE".equals(value.type().name())) {
                            Node node = value.asNode();
                            return extractNode(node);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private NodeVO extractNode(Node node) {
        NodeVO nodeVO = new NodeVO();
        Map<String, Object> map = node.asMap();
        nodeVO.setId(node.id());
        nodeVO.setName((String) map.getOrDefault("name", ""));
        Iterator<String> it = node.labels().iterator();
        String type = new String();
        type = it.next();
        if(type.equals("知识图谱") || type.equals("事理图谱")){
            type = it.next();
        }
        nodeVO.setType(type);
        Map<String, Object> properties = new HashMap<>(map);
        // 检测名称为纯英文的剔出
        // 2021.1.8 英文属性不剔除
        /*
        Set<String> enKeys = new HashSet<>();
        for (String key : properties.keySet()) {
            if (StringUtil.isEnglish(key)) {
                enKeys.add(key);
            }
        }
        for (String enKey : enKeys) {
            properties.remove(enKey);
        }
        */
        nodeVO.setProperties(properties);
        return nodeVO;
    }

    /**
     * 解析多个知识图谱中节点
     *
     * @param cypher cypher
     * @return 节点列表
     */
    public List<NodeVO> findGraphNode(String cypher) {
        List<NodeVO> nodeVOS = new LinkedList<>();
        try {
            StatementResult result = runCypher(cypher);
            System.out.println("cypher：" + cypher);
            if (result.hasNext()) {
                List<Record> records = result.list();
                // 遍历每个记录
                for (Record recordItem : records) {
                    // 获取每条记录的键值对列表
                    List<Pair<String, Value>> f = recordItem.fields();
                    for (Pair<String, Value> pair : f) {
                        String typeName = pair.value().type().name();
                        // 当前数据尾节点数据
                        if ("NODE".equals(typeName)) {
                            Node node = pair.value().asNode();
                            nodeVOS.add(extractNode(node));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodeVOS;
    }

    /**
     * 解析查询结果中的关系数据
     *
     * @param cypher cypher
     * @return 关系列表
     */
    public List<RelationVO> findGraphRel(String cypher) {
        List<RelationVO> relationVOS = new LinkedList<>();
        try {
            StatementResult result = runCypher(cypher);
            if (result.hasNext()) {
                List<Record> records = result.list();
                for (Record recordItem : records) {
                    List<Pair<String, Value>> f = recordItem.fields();
                    for (Pair<String, Value> pair : f) {
                        String typeName = pair.value().type().name();
                        if ("RELATIONSHIP".equals(typeName)) {
                            Relationship rel = pair.value().asRelationship();
                            relationVOS.add(extractRel(rel));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return relationVOS;
    }

    private RelationVO extractRel(Relationship rel) {
        RelationVO relationVO = new RelationVO();
        relationVO.setId(rel.id());
        relationVO.setSourceId(rel.startNodeId());
        relationVO.setTargetId(rel.endNodeId());
        relationVO.setProperties(rel.asMap());
        relationVO.setName(rel.type());
        return relationVO;
    }

    /**
     * 解析查询结果中的节点和关系数据
     *
     * @param cypher 查询语句
     * @return 数据列表
     */
    public GraphVO findNodeAndRelGraph(String cypher) {

        GraphVO graphVO = new GraphVO();
        List<NodeVO> nodeVOS = new LinkedList<>();
        List<RelationVO> relationVOS = new LinkedList<>();

        List<Long> nodeIDS = new LinkedList<>();
        List<Long> relIDS = new LinkedList<>();
        try {
            StatementResult result = runCypher(cypher);
            if (result.hasNext()) {
                List<Record> records = result.list();
                for (Record recordItem : records) {
                    List<Pair<String, Value>> f = recordItem.fields();
                    HashMap<String, Object> rss = new HashMap<>();
                    for (Pair<String, Value> pair : f) {
                        String typeName = pair.value().type().name();
                        // 实体节点
                        if ("NODE".equals(typeName)) {
                            Node node = pair.value().asNode();
                            long id = node.id();
                            if (nodeIDS.contains(id)) {
                                continue;
                            }
                            nodeIDS.add(id);
                            nodeVOS.add(extractNode(node));

                        } else if ("RELATIONSHIP".equals(typeName)) {
                            // 关系节点
                            Relationship rel = pair.value().asRelationship();
                            long id = rel.id();
                            if (relIDS.contains(id)) {
                                continue;
                            }
                            relIDS.add(id);
                            relationVOS.add(extractRel(rel));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        graphVO.setNodes(nodeVOS);
        graphVO.setLinks(relationVOS);
        return graphVO;
    }


    /**
     * 获取值类型的结果,如count,uuid
     *
     * @param cypher cypher
     * @return 1 2 3 等数字类型
     */
    public long findSingleValue(String cypher) {
        long val = 0;
        try {
            StatementResult cypherResult = runCypher(cypher);
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
     * @param cypher cypher
     * @return 节点和关系数据
     */
    public GraphVO findAllGraph(String cypher) {
        GraphVO graphVO = new GraphVO();
        List<NodeVO> nodeVOS = new LinkedList<>();
        List<RelationVO> relationVOS = new LinkedList<>();

        List<Long> nodeIDS = new LinkedList<>();
        List<Long> relIDS = new LinkedList<>();
        try {
            StatementResult result = runCypher(cypher);
            if (result.hasNext()) {
                List<Record> records = result.list();
                for (Record recordItem : records) {
                    List<Pair<String, Value>> f = recordItem.fields();
                    for (Pair<String, Value> pair : f) {
                        String typeName = pair.value().type().name();
                        if ("NULL".equals(typeName)) {
                        } else if ("NODE".equals(typeName)) {
                            Node node = pair.value().asNode();
                            long id = node.id();
                            if (nodeIDS.contains(id)) {
                                continue;
                            }
                            nodeIDS.add(id);
                            nodeVOS.add(extractNode(node));
                        } else if ("RELATIONSHIP".equals(typeName)) {
                            // 关系节点
                            Relationship rel = pair.value().asRelationship();
                            long id = rel.id();
                            if (relIDS.contains(id)) {
                                continue;
                            }
                            relIDS.add(id);
                            relationVOS.add(extractRel(rel));
                        } else if ("PATH".equals(typeName)) {
                            // 解析路径
                            Path path = pair.value().asPath();
                            Node node = path.start();
                            Long id = path.start().id();
                            // 解析起点
                            if (!nodeIDS.contains(id)) {
                                nodeIDS.add(id);
                                nodeVOS.add(extractNode(node));
                            }

                            // 解析终点
                            Node endNode = path.end();
                            Long endNodeUuid = path.end().id();
                            if (!nodeIDS.contains(endNodeUuid)) {
                                nodeIDS.add(endNodeUuid);
                                nodeVOS.add(extractNode(endNode));
                            }
                            // 获取路径上的节点
                            for (Node next : path.nodes()) {
                                Long curId = next.id();
                                if (!nodeIDS.contains(curId)) {
                                    nodeIDS.add(curId);
                                    nodeVOS.add(extractNode(next));
                                }
                            }
                            // 遍历关系
                            for (Relationship next : path.relationships()) {
                                Long relID = next.id();
                                if (!relIDS.contains(relID)) {
                                    relIDS.add(relID);
                                    relationVOS.add(extractRel(next));
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
                                    Node node = next.asNode();
                                    long id = node.id();
                                    if (nodeIDS.contains(id)) {
                                        continue;
                                    }
                                    nodeIDS.add(id);
                                    nodeVOS.add(extractNode(node));
                                } else if ("RELATIONSHIP".equals(type)) {
                                    Relationship rel = next.asRelationship();
                                    Long relID = rel.id();
                                    if (!relIDS.contains(relID)) {
                                        relIDS.add(relID);
                                        relationVOS.add(extractRel(rel));
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        graphVO.setNodes(nodeVOS);
        graphVO.setLinks(relationVOS);
        return graphVO;
    }

    /**
     * 删除节点
     */
    public static final String DELETE_NODE = "MATCH (h) WHERE id(h)=%d DETACH DELETE h";
    /**
     * 删除关系
     */
    public static final String DELETE_REL = "MATCH ()-[r]-() WHERE id(r)=%d DELETE r";

    /**
     * 删除节点
     *
     * @param nodeId 节点
     * @return 结果
     */
    public Boolean deleteNode(Integer nodeId) {
        try (Session session = driver.session()) {
            session.run(String.format(DELETE_NODE, nodeId));
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    /**
     * 删除节点
     *
     * @param relId 关系id
     * @return 结果
     */
    public Boolean deleteRel(Integer relId) {
        try (Session session = driver.session()) {
            session.run(String.format(DELETE_REL, relId));
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
}
