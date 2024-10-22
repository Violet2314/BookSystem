<template>
    <div class="topnavbox">
        <div class="left">
            <img src="../image/menu.svg" alt="" @click="$emit('toggle-menu')">
            <!-- 使用 Vue 的 <transition> 组件来包裹要进行过渡的内容 -->
            <transition name="slide-context" mode="out-in">
                <p :key="context">{{ context }}</p>
            </transition>
        </div>
        <div class="right">
            <TeamlogoDrop class="logodrop"/>
        </div>
    </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import TeamlogoDrop from "./TeamlogoDrop.vue";

defineProps({
    context: {
        type: String,
        Required: true,
    }
});

defineEmits(['toggle-menu']);
</script>

<style>
.topnavbox {
    border-bottom: 2px solid rgba(0, 0, 0, 0.109);
    display: flex;
    justify-content: space-between;
}

.topnavbox .left {
    height: 55px;
    display: flex;
    margin-left: 1em;
}

.topnavbox p {
    font-weight: bold;
    margin-left: 2em;
}

.left img {
    width: 28px
}

.right img {
    margin-right: 4em;
    height: 48px;
}

/* 定义 slide-fade 过渡效果 */
.slide-context-enter-active,
.slide-context-leave-active {
    transition: opacity 0.5s ease, transform 0.5s ease;
}

.slide-context-enter-from {
    opacity: 0;
    transform: translateX(100%);
    /* 新元素从右侧划入 */
}

.slide-context-leave-to {
    opacity: 0;
    transform: translateX(100%);
    /* 旧元素向左侧划出 */
}
</style>