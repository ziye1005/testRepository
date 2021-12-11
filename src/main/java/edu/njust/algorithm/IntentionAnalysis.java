package edu.njust.algorithm;
import edu.njust.vo.NodeVO;

import java.util.*;
import java.lang.String;

import static java.lang.Math.asin;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import edu.njust.util.GraphQueryUtils;

import org.springframework.stereotype.Component;

@Component
public class IntentionAnalysis {
    GraphQueryUtils graphQueryUtils;

    public IntentionAnalysis(GraphQueryUtils graphQueryUtils) {
        this.graphQueryUtils = graphQueryUtils;
    }

    public double radians(double d) {
        return d * Math.PI / 180.0;
    }

    public double getDistanceFrom2LngLat(double lng1, double lat1, double lng2, double lat2) {
        //将角度转化为弧度
        double radLng1 = radians(lng1);
        double radLat1 = radians(lat1);
        double radLng2 = radians(lng2);
        double radLat2 = radians(lat2);

        double a = radLat1 - radLat2;
        double b = radLng1 - radLng2;

        return 2 * asin(sqrt(sin(a / 2) * sin(a / 2) + cos(radLat1) * cos(radLat2) * sin(b / 2) * sin(b / 2))) * 6378.137;
    }


    public List<Map.Entry<String, Double>>  getDistances(double lng,double lat){
        Map<String,Double> distances=new HashMap<>();
        List<NodeVO> places=graphQueryUtils.findPlace();
        for(NodeVO place:places){
            double distance=getDistanceFrom2LngLat(lng,lat,Double.parseDouble(place.getProperties().get("longitude").toString()),Double.parseDouble(place.getProperties().get("latitude").toString()));
            distances.put(place.getName(),distance);
        }
        List<Map.Entry<String, Double>> wordMap = new ArrayList<Map.Entry<String, Double>>(distances.entrySet());
        Collections.sort(wordMap, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                double result=o2.getValue() - o1.getValue();
                if(result > 0)
                    return -1;
                else if(result == 0)
                    return 0;
                else
                    return 1;
            }
        });
        return wordMap;
    }

    public Map<String,Double> getP(NodeVO event,String place){
        Map<String,Double> intention=new HashMap<>();
        if(event.getName().indexOf("演训")!=-1){
            intention.put(event.getName(),1.0);
            return intention;
        }else if(event.getName().indexOf("转场")!=-1){
            intention.put(event.getName(),1.0);
            return intention;
        }else if(event.getName().indexOf("侦察")!=-1){
            intention.put(event.getName(),1.0);

            return intention;
        }
        List<NodeVO> next_events=graphQueryUtils.findIntention(event.getId().intValue(),place);
        for (NodeVO next_event:next_events){
            double p=graphQueryUtils.findP(event.getId().intValue(),next_event.getId().intValue());
            List<NodeVO> places=graphQueryUtils.findEventPlace(next_event.getId().intValue());
            for (NodeVO plc:places){
                Map<String,Double> temp=getP(next_event,plc.getName());
                for(String key:temp.keySet()) {
                    intention.put(key, temp.get(key) * p);
                }
            }
        }
//        System.out.println(intention);
        return intention;
    }

    public Map<String,Double> getIntention(String place){
        Map<String,Double> intention=new HashMap<>();
        intention.put("演训",0.0);
        intention.put("转场",0.0);
        intention.put("侦察",0.0);
        List<NodeVO> events=graphQueryUtils.findEvent(place);

        for (NodeVO event:events){
            if(event.getName().indexOf("抵达")==-1){
                continue;
            }
            Map<String,Double> temp=getP(event,place);
            for(String key:temp.keySet()){
                if (key.indexOf("演训")!=-1){
                    intention.replace("演训",intention.get("演训")+temp.get(key));
                }else if (key.indexOf("转场")!=-1){
                    intention.replace("转场",intention.get("转场")+temp.get(key));
                }else  if (key.indexOf("侦察")!=-1){
                    intention.replace("侦察",intention.get("侦察")+temp.get(key));
                }
            }
        }
        return intention;
    }

}
