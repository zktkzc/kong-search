# 聚合搜索平台-空搜索

> by tkzc00

## 项目介绍

一个聚合搜索平台，可以让用户在同一个入口（同一个页面）集中搜索出不同来源、不同类型的内容。

对用户来说：提升用户的检索效率、提升用户体验

对企业来说：无需针对每一个项目都去开发一个搜索功能，当有新的内容、新的网站时，可以复用同一套搜索系统，提升开发效率

## 技术栈

### 前端

- Vue
- Ant Design Vue
- Lodash

### 后端

- SpringBoot
- MySQL
- ElasticSearch（Elastic Stack）搜索引擎
- 数据抓取
- 数据同步
  - logstash
  - Canal
- Guava Retrying
- 如何保证 API 的稳定性