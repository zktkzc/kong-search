package com.tkzc00.kong_search_backend.model.esdao;

import com.tkzc00.kong_search_backend.model.dto.post.PostEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 帖子 Es 操作
 *
 * @author tkzc00
 */
public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {
    List<PostEsDTO> findByUserId(Long userId);

    List<PostEsDTO> findByTitle(String title);
}
