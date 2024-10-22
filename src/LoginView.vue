<template>
    <div class="auth-container">
        <router-view />
    </div>
</template>

<script setup>
</script>

<style scoped>
.auth-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f5f5f5;
}
* {
    outline: none;
    /* 禁用蓝色框 */
    -webkit-tap-highlight-color: transparent;
    /* 禁用点击高亮效果 */
    user-select: none;
}
</style>