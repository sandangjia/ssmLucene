package com.erinic.ssm.dao;

import com.erinic.ssm.domain.Content;

import java.util.List;

/**
 * Created by asus on 2017/4/5.
 */
public interface ContentDao {

    List<Content> findList();

    int insert(Content content);
}
