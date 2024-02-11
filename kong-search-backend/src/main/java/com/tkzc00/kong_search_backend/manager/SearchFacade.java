package com.tkzc00.kong_search_backend.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkzc00.kong_search_backend.common.ErrorCode;
import com.tkzc00.kong_search_backend.datasource.DataSource;
import com.tkzc00.kong_search_backend.datasource.DataSourceRegistry;
import com.tkzc00.kong_search_backend.exception.BusinessException;
import com.tkzc00.kong_search_backend.exception.ThrowUtils;
import com.tkzc00.kong_search_backend.model.dto.search.PictureDataSource;
import com.tkzc00.kong_search_backend.model.dto.search.PostDataSource;
import com.tkzc00.kong_search_backend.model.dto.search.SearchRequest;
import com.tkzc00.kong_search_backend.model.dto.search.UserDataSource;
import com.tkzc00.kong_search_backend.model.entity.Picture;
import com.tkzc00.kong_search_backend.model.enums.SearchTypeEnum;
import com.tkzc00.kong_search_backend.model.vo.PostVO;
import com.tkzc00.kong_search_backend.model.vo.SearchVO;
import com.tkzc00.kong_search_backend.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索门面
 */
@Component
@Slf4j
public class SearchFacade {
    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PostDataSource postDataSource;
    @Resource
    private DataSourceRegistry dataSourceRegistry;

    public SearchVO doSearchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();
        String type = searchRequest.getType();
        int current = searchRequest.getCurrent();
        int pageSize = searchRequest.getPageSize();
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        SearchTypeEnum typeEnum = SearchTypeEnum.getEnumByValue(type);
        // 查询所有数据
        if (typeEnum == null) {
            CompletableFuture<Page<PostVO>> postCompletableFuture = CompletableFuture.supplyAsync(() -> postDataSource.doSearch(searchText, current, pageSize));

            CompletableFuture<Page<Picture>> pictureCompletableFuture = CompletableFuture.supplyAsync(() -> pictureDataSource.doSearch(searchText, current, pageSize));

            CompletableFuture<Page<UserVO>> userCompletableFuture = CompletableFuture.supplyAsync(() -> userDataSource.doSearch(searchText, current, pageSize));

            CompletableFuture.allOf(pictureCompletableFuture, postCompletableFuture, userCompletableFuture).join();
            Page<Picture> picturePage = null;
            Page<PostVO> postPage = null;
            Page<UserVO> userPage = null;
            try {
                picturePage = pictureCompletableFuture.get();
                postPage = postCompletableFuture.get();
                userPage = userCompletableFuture.get();
                SearchVO searchVO = new SearchVO();
                searchVO.setPictureList(picturePage.getRecords());
                searchVO.setPostList(postPage.getRecords());
                searchVO.setUserList(userPage.getRecords());
                return searchVO;
            } catch (Exception e) {
                log.error("查询异常", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        } else {
            SearchVO searchVO = new SearchVO();
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(typeEnum);
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            searchVO.setDataList((List<Object>) page.getRecords());
            return searchVO;
        }
    }
}
