package com.tkzc00.kong_search_backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkzc00.kong_search_backend.common.BaseResponse;
import com.tkzc00.kong_search_backend.common.ErrorCode;
import com.tkzc00.kong_search_backend.common.ResultUtils;
import com.tkzc00.kong_search_backend.exception.BusinessException;
import com.tkzc00.kong_search_backend.model.dto.post.PostQueryRequest;
import com.tkzc00.kong_search_backend.model.dto.search.SearchRequest;
import com.tkzc00.kong_search_backend.model.dto.user.UserQueryRequest;
import com.tkzc00.kong_search_backend.model.entity.Picture;
import com.tkzc00.kong_search_backend.model.vo.PostVO;
import com.tkzc00.kong_search_backend.model.vo.SearchVO;
import com.tkzc00.kong_search_backend.model.vo.UserVO;
import com.tkzc00.kong_search_backend.service.PictureService;
import com.tkzc00.kong_search_backend.service.PostService;
import com.tkzc00.kong_search_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 查询接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Resource
    private PictureService pictureService;
    @Resource
    private UserService userService;
    @Resource
    private PostService postService;

    @PostMapping("/all")
    public BaseResponse<SearchVO> doSearchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();

        CompletableFuture<Page<PostVO>> postCompletableFuture = CompletableFuture.supplyAsync(() -> {
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            return postService.listPostVOByPage(postQueryRequest, request);
        });

        CompletableFuture<Page<Picture>> pictureCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return pictureService.searchPicture(searchText, 1, 10);
        });

        CompletableFuture<Page<UserVO>> userCompletableFuture = CompletableFuture.supplyAsync(() -> {
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            return userService.listUserVOByPage(userQueryRequest);
        });

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
            return ResultUtils.success(searchVO);
        } catch (Exception e) {
            log.error("查询异常", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
        }
    }
}
