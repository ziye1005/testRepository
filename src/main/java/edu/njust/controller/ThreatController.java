package edu.njust.controller;

import edu.njust.algorithm.ThreatAnalysis;
import edu.njust.model.oracle.Node;
import edu.njust.service.NodeService;
import edu.njust.service.RelationshipService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/threat")
@CrossOrigin
public class ThreatController {
    @Autowired
    private ThreatAnalysis threatAnalysis;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private RelationshipService relationshipService;

    @PostMapping("/threat_bn")
    public Map<String, float[]> threatBN(@RequestParam("type") int type, @RequestBody Map<String, Object> rcvData){
        threatAnalysis.setType(type);

        Map<String, Object> data = (Map<String, Object>)rcvData.get("data");
        List<Float> intention = new ArrayList<>();

        List<Double> tmp = (List<Double>)rcvData.get("intention");
        for (Double d : tmp){
            intention.add(d.floatValue());
        }

        return threatAnalysis.analyze(data, intention);
    }

    @PostMapping("/add_node")
    public boolean addNode(@RequestParam("type") int type, @RequestBody Map<String, Object> rcvData){
        threatAnalysis.setType(type);

        String name = (String)rcvData.get("name");
        List<String> states = (List<String>)rcvData.get("states");
        List<Integer> parents = (List<Integer>)rcvData.get("parents");
        List<Integer> children = (List<Integer>)rcvData.get("children");

        return threatAnalysis.addNode(name, states, parents, children);
    }

    @PostMapping("/delete_node")
    public void deleteNode(@RequestParam("type") int type, @RequestParam("name") String name){
        int id = nodeService.findIdByNameAndType(name, type);
        nodeService.deleteNodeNyId(id);
        relationshipService.deleteRelationshipById(id);

    }

    @PostMapping("/get_node_by_id")
    public Node getNodeById(@RequestParam("id") int id){
        return nodeService.findNodeNyId(id);
    }

    @PostMapping("/get_relationship_by_id")
    public List getRelationshipById(@RequestParam("id") int id){
        return relationshipService.findRelationshipsById(id);
    }

    @PostMapping("/get_nodes_by_type")
    public List getNodesByType(@RequestParam("type") int type){

        return nodeService.findAllNodeByType(type);
    }

    @PostMapping("/bn_train")
    public void bnTrain(@RequestParam("type") int type, @RequestBody List<Map<String, Integer>> data){
        threatAnalysis.setType(type);

        threatAnalysis.train(data);
    }

    @PostMapping("/get_bn_model")
    public List getBNModel(@RequestParam("type") int type){
        threatAnalysis.setType(type);

        return threatAnalysis.getNodeAndLink();
    }

    @RequestMapping("/get_report")
    public String getReport(@RequestParam("filename") String fileName, HttpServletResponse response){
        OutputStream os = null;
        InputStream is = null;
        try {
            os = response.getOutputStream();
            response.reset();
            response.setContentType("application/x-download;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"),"ISO8859-1"));
            File f = new File("./src/main/resources/bn/report.docx");

            is = new FileInputStream(f);
            if (is == null){
                return "download failed";
            }

            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();

        }catch (Exception e){
            e.printStackTrace();
            return "download failed";
        }
        finally {
            try {
                if (os != null){
                    os.close();
                }
                if (is != null){
                    is.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return "download succeeded";
    }
}
