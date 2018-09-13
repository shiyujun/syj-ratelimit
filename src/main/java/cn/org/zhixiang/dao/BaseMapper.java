package cn.org.zhixiang.dao;

import cn.org.zhixiang.entity.TokenLimit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/06
 * @描述
 */

public interface BaseMapper {


    @Select("select * from syj_rate_limit where `key` =#{key}")
    public TokenLimit getKey(@Param("key") String key);


    @Insert("REPLACE into syj_rate_limit set `key`=#{key} ,`value`=#{value},`lastPutTime`=#{lastPutTime}")
    void insert(TokenLimit tokenLimit);




}
