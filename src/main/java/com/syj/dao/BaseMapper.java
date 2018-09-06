package com.syj.dao;

import com.syj.entity.TokenLimit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/06
 * @描述
 */
public interface BaseMapper {


    @Select("select COALESCE(max(`value`),-1) from syj_rate_limit where `key` =#{key}")
    public Integer getKey(@Param("key") String key);

    @Update("update syj_rate_limit set `value` =#{value} where `key` =#{key}")
    void updateValue(@Param("key") String key,@Param("value")Integer value);

    @Insert("insert into syj_rate_limit(`key`,`value`) values(#{key},#{value})")
    void insert(@Param("key")String key, @Param("value")Long value);

    @Update("update syj_rate_limit set `value` =0")
    void clearValue();

    @Insert("insert into syj_rate_limit(`key`,`value`,`limit`) values(#{key},#{value},#{value})")
    void insertValueAndLimit(@Param("key")String key, @Param("value")Long value);

    @Select("select `key`,`value`,limit from syj_rate_limit")
    List<TokenLimit> getAll();

    @Update("<foreach collection='list' separator=';' item='cus'>  update syj_rate_limit set `value` =#{value} where `key` =#{key}  </foreach>")
    void batchUpdate(List<TokenLimit> tokenLimitList);
}
