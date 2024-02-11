<script setup lang="ts">
import { ref, watchEffect } from "vue";
import PostList from "@/components/PostList.vue";
import UserList from "@/components/UserList.vue";
import PictureList from "@/components/PictureList.vue";
import MyDivider from "@/components/MyDivider.vue";
import { useRoute, useRouter } from "vue-router";
import myAxios from "@/plugins/myAxios";
import { message } from "ant-design-vue";

const postList = ref([]);
const userList = ref([]);
const pictureList = ref([]);
const router = useRouter();
const route = useRoute();
const activeKey = ref("post");
const searchText = ref(route.params.text || "");
const initSearchParams = {
  text: "",
  type: activeKey.value,
  pageNum: 1,
  pageSize: 10,
};
const loadData = async (params: any) => {
  const { type } = params;
  if (!type) {
    message.error("搜索类型为空");
    return;
  }
  const query = {
    ...params,
    searchText: params.text,
  };
  await myAxios.post("/search/all", query).then((res: any) => {
    if (type === "post") postList.value = res.dataList;
    else if (type === "picture") pictureList.value = res.dataList;
    else if (type === "user") userList.value = res.dataList;
  });
};
const loadAllData = (params: any) => {
  const query = {
    ...params,
    searchText: params.text,
  };
  myAxios.post("/search/all", query).then((res: any) => {
    postList.value = res.postList;
    pictureList.value = res.pictureList;
    userList.value = res.userList;
  });
};
const searchParams = ref(initSearchParams);
watchEffect(async () => {
  searchParams.value = {
    ...initSearchParams,
    type: activeKey.value,
    text: route.query.text as string,
  };
  activeKey.value = route.params.category as string;
  await loadData(searchParams.value);
});
const onSearch = async (value: string) => {
  router.push({
    query: {
      ...searchParams.value,
      text: value,
    },
  });
  await loadData(searchParams.value);
};
const onTabChange = (key: string) => {
  activeKey.value = key;
  router.push({
    path: `/${key}`,
    query: searchParams.value,
  });
};
</script>

<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchText"
      enter-button="搜索"
      size="large"
      placeholder="Search"
      @search="onSearch"
    />
    <my-divider />
    <a-tabs v-model:active-key="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="文章">
        <PostList :post-list="postList" />
      </a-tab-pane>
      <a-tab-pane key="picture" tab="图片">
        <PictureList :picture-list="pictureList" />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList :user-list="userList" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<style scoped></style>
