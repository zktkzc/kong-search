package com.tkzc00.kong_search_backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkzc00.kong_search_backend.model.entity.Picture;

/**
 * 图片服务
 */
public interface PictureService {
    Page<Picture> searchPicture(String searchText, long page, long pageSize);
}
