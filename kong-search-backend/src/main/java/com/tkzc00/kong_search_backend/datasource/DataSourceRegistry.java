package com.tkzc00.kong_search_backend.datasource;

import com.tkzc00.kong_search_backend.model.dto.search.PictureDataSource;
import com.tkzc00.kong_search_backend.model.dto.search.PostDataSource;
import com.tkzc00.kong_search_backend.model.dto.search.UserDataSource;
import com.tkzc00.kong_search_backend.model.enums.SearchTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRegistry {
    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PostDataSource postDataSource;

    private Map<String, DataSource<?>> typeDataSourceMap;

    @PostConstruct
    public void doInit() {
        typeDataSourceMap = new HashMap<>();
        typeDataSourceMap.put(SearchTypeEnum.POST.getValue(), postDataSource);
        typeDataSourceMap.put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
        typeDataSourceMap.put(SearchTypeEnum.USER.getValue(), userDataSource);
    }

    public DataSource<?> getDataSourceByType(SearchTypeEnum type) {
        if (typeDataSourceMap == null || typeDataSourceMap.isEmpty()) {
            return null;
        }
        return typeDataSourceMap.get(type.getValue());
    }
}
