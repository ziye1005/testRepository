package edu.njust.service.impl;

import com.alibaba.fastjson.JSON;
import edu.njust.dal.IFieldMeanRepository;
import edu.njust.dal.IKGraphRepository;
import edu.njust.dal.Table2NodelLabelRepository;
import edu.njust.entity.DataMap;
import edu.njust.entity.dto.FieldMean;
import edu.njust.entity.dto.Table2NodeLabel;
import edu.njust.service.IKnowGraphControlService;
import edu.njust.util.GraphQueryUtils;
import edu.njust.util.Neo4jUtil;
import edu.njust.util.StringUtil;
import edu.njust.vo.GraphVO;
import edu.njust.vo.NodeVO;
import edu.njust.vo.RelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 知识图谱控制服务实现类
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/21
 */
@Service
public class KnowGraphControlServiceImpl implements IKnowGraphControlService {

    private GraphQueryUtils graphQueryUtils;
    private IFieldMeanRepository fieldMeanRepository;
    private Table2NodelLabelRepository table2NodelLabelRepository;
    @Autowired
    private IKGraphRepository ikGraphRepository;
    @Autowired
    private Neo4jUtil neo4jUtil;

    public KnowGraphControlServiceImpl(GraphQueryUtils graphQueryUtils, IFieldMeanRepository fieldMeanRepository, Table2NodelLabelRepository table2NodelLabelRepository) {
        this.graphQueryUtils = graphQueryUtils;
        this.fieldMeanRepository = fieldMeanRepository;
        this.table2NodelLabelRepository = table2NodelLabelRepository;
    }

    @Override
    public GraphVO querySchema() {
        return graphQueryUtils.findAllGraph(GraphQueryUtils.CALL_SCHEMA);
    }

    @Override
    public NodeVO queryNode(Integer id) {
        return graphQueryUtils.findSingleNodeById(id);
    }

    @Override
    public List<NodeVO> queryNode(String name) {
        return null;
    }

    @Override
    public List<NodeVO> findNodeByName(String label, String property, String value) {
        return graphQueryUtils.findNodeByName(label, property,value);
    }
    @Override
    public GraphVO queryNodeNeighbour(String nodeId,String domain) {
        HashMap<String, Object> graph = ikGraphRepository.getmorerelationnode(domain,nodeId);
        //System.out.println("关系" + graph);
        GraphVO graphVO = new GraphVO();
        graphVO.setLinks((List<RelationVO>)graph.get("relationship"));
        //graphVO.setNodes((List<NodeVO>)graph.get("node"));

        //System.out.println("关系转换: " + graphVO.getLinks() + ' ' + graphVO.getNodes());

        List<NodeVO> nodes = graphQueryUtils.findGraphNode(String.format(GraphQueryUtils.LINKNODE_ID, domain, nodeId));
        System.out.println("nodes" + nodes);
        List<NodeVO> nodesAfter = new ArrayList<NodeVO>();
        for (NodeVO node1 : nodes) {
            int flag = 0;
            for(NodeVO node2 : nodesAfter){
                if(node2.getId().equals(node1.getId())){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0)
                nodesAfter.add(node1);

        }
        System.out.println("afterNodes" + nodesAfter);
        graphVO.setNodes(nodesAfter);
        return graphVO;
    }

    @Override
    public Set<String> queryAllLabel() {
        return graphQueryUtils.findAllLabel();
    }

    @Override
    public List<DataMap<String, String>> queryAllLabelMap() {
        Set<String> labels = this.queryAllLabel();
        List<DataMap<String, String>> ret = new LinkedList<>();
        for (String label : labels) {
            Table2NodeLabel tableNodeMap = table2NodelLabelRepository.getFirstByTableName(label);
            if (tableNodeMap != null) {
                ret.add(new DataMap<>(label, tableNodeMap.getNodeLabel()));
            } else {
                ret.add(new DataMap<>(label, label));
            }
        }
        return ret;
    }


    @Override
    public Set<String> queryProperties(String label) {
        Set<String> properties = graphQueryUtils.findProperties(label);
        // 检测名称为纯英文的剔出
        // 2021.1.8 显示英文属性
        /*
        Set<String> enSet = new HashSet<>();
        for (String key : properties) {
            if (StringUtil.isEnglish(key)) {
                enSet.add(key);
            }
        }
        for (String enKey : enSet) {
            properties.remove(enKey);
        }
        */
        return properties;
    }

    @Override
    public List<DataMap<String, String>> queryPropertiesMap(String label) {
        Set<String> properties = this.queryProperties(label);
        List<DataMap<String, String>> ret = new LinkedList<>();
        for (String property : properties) {
            FieldMean fieldMean = fieldMeanRepository.getFirstByTableNameAndField(label, property);
            if (fieldMean != null) {
                ret.add(new DataMap<>(property, fieldMean.getMean()));

            } else {
                ret.add(new DataMap<>(property, property));
            }
        }
        return ret;
    }

    @Override
    public Set<String> queryAllRel() {
        return graphQueryUtils.findAllRel();
    }

    @Override
    public GraphVO queryNeighbour(Long id, String rel) {
        if (StringUtil.isBlank(rel)) {
            return graphQueryUtils.findNeighbour(id);
        }
        return graphQueryUtils.findNeighbour(id, rel);
    }

    @Override
    public GraphVO queryEventNeighbour(Long id, String rel, String startTime, String endTime) {
        return graphQueryUtils.findEventByTimeAndNode(id, rel, startTime, endTime);
    }


    @Override
    public List<NodeVO> queryNodeByProperty(String label, String property, String value) {
        List<NodeVO> nodes = graphQueryUtils.findGraphNode(String.format(GraphQueryUtils.NODE_PROPERTY, label, property, value));
        for (NodeVO node : nodes) {
            Map<String, Object> properties = node.getProperties();
            node.setProperties(properties);
        }
        return nodes;
    }
    @Override
    public List<NodeVO> queryNodeByLabel(String label) {
        List<NodeVO> nodes = graphQueryUtils.findGraphNode(String.format(GraphQueryUtils.NODE_LABLE, label));
        System.out.println("nodes" + nodes);
        for (NodeVO node : nodes) {
            Map<String, Object> properties = node.getProperties();
            node.setProperties(properties);
        }
        return nodes;
    }
    @Override
    public List<RelationVO> queryDomainRelation(String domain){
        List<RelationVO> relations;
        System.out.println(domain);
        if(domain.equals("事理图谱")){
            relations = graphQueryUtils.findGraphRel(GraphQueryUtils.SL_RELATION);
            return relations;
        }
        if(domain.equals("知识图谱")){
            System.out.println("start" + domain);
            relations = graphQueryUtils.findGraphRel(GraphQueryUtils.ZS_RELATION);
            return relations;
        }
        return  null;
    }

    @Override
    public Set<String> queryRelByNode(Long nodeId) {
        return graphQueryUtils.findNodeConnectedRel(nodeId);
    }

    @Override
    public Map<String, Set<String>> queryRelAndNodeType(Long nodeId) {
        return graphQueryUtils.findNodeConnectedRelAndNodeType(nodeId);
    }

    @Override
    public Boolean deleteNode(Integer nodeId) {
        return graphQueryUtils.deleteNode(nodeId);
    }

    @Override
    public Boolean deleteRel(Integer relId) {
        return graphQueryUtils.deleteRel(relId);
    }

    @Override
    public RelationVO creteRel(String domain,String source, String target, String name){
        HashMap<String, Object> map = ikGraphRepository.createlink(domain, Integer.parseInt(source), Integer.parseInt(target),name);
        RelationVO relationVO = new RelationVO();
        relationVO.setId((long) Integer.parseInt((String) map.get("uuid")));
        relationVO.setName((String) map.get("name"));
        System.out.println((String) map.get("sourceId"));
        relationVO.setSourceId((long) Integer.parseInt((String) map.get("sourceId")));
        relationVO.setTargetId((long) Integer.parseInt((String) map.get("targetId")));
        return relationVO;
    }
    @Override
    public Integer createNode(String domain, String type,Map<String,Object> property){
        //graphQueryUtils.createNodeByMap(GraphQueryUtils.CREATE_NODE, properties);
        try{
            List<HashMap<String, Object>> graphNodeList = new ArrayList<HashMap<String, Object>>();
            //System.out.println('1' + JSON.toJSONString(property));
            String propertiesString = neo4jUtil.getFilterPropertiesJson(JSON.toJSONString(property));
            //System.out.println('1' + propertiesString);
            String cypherSql = String.format("create (n:`%s`:`%s` %s) return n",
                    domain,type, propertiesString);
            graphNodeList = neo4jUtil.GetGraphNode(cypherSql);
            return Integer.parseInt((String) graphNodeList.get(0).get("uuid"));
        }catch (Exception e){
            throw e;
        }
    }
    @Override
    public Set<String> getLabel(String domain){
        return graphQueryUtils.findDomainLabel(domain);
    }
    @Override
    public Set<String> getLabelProperty(String domain, String label){
        return graphQueryUtils.findDomainLabelProperty(domain, label);
    }
    @Override
    public List<NodeVO> searchByProperty(String domain, String label, String property,String propertyInput){
        List<NodeVO> nodes;
        if(propertyInput.isEmpty())
            nodes = graphQueryUtils.findGraphNode(String.format(GraphQueryUtils.NODE_PROPERTY_DOMAIN_NOVALUE, domain, label, property));
        else
            nodes = graphQueryUtils.findGraphNode(String.format(GraphQueryUtils.NODE_PROPERTY_DOMAIN, domain, label, property, propertyInput));

        return nodes;
    }
    @Override
    public GraphVO searchByRel(String domain,String relName) {
        GraphVO graph = graphQueryUtils.searchByRel(domain,relName);
        //System.out.println("关系" + graph);
        return graph;
    }
}
