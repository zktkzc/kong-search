package com.tkzc00.kong_search_backend.model.dto.picture;

import com.tkzc00.kong_search_backend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 4475979759848391000L;

    /**
     * 搜索词
     */
    private String searchText;
}