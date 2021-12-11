package edu.njust.vo;

import java.util.List;

/**
 * 图谱显示对象
 *
 * @author gudongxian
 * @version 0.1
 * @date 2020/9/24
 */
public class GraphVO {
    /**
     * 图中节点列表
     */
    private List<NodeVO> nodes;
    /**
     * 图中关系列表
     */
    private List<RelationVO> links;

    public GraphVO() {
    }

    public GraphVO(List<NodeVO> nodes, List<RelationVO> links) {
        this.nodes = nodes;
        this.links = links;
    }

    @Override
    public String toString() {
        return "GraphVO{" +
                "nodes=" + nodes +
                ", links=" + links +
                '}';
    }

    public List<NodeVO> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeVO> nodes) {
        this.nodes = nodes;
    }

    public List<RelationVO> getLinks() {
        return links;
    }

    public void setLinks(List<RelationVO> links) {
        this.links = links;
    }
}
