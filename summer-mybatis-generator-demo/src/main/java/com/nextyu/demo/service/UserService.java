package com.nextyu.demo.service;

import com.github.pagehelper.PageInfo;
import com.nextyu.demo.entity.User;
import com.nextyu.demo.query.UserQuery;
import com.nextyu.demo.vo.UserVO;
import java.util.List;

public interface UserService {
    Boolean save(UserVO userVO);

    UserVO getById(Long id);

    Boolean update(UserVO userVO);

    List<UserVO> listAll();

    List<UserVO> listPage(UserQuery query);

    PageInfo<UserVO> getPageInfo(UserQuery query);
}