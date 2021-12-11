//package edu.njust;
//
//import edu.njust.algorithm.ThreatAnalysis;
//import edu.njust.model.oracle.Relationship;
//import edu.njust.service.NodeService;
//import edu.njust.service.RelationshipService;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import norsys.netica.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.*;
//
//@SpringBootTest
//class Demo1ApplicationTests {
//    @Autowired
//    NodeService nodeService;
//
//    @Autowired
//    RelationshipService relationshipService;
//
//    @Test
//    void contextLoads() throws Exception {
////        Environ environ = new Environ(null);
//        int id = 0;
//        int type = 0;
//        Net net;
//        String[] planeType = new String[]{"reco", "bomber", "fighter"};
//        String[] threatType = new String[]{"pingtai", "wuqi", "shuxing", "shikong", "weixie"};
//        for (String pt : planeType){
//            for (String tt : threatType){
//
//                String path = "./src/main/resources/bn/"+pt+"/"+pt+"_"+tt+".neta";
//                net = new Net(new Streamer(path));
//                for (Object o : net.getNodes()){
//                    Node node = (Node)o;
//                    if (node.getName().equals("GuoJiaDiQu")){
//                        int stateNum = node.getNumStates();
//
//                        StringBuilder sb = new StringBuilder();
//                        /*
//                        *******************************************
//                         */
//                        for (int s = 0; s < 2; s ++){
//                            sb.append("c").append(s);
//                            if (s < 1){
//                                sb.append(',');
//                            }
//                        }
//                        node.addStates(stateNum, sb.toString(), 2, -1);
//
//                        for (int s = 0; s < stateNum; s ++){
//                            node.state(s).delete();
//                        }
//                    }
//                    else if (node.getName().equals("QiFeiJiChang")){
//                        int stateNum = node.getNumStates();
//
//                        /*
//                         *******************************************
//                         */
//                        StringBuilder sb = new StringBuilder();
//                        for (int s = 0; s < 2; s ++){
//                            sb.append("a").append(s);
//                            if (s < 1){
//                                sb.append(',');
//                            }
//                        }
//                        node.addStates(stateNum, sb.toString(), 2, -1);
//
//                        for (int s = 0; s < stateNum; s ++){
//                            node.state(s).delete();
//                        }
//                    }
//
//                    edu.njust.model.oracle.Node oracleNode = new edu.njust.model.oracle.Node();
//                    oracleNode.setId(id++);
//                    oracleNode.setName(node.getName());
//                    oracleNode.setType(type);
//
//                    StringBuilder stateString = new StringBuilder();
//                    for (int s = 0; s < node.getNumStates(); s ++){
//                        stateString.append(node.state(s).getName());
//                        if (s < node.getNumStates() - 1){
//                            stateString.append(',');
//                        }
//                    }
//                    oracleNode.setState(stateString.toString());
//
//                    if (node.hasTable(null)){
//                        StringBuilder cptString = new StringBuilder();
//                        float[] cpt = node.getCPTable(null);
//                        for (int c = 0; c < cpt.length; c ++){
//                            cptString.append(cpt[c]);
//                            if (c < cpt.length - 1){
//                                cptString.append(',');
//                            }
//                        }
//                        oracleNode.setCpt(cptString.toString());
//                    }
//                    else {
//                        oracleNode.setCpt("null");
//                    }
//
//                    nodeService.addNode(oracleNode);
//                }
//                for (Object o : net.getNodes()){
//                    Node node = (Node)o;
//                    NodeList parents = node.getParents();
//                    for (Object p : parents){
//                        Node parent = (Node)p;
//                        Relationship relationship = new Relationship();
//                        relationship.setFrom(nodeService.findIdByNameAndType(parent.getName(), type));
//                        relationship.setTo(nodeService.findIdByNameAndType(node.getName(), type));
//
//                        relationshipService.addRelationship(relationship);
//                    }
//                }
//
//                type ++;
//            }
//
//
//        }
//
//    }
//
//    @Test
//    void oracleTest() {
//        edu.njust.model.oracle.Node node = new edu.njust.model.oracle.Node();
//        node.setId(0);
//        node.setName("test");
//        node.setType(0);
//        node.setState("test");
//        node.setCpt("test");
//
//        nodeService.addNode(node);
//
//        System.out.println(nodeService.findIdByNameAndType("test", 0));
//    }
//
//    @Test
//    void buildNetFromOracle() throws Exception{
////        Environ environ = new Environ(null);
//        List<Net> netList = new ArrayList<>();
//        int[] types = new int[]{0, 1, 2, 3, 4};
//        for (int type : types){
//            List<edu.njust.model.oracle.Node> result = nodeService.findAllNodeByType(type);
//
//            Net net = new Net();
//            // 依次添加节点
//            for (edu.njust.model.oracle.Node n : result){
//                Node node = new Node(n.getName(), n.getState(), net);
//
//            }
//            // 依次添加边
//            for (edu.njust.model.oracle.Node n : result){
//                Node node = net.getNode(n.getName());
//                List<Relationship> relationships = relationshipService.findRelationshipByTo(n.getId());
//                for (Relationship relationship : relationships){
//                    Node parent = net.getNode(nodeService.findNameById(relationship.getFrom()));
//                    node.addLink(parent);
//                }
//            }
//            // 依次添加CPT
//            for (edu.njust.model.oracle.Node n : result){
//                Node node = net.getNode(n.getName());
//                if (!n.getCpt().equals("null")){
//                    String[] cptStrings = n.getCpt().split(",");
//                    float[] cpt = new float[cptStrings.length];
//                    for (int i = 0; i < cpt.length; i ++){
//                        cpt[i] = Float.parseFloat(cptStrings[i]);
//                    }
//                    node.setCPTable(cpt);
//                }
//            }
//
//            System.out.println(net.getNode("WuLiTeXing"));
//            netList.add(net);
//        }
//
//
//    }
//
//    @Autowired
//    ThreatAnalysis threatAnalysis;
//
//    @Test
//    void createTrainData(){
//        Random random = new Random();
//        int[] types = new int[]{0, 1, 2, 3, 4};
//        List<Map<String, Integer>> data = new ArrayList<>();
//        for (int _ = 0; _ < 100; _ ++){
//            Map<String, Integer> d = new HashMap<>();
//            for (int type : types) {
////                System.out.println(type);
//                List<edu.njust.model.oracle.Node> result = nodeService.findAllNodeByType(type+10);
//                for (edu.njust.model.oracle.Node n : result){
//                    d.put(n.getName(), random.nextInt(n.getState().split(",").length));
//                }
//            }
//            data.add(d);
//        }
//
//        JSONArray jsonArray = JSONArray.fromObject(data);
//        FileWriter fw;
//        try {
//
//            fw = new FileWriter(String.format("./trainSet.json"));
//            PrintWriter out = new PrintWriter(fw);
//            out.write(jsonArray.toString());
//            out.close();
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        threatAnalysis.setType(ThreatAnalysis.FIGHTER);
//        threatAnalysis.train(data);
//
//    }
//
//    @Test
//    void createTestData(){
//        List<String> resultList = new ArrayList<>();
//        resultList.add("WuLiTeXing");
//        resultList.add("DianCiPingTai");
//        resultList.add("MuBiaoShuXing");
//        resultList.add("FeiXingHangXian");
//        resultList.add("WuQiPingTai");
//        resultList.add("WeiXieDengJi");
//        resultList.add("RenWuShuXing");
//        Random random = new Random();
//        int[] types = new int[]{0, 1, 2, 3, 4};
//        Map<String, Object> data = new HashMap<>();
//        for (int type : types) {
//            List<edu.njust.model.oracle.Node> result = nodeService.findAllNodeByType(type);
//            for (edu.njust.model.oracle.Node n : result){
//                if (resultList.contains(n.getName())) continue;
//                data.put(n.getName(), random.nextInt(n.getState().split(",").length));
//            }
//
//        }
//
//        JSONObject jsonObject = JSONObject.fromObject(data);
//        FileWriter fw;
//        try {
//
//            fw = new FileWriter(String.format("./testData.json"));
//            PrintWriter out = new PrintWriter(fw);
//            out.write(jsonObject.toString());
//            out.close();
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        List<Float> intention = new ArrayList<>();
//        intention.add(0.5f);
//        intention.add(0.2f);
//        intention.add(0.3f);
//        threatAnalysis.setType(ThreatAnalysis.RECONNAISSANCE);
//        System.out.println(threatAnalysis.analyze(data, intention));
//
//
//    }
//
//
//}
