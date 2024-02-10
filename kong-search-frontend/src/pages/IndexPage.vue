<script setup lang="ts">
import { ref, watchEffect } from "vue";
import PostList from "@/components/PostList.vue";
import UserList from "@/components/UserList.vue";
import PictureList from "@/components/PictureList.vue";
import MyDivider from "@/components/MyDivider.vue";
import { useRoute, useRouter } from "vue-router";
import myAxios from "@/plugins/myAxios";

const router = useRouter();
const route = useRoute();
const activeKey = ref("article");
const initSearchParams = {
  text: "",
  pageNum: 1,
  pageSize: 10,
};
const searchParams = ref(initSearchParams);
watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    text: route.query.text as string,
  };
  activeKey.value = (route.params.category as string) || "article";
});
const onSearch = (value: string) => {
  router.push({
    query: searchParams.value,
  });
};
const onTabChange = (key: string) => {
  activeKey.value = key;
  router.push({
    path: `/${key}`,
    query: searchParams.value,
  });
};
const postList = ref([]);
myAxios.post("/post/list/page/vo", {}).then((res: any) => {
  postList.value = res.records;
});
const userList = ref([]);
myAxios.post("/user/list/page/vo", {}).then((res: any) => {
  userList.value = res.records;
});
</script>

<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchParams.text"
      enter-button="搜索"
      size="large"
      placeholder="Search"
      @search="onSearch"
    />
    <my-divider />
    <a-tabs v-model:active-key="activeKey" @change="onTabChange">
      <a-tab-pane key="article" tab="文章">
        <PostList :post-list="postList" />
      </a-tab-pane>
      <a-tab-pane key="picture" tab="图片">
        <PictureList />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList :user-list="userList" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<style scoped></style>
