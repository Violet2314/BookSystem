<template>
    <div class="article-item">
        <span class="article-item-type">{{ type }}</span>
        <span class="article-item-content">{{ content }}</span>
        <span class="article-item-time">{{ time }}</span>
    </div>
</template>

<script setup>
import { defineProps } from 'vue';
defineProps({
    time: {
        type: String,
        Required: true
    },
    content: {
        type: String,
        Required: true
    },
    type: {
        type: String,
        Required: true,
    },
});
</script>

<style>
.article-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #f2f2f232;
    color: #f2f2f2;
    border-radius: 5px;
    padding: 15px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.892);
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
    transition: background-color 0.3s ease, transform 0.3s ease;
    cursor: pointer;
}

.article-item-type {
    display: inline-block;
    border: 1px solid #f2f2f2;
    border-radius: 50px;
    padding: 5px 20px;
    font-size: 14px;
    color: #f2f2f2;
    margin-right: 10px;
}

.article-item-content {
    margin-left: 2em;
    margin-bottom: 1.5px;
    flex: 1;
    font-size: 16px;
    font-weight: bold;
}

.article-item-time {
    margin-top: 2px;
    font-size: 14px;
    color: #9d9895;
    white-space: nowrap;
}

.article-item:hover {
    background-color: #f2f2f261;
    transform: translateY(-5px);
}
</style>