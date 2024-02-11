package com.tkzc00.kong_search_backend.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkzc00.kong_search_backend.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜素
 *
 * @author tkzc00
 */
@Data
public class SearchVO implements Serializable {
    private static final long serialVersionUID = -6003010961310767430L;

    private List<UserVO> userList;
    private List<Picture> pictureList;
    private List<PostVO> postList;
    private List<Object> dataList;
}
