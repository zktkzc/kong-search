package com.tkzc00.kong_search_backend.model.dto.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkzc00.kong_search_backend.datasource.DataSource;
import com.tkzc00.kong_search_backend.model.dto.user.UserQueryRequest;
import com.tkzc00.kong_search_backend.model.vo.UserVO;
import com.tkzc00.kong_search_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现
 */
@Component
public class UserDataSource implements DataSource<UserVO> {
    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, int pageNum, int pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        userQueryRequest.setUserName(searchText);
        return userService.listUserVOByPage(userQueryRequest);
    }
}
