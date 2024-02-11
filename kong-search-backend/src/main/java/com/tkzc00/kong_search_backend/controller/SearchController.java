package com.tkzc00.kong_search_backend.controller;

import com.tkzc00.kong_search_backend.common.BaseResponse;
import com.tkzc00.kong_search_backend.common.ResultUtils;
import com.tkzc00.kong_search_backend.manager.SearchFacade;
import com.tkzc00.kong_search_backend.model.dto.search.SearchRequest;
import com.tkzc00.kong_search_backend.model.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 查询接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> doSearchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        SearchVO searchVO = searchFacade.doSearchAll(searchRequest, request);
        return ResultUtils.success(searchVO);
    }
}
