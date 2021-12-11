package edu.njust.mapper.oracle;

import edu.njust.model.oracle.Node;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NodeMapper {
    @Insert("INSERT INTO \"node\" (\"id\", \"name\", \"type\", \"state\", \"cpt\") VALUES (#{id}, #{name}, #{type}, #{state}, #{cpt})")
    int addNode(Node node);

    int findIdByNameAndType(String name, int type);

    @Select("SELECT \"name\" FROM \"node\" WHERE \"id\"=#{id}")
    String findNameById(int id);

    @Select("SELECT * FROM \"node\" WHERE \"type\"=#{type}")
    List<Node> findAllNodeByType(int type);

    @Update("UPDATE \"node\" SET \"cpt\"=#{cpt} where \"name\"=#{name}  AND \"type\"=#{type}")
    int updateCPTByNameAndType(String name, int type, String cpt);

    @Delete("DELETE FROM \"node\" WHERE \"id\"=#{id}")
    int deleteNodeNyId(int id);

    @Select("SELECT MAX(\"id\") FROM \"node\"")
    String getMaxId();

    @Select("SELECT * FROM \"node\" WHERE \"id\"=#{id}")
    Node findNodeNyId(int id);

}
