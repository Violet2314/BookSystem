<template>
    <div class="NoticeBoard">
        <div class="news-box">
            <div class="page-title">
                NEWS
            </div>
            <div class="new-main">
                <div class="news-banner">
                    <SimpleCarousel />
                </div>
                <div class="news-container">
                    <div class="news-container-transform">
                        <section class="menu-tab">
                            <div class="tab-container">
                                <div class="tab-item" :class="{ 'tab-now': currentTab === 'latest' }"
                                    @click="currentTab = 'latest'">最新</div>
                                <div class="tab-item" :class="{ 'tab-now': currentTab === 'announcement' }"
                                    @click="currentTab = 'announcement'">公告</div>
                                <div class="tab-item" :class="{ 'tab-now': currentTab === 'news' }"
                                    @click="currentTab = 'news'">新闻</div>
                                <div class="tab-item" :class="{ 'tab-now': currentTab === 'event' }"
                                    @click="currentTab = 'event'">活动</div>
                                <div class="tab-navi">
                                    <div class="tab-navi-now" :style="naviStyle"></div>
                                </div>
                            </div>
                        </section>
                        <section class="article-container">
                            <transition name="slide-fade">
                                <div class="article-lists" v-if="currentTab === 'latest'"
                                    :style="{ opacity: isFading ? 0 : 1 }">
                                    <ArticleItem :time="'2024-10-01'" :content="'败犬女主12话'" :type="'公告'" />
                                    <ArticleItem :time="'2024-10-01'" :content="'败犬女主11话'" :type="'公告'" />
                                    <ArticleItem :time="'2024-10-01'" :content="'败犬女主10话'" :type="'公告'" />
                                    <ArticleItem :time="'2024-10-01'" :content="'败犬女主9话'" :type="'公告'" />
                                    <ArticleItem :time="'2024-10-01'" :content="'败犬女主8话'" :type="'公告'" />
                                    <ArticleItem :time="'2024-10-01'" :content="'败犬女主7话'" :type="'公告'" />
                                </div>
                            </transition>
                            <transition name="slide-fade">
                                <div class="article-lists" v-if="currentTab === 'announcement'"
                                    :style="{ opacity: isFading ? 0 : 1 }">
                                    <ArticleItem :time="'2024-10-01'" :content="'败犬女主7话'" :type="'公告'" />
                                </div>
                            </transition>
                            <transition name="slide-fade">
                                <div class="article-lists" v-if="currentTab === 'news'"
                                    :style="{ opacity: isFading ? 0 : 1 }">
                                    <ArticleItem :time="'2024-10-01'" :content="'败犬女主6话'" :type="'新闻'" />
                                </div>
                            </transition>
                            <transition name="slide-fade">
                                <div class="article-lists" v-if="currentTab === 'event'"
                                    :style="{ opacity: isFading ? 0 : 1 }">
                                    <ArticleItem :time="'2024-10-01'" :content="'败犬女主5话'" :type="'活动'" />
                                </div>
                            </transition>
                        </section>
                        <div class="new-more">
                            <span>MORE</span>
                            <i class="arrow-icon">→</i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue';
import ArticleItem from '../components/ArticleItem.vue';
import SimpleCarousel from '../components/SimpleCarousel.vue';

const currentTab = ref('latest');

const isFading = ref(false); // 用于控制 opacity

watch(currentTab, () => {
    isFading.value = true; // 在切换标签时开始淡出
    setTimeout(() => {
        isFading.value = false; // 0.5秒后重置状态以允许淡入
    }, 500); // 500毫秒与 CSS 过渡时间一致
});

const naviStyle = computed(() => {
    if (currentTab.value === 'latest') {
        return {
            left: '20.5px'
        };
    } else if (currentTab.value === 'announcement') {
        return {
            left: '92.5px'
        };
    } else if (currentTab.value === 'news') {
        return {
            left: '164.5px'
        };
    } else if (currentTab.value === 'event') {
        return {
            left: '236.5px'
        };
    }
    return {}; // 默认返回空对象
});
</script>

<style>
@import url("../css/NoticeBoard.css");
</style>