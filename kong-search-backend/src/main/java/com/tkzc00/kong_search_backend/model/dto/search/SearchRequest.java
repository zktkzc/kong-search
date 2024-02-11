package com.tkzc00.kong_search_backend.model.dto.search;

import com.tkzc00.kong_search_backend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 6786362333031005822L;

    private String searchText;

    private String type;
}
