package com.tkzc00.kong_search_backend;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tkzc00.kong_search_backend.model.entity.Picture;
import com.tkzc00.kong_search_backend.model.entity.Post;
import com.tkzc00.kong_search_backend.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CrawlerTest {
    @Resource
    private PostService postService;

    @Test
    void testFetchPictures() throws IOException {
        int current = 1;
        String url = "https://cn.bing.com/images/search?q=%e5%9b%be%e7%89%87&form=HDRSC2&first=" + current;
        Document doc = Jsoup.connect(url).get();
        System.out.println("doc = " + doc);
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            String m = element.select(".iusc").get(0).attr("m");
            System.out.println("m = " + m);
            Map map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            System.out.println("murl = " + murl);
            String title = element.select(".inflnk").get(0).attr("aria-label");
            System.out.println("title = " + title);
            Picture picture = new Picture();
            picture.setUrl(murl);
            picture.setTitle(title);
            pictureList.add(picture);
        }
        System.out.println("pictureList = " + pictureList);
    }

    @Test
    void testFetchPassage() {
        // 获取数据
        String json = "{\n" +
                "  \"current\": 1,\n" +
                "  \"pageSize\": 8,\n" +
                "  \"sortField\": \"createTime\",\n" +
                "  \"sortOrder\": \"descend\",\n" +
                "  \"category\": \"文章\",\n" +
                "  \"reviewStatus\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest.post(url).body(json).execute().body();
        System.out.println("result = " + result);
        // json转对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
        System.out.println("map = " + map);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        System.out.println("records = " + records);
        List<Post> postList = JSONUtil.toList(records, Post.class);
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);
            postList.add(post);
            Picture picture = new Picture();
            picture.setUrl(tempRecord.getStr("cover"));
        }
        System.out.println("postList = " + postList);
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }
}
