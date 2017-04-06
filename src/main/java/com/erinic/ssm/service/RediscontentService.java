package com.erinic.ssm.service;


import com.erinic.ssm.domain.Rediscontent;
import com.erinic.ssm.domain.RediscontentExample;
import com.erinic.ssm.utils.PageEntity;

import java.util.List;

/**
 * Function:
 *
 * @author chenjiec
 *         Date: 2016/12/9 上午12:16
 * @since JDK 1.7
 */
public interface RediscontentService {
    List<Rediscontent> selectByExample(RediscontentExample example);

    Rediscontent selectByPrimaryKey(Integer id);

    PageEntity<Rediscontent> selectByPage(Integer pageNum, Integer pageSize);

}
