package com.tkzc00.kong_search_backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkzc00.kong_search_backend.common.BaseResponse;
import com.tkzc00.kong_search_backend.common.ErrorCode;
import com.tkzc00.kong_search_backend.common.ResultUtils;
import com.tkzc00.kong_search_backend.exception.ThrowUtils;
import com.tkzc00.kong_search_backend.model.dto.picture.PictureQueryRequest;
import com.tkzc00.kong_search_backend.model.entity.Picture;
import com.tkzc00.kong_search_backend.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {
    @Resource
    private PictureService pictureService;

    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                         HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Picture> pictures = pictureService.searchPicture(pictureQueryRequest.getSearchText(), current, size);
        return ResultUtils.success(pictures);
    }
}
