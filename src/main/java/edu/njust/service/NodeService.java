package edu.njust.service;

import edu.njust.mapper.oracle.NodeMapper;
import edu.njust.model.oracle.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeService {
    @Autowired
    private NodeMapper nodeMapper;
    public int addNode(Node node){
        String maxId = nodeMapper.getMaxId();
        int id;
        if (maxId==null){
            id = -1;
        }
        else {
            id = Integer.parseInt(maxId);
        }
        node.setId(id+1);
        return nodeMapper.addNode(node);
    }

    public int findIdByNameAndType(String name, int type){
        return nodeMapper.findIdByNameAndType(name, type);
    }

    public String findNameById(int id){
        return nodeMapper.findNameById(id);
    }

    public List<Node> findAllNodeByType(int type){
        return nodeMapper.findAllNodeByType(type);
    }

    public int updateCPTByNameAndType(String name, int type, String cpt){
        return nodeMapper.updateCPTByNameAndType(name, type, cpt);
    }

    public int deleteNodeNyId(int id){
        return nodeMapper.deleteNodeNyId(id);
    }

    public Node findNodeNyId(int id){
        return nodeMapper.findNodeNyId(id);
    }

}
