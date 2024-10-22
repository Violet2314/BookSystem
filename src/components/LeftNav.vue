<template>
    <transition name="slide-menu">
        <div class="leftnavbox">
            <div class="logo1">
                <img src="../assets/404team.png" alt="">
                <p>404team</p>
            </div>
            <div class="function">
                <router-link to="/" :class="{ 'tab-now': currentTab === 'notice' }"
                    @click="setTab('notice')">公告</router-link>
                <router-link to="/selectbook" :class="{ 'tab-now': currentTab === 'select' }"
                    @click="setTab('select')">查询书籍</router-link>
                <router-link to="/returnbook" :class="{ 'tab-now': currentTab === 'return' }"
                    @click="setTab('return')">还书</router-link>
                <router-link to="/recommend" :class="{ 'tab-now': currentTab === 'recommend' }"
                    @click="setTab('recommend')">推荐</router-link>
                <router-link to="/addbook" :class="{ 'tab-now': currentTab === 'Borrowedbooks' }"
                    @click="setTab('Borrowedbooks')">书籍管理</router-link>
            </div>
        </div>
    </transition>
</template>

<script setup>
import { ref, defineEmits } from "vue";

const currentTab = ref('notice');
const emit = defineEmits(['update-tab']);

const setTab = (tab) => {
    currentTab.value = tab;
    emit('update-tab', tab);  // 发出事件，并传递选项卡的值
};
</script>

<style>
.leftnavbox {
    display: flex;
    flex-direction: column;
    transition: transform 0.3s ease-in-out;
}

.logo1 {
    height: 56px;
    display: flex;
    flex-direction: row;
    background-color: #1a1a1a71;
}

.logo1 p {
    font-weight: bold;
    margin: auto auto;
}

.leftnavbox img {
    width: 30%;
}

.function {
    flex-grow: 1;
    background-color: #1a1a1abc
}

.function a {
    border-bottom: 2px solid rgba(255, 255, 255, 0.499);
    color: white;
    text-decoration: none;
    display: block;
    padding: 0.5em;
    padding-top: 1.5em;
    font-size: 1.2em;
    text-align: left;
    font-weight: 400;
    transition: background-color 0.5s ease, color 0.5s ease;
}

.function a:hover {
    background-color: rgba(55, 55, 55, 0.791);
}

.tab-now {
    color: #ffe692e5 !important;
}
</style>