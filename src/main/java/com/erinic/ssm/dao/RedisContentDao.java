package com.erinic.ssm.dao;

import com.erinic.ssm.domain.Rediscontent;
import com.erinic.ssm.domain.RediscontentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by asus on 2017/4/6.
 */
public interface RedisContentDao {
    int countByExample(RediscontentExample example);

    int deleteByExample(RediscontentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Rediscontent record);

    int insertSelective(Rediscontent record);

    List<Rediscontent> selectByExample(RediscontentExample example);

    Rediscontent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Rediscontent record, @Param("example") RediscontentExample example);

    int updateByExample(@Param("record") Rediscontent record, @Param("example") RediscontentExample example);

    int updateByPrimaryKeySelective(Rediscontent record);

    int updateByPrimaryKey(Rediscontent record);
}
