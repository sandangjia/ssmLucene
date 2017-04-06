package com.erinic.ssm.service.impl;

import com.erinic.ssm.dao.ContentDao;
import com.erinic.ssm.domain.Content;
import com.erinic.ssm.service.ContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by asus on 2017/4/5.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Resource
    private ContentDao contentDao;

    @Override
    public List<Content> findContentList() {
        return contentDao.findList();
    }

    @Override
    public int insertSelective(Content content) {
        return contentDao.insert(content);
    }
}
