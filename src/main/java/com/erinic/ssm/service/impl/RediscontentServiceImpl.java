package com.erinic.ssm.service.impl;

import com.erinic.ssm.dao.RedisContentDao;
import com.erinic.ssm.domain.Rediscontent;
import com.erinic.ssm.domain.RediscontentExample;
import com.erinic.ssm.service.RediscontentService;
import com.erinic.ssm.utils.PageEntity;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Function:
 *
 * @author chenjiec
 *         Date: 2016/12/9 上午12:17
 * @since JDK 1.7
 */
@Service
public class RediscontentServiceImpl implements RediscontentService {

    @Autowired
    private RedisContentDao redisContentDao;


    @Override
    public List<Rediscontent> selectByExample(RediscontentExample example) {
        return redisContentDao.selectByExample(example);
    }

    @Override
    public Rediscontent selectByPrimaryKey(Integer id) {
        return redisContentDao.selectByPrimaryKey(id);
    }

    @Override
    public PageEntity<Rediscontent> selectByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //因为是demo，所以这里默认没有查询条件。
        List<Rediscontent> rediscontents = redisContentDao.selectByExample(new RediscontentExample());
        PageEntity<Rediscontent> rediscontentPageEntity = new PageEntity<Rediscontent>();
        rediscontentPageEntity.setList(rediscontents);
        int size = redisContentDao.selectByExample(new RediscontentExample()).size();
        rediscontentPageEntity.setCount(size);
        return rediscontentPageEntity;
    }
}
