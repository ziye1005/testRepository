package edu.njust.mapper.oracle;

import edu.njust.model.oracle.Membership;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MembershipMapper {
    @Select("SELECT * FROM \"membership\" WHERE \"id\" = #{id}")
    Membership getMembershipById(int id);

    @Insert("INSERT INTO \"membership\" VALUES (#{id},#{threshold},#{k},#{type})")
    int addMembership(Membership membership);

    @Update("UPDATE \"membership\" SET \"threshold\"=#{threshold},\"k\"=#{k},\"type\"=#{type} WHERE \"id\"=#{id}")
    int updateMembership(Membership membership);
}
