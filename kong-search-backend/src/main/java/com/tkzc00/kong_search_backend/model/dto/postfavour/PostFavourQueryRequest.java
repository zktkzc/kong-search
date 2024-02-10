package com.tkzc00.kong_search_backend.model.dto.postfavour;

import java.io.Serializable;

import com.tkzc00.kong_search_backend.common.PageRequest;
import com.tkzc00.kong_search_backend.model.dto.post.PostQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帖子收藏查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostFavourQueryRequest extends PageRequest implements Serializable {

    /**
     * 帖子查询请求
     */
    private PostQueryRequest postQueryRequest;

    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}