package com.tkzc00.kong_search_backend.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口，新接入数据源必须实现
 */
public interface DataSource<T> {
    /**
     * 搜索
     * @param searchText 关键词
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页数据
     */
    Page<T> doSearch(String searchText, int pageNum, int pageSize);
}
