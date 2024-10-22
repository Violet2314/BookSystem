<template>
    <div class="mainbox">
        <LeftNav @update-tab="handleTabUpdate" :class="{ 'nav-hidden': !isMenVisible }" />
        <div class="main">
            <TopNav :context="currentTab" @toggle-menu="toggleMenu" />
            <div class="maincontainer" style="position: relative;">
                <TransitionAnimation ref="loadingRef" :checkLoading="checkLoading" />
                <router-view />
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, nextTick} from 'vue';
import LeftNav from './components/LeftNav.vue'
import TopNav from './components/TopNav.vue'
import TransitionAnimation from './components/TransitionAnimation.vue';
import router from './router';
import { fetchBooks } from './service/selectbookservice';

const currentTab = ref('notice');
const isMenVisible = ref(true);
const loadingRef = ref(null);

const checkLoading = () => {
    const timer = setInterval(() => {
        if (document.readyState === 'complete') {
            clearInterval(timer);
            loadingRef.value.outLoading();
        }
    }, 300);
};

const toggleMenu = () => {
    console.log(isMenVisible.value)
    isMenVisible.value = !isMenVisible.value;
}

const handleTabUpdate = (newTab) => {
    currentTab.value = newTab;
};

onMounted(async () => {
    await fetchBooks();
    checkLoading();
    nextTick(() => {
        // 可以优化的地方
        // 这个问题的核心在于：router.beforeEach 会全局注册且不会自动卸载，即使组件被卸载后，之前注册的路由守卫依然存在，
        // 因此导致你在多次跳转后，beforeEach 钩子被多次触发。这就是为什么在你从 / 跳到 /login 再跳回 / 时，console.log 输出的值会有两次，并且会输出两个 null 和两个正确的值。
        router.beforeEach((to, from, next) => {
            if (loadingRef.value) {
                // 如果目标路由不是 login 页面，才执行 inLoading()
                if (to.path !== '/login' && from !== '/login') {
                    loadingRef.value.inLoading(next);
                } else {
                    next();  // 直接跳转到目标页面
                }
            } else {
                next();
            }
        });
    });
});
</script>

<style>
* {
    outline: none;
    /* 禁用蓝色框 */
    -webkit-tap-highlight-color: transparent;
    /* 禁用点击高亮效果 */
    user-select: none;
}

body {
    margin: 0;
    padding: 0;
}

.mainbox {
    width: 100%;
    height: 100%;
    min-height: 100vh;
    display: flex;
}

.maincontainer {
    height: calc(100% - 57px);
}

.leftnavbox {
    width: 210px;
    transition: transform 0.3s ease-in-out;
}

.nav-hidden {
    transform: translateX(-100%);
}

.main {
    flex-grow: 1;
    transition: margin-left 0.3s ease-in-out;
}

.nav-hidden+.main {
    margin-left: -210px;
}
</style>