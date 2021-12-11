package edu.njust.mapper;

import edu.njust.model.TargetRecogWeights;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TargetsRecogWeightsMapper {

//    @Insert("insert into target_recog_weights(weight_basic,weight_rcs,weight_trackline,weight_tactics) values(0.2,0.2,0.4,0.2)")
    int insert(@Param("weights") TargetRecogWeights weights);
    int delete(String id);
    int update(String id, TargetRecogWeights weights);
    List<TargetRecogWeights> select(@Param("id") String id);
    List<TargetRecogWeights> selectAll();

}
