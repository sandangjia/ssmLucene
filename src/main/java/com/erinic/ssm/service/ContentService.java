package com.erinic.ssm.service;

import com.erinic.ssm.domain.Content;

import java.util.List;

/**
 * Created by asus on 2017/4/5.
 */
public interface ContentService {

    List<Content> findContentList();

    int insertSelective(Content content) ;
}
