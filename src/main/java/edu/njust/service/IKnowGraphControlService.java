package edu.njust.service;

import edu.njust.entity.DataMap;
import edu.njust.entity.QAEntityItem;
import edu.njust.vo.GraphVO;
import edu.njust.vo.NodeVO;
import edu.njust.vo.RelationVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 知识图谱控制服务类
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/21
 */
public interface IKnowGraphControlService {
    /**
     * 查询图谱得模式层
     *
     * @return 数据
     */
    GraphVO querySchema();

    /**
     * 根据节点id查询节点
     *
     * @param id 节点id
     * @return 节点信息
     */
    NodeVO queryNode(Integer id);

    /**
     * 查询Node
     *
     * @param label    节点类型
     * @param property 属性
     * @param value    值
     * @return node
     */
    List<NodeVO> findNodeByName(String label, String property, String value);
    /**
     * 根据名称查询节点
     *
     * @param name 名称，后续用于显示节点信息
     * @return 节点列表
     */
    List<NodeVO> queryNode(String name);

    /**
     * 根据节点ID 查询周围节点
     *
     * @param nodeId 节点ID
     * @return 图谱显示对象
     */
    GraphVO queryNodeNeighbour(String nodeId,String domain);

    /**
     * 查询所有的Label
     *
     * @return label
     */
    Set<String> queryAllLabel();

    /**
     * 查询所有label 映射信息
     *
     * @return
     */
    List<DataMap<String, String>> queryAllLabelMap();


    /**
     * 查询Label 属性
     *
     * @param label 节点类型
     * @return label
     */
    Set<String> queryProperties(String label);

    /**
     * 查询label 属性 映射信息
     *
     * @return properties
     */
    List<DataMap<String, String>> queryPropertiesMap(String label);


    /**
     * 查询所有的关系
     *
     * @return label
     */
    Set<String> queryAllRel();


    /**
     * 查询节点关系的相邻节点
     *
     * @return Graph
     */
    GraphVO queryNeighbour(Long id, String rel);

    /**
     * 查询节点关系的相邻的事件节点
     *
     * @return Graph
     */
    GraphVO queryEventNeighbour(Long id, String rel, String startTime, String endTime);
    /**
     * 根据关系名查询图谱
     * @param domain 图谱领域
     * @param relName 关系名称
     * @return Graph
     */
    public GraphVO searchByRel(String domain, String relName);
    /**
     * 查询Node
     *
     * @param label    节点类型
     * @param property 属性
     * @param value    值
     * @return node
     */
    List<NodeVO> queryNodeByProperty(String label, String property, String value);

    /**
     * 查询Node
     *
     * @param label    节点类型
     * @return node
     */
    public List<NodeVO> queryNodeByLabel(String label);
    /**
     * 查询全部事理关系
     *
     * @return relation
     */
    public List<RelationVO> queryDomainRelation(String domain);
    /**
     * 根据节点编号查询当前节点的关系
     *
     * @param nodeId 节点id
     * @return 关系集
     */
    Set<String> queryRelByNode(Long nodeId);

    /**
     * 查询关系和关系连接的节点类型
     *
     * @param nodeId 节点id
     * @return 关系哈希表  key:关系名 value: nodeTypes
     */
    Map<String, Set<String>> queryRelAndNodeType(Long nodeId);

    /**
     * 删除节点
     *
     * @param nodeId 节点id
     * @return 删除结果
     */
    Boolean deleteNode(Integer nodeId);

    /**
     * 删除关系
     *
     * @param relId 关系id
     * @return 删除结果
     */
    Boolean deleteRel(Integer relId);

    /**
     * 添加关系
     *
     * @param domain 关系领域
     * @param source 关系源节点ID
     * @param target 关系目标节点ID
     * @param name 关系名称
     * @return 删除结果
     */
    RelationVO creteRel(String domain,String source, String target, String name);
    /**
     * 创建单个节点
     *
     * @param domain 节点领域
     * @param type 节点类型
     * @param property 节点属性
     * @return 创建节点ID
     */
    Integer createNode(String domain, String type,Map<String,Object> property);
    /**
     * 获取图谱标签
     *
     * @param domain 节点领域
     * @return 图谱所有标签类型
     */
    Set<String> getLabel(String domain);
    /**
     * 获取标签属性名
     * @param domain 节点领域
     * @param label 节点标签
     * @return 图谱该标签所有属性名
     */
    Set<String> getLabelProperty(String domain, String label);
    /**
     * 根据标签属性名搜索
     * @param domain 节点领域
     * @param label 节点标签
     * @param property 节点属性
     * @param propertyInput 节点属性值
     * @return 查询到的节点
     */
    List<NodeVO> searchByProperty(String domain, String label, String property,String propertyInput);
}
