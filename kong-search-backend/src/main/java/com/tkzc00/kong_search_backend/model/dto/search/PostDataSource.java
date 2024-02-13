package com.tkzc00.kong_search_backend.model.dto.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkzc00.kong_search_backend.datasource.DataSource;
import com.tkzc00.kong_search_backend.model.dto.post.PostQueryRequest;
import com.tkzc00.kong_search_backend.model.entity.Post;
import com.tkzc00.kong_search_backend.model.vo.PostVO;
import com.tkzc00.kong_search_backend.service.PostService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子服务实现
 */
@Component
public class PostDataSource implements DataSource<PostVO> {
    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, int pageNum, int pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);
        postQueryRequest.setSearchText(searchText);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        return postService.getPostVOPage(postPage, request);
    }
}




