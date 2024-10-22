<template>
    <div class="carousel" @mousedown="startDrag" @mousemove="onDrag" @mouseup="endDrag" @mouseleave="endDrag">
        <!-- 图片展示区域 -->
        <div class="carousel-images" :style="carouselStyle">
            <div v-for="(image, index) in images" :key="index" class="carousel-image"
                :style="{ backgroundImage: `url(${image})` }"></div>
        </div>

        <!-- 底部指示点 -->
        <div class="indicators">
            <span v-for="(image, index) in images" :key="index" class="indicator"
                :class="{ active: index === currentIndex }" @click="goToSlide(index)"></span>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue';

// 图片列表，可以传递外部数据进来，这里简单写死
const images = [
    require('../image/PixPin_2024-09-30_10-43-07.png'),
    require('../image/PixPin_2024-09-30_10-43-24.png'),
];

const currentIndex = ref(0);  // 当前显示的图片索引
const intervalId = ref(null); // 自动播放的计时器 ID
let startX = 0;
let isDragging = ref(false);
let offsetX = ref(0);

// 切换到下一张
const nextSlide = () => {
    currentIndex.value = (currentIndex.value + 1) % images.length;
};

function prevSlide() {
    currentIndex.value = (currentIndex.value - 1 + images.length) % images.length;
}

// 跳转到指定索引的图片
const goToSlide = (index) => {
    currentIndex.value = index;
};

// 自动播放轮播图
const startAutoplay = () => {
    intervalId.value = setInterval(nextSlide, 3000); // 每3秒切换一张
};

// 清除自动播放
const stopAutoplay = () => {
    if (intervalId.value) {
        clearInterval(intervalId.value);
        intervalId.value = null;
    }
};
// 页面加载时启动自动播放
onMounted(() => {
    startAutoplay();
});

// 页面卸载时清除计时器
onUnmounted(() => {
    stopAutoplay();
});

// 动态计算轮播图的滚动样式
const carouselStyle = computed(() => {
    return {
        transform: `translateX(-${currentIndex.value * 100}%)`,
        transition: 'transform 0.5s ease'
    };
});

function startDrag(event) {
    startX = event.clientX;
    isDragging.value = true;
}

function onDrag(event) {
    if (isDragging.value) {
        offsetX.value = event.clientX - startX;
    }
}

function endDrag() {
    if (isDragging.value) {
        isDragging.value = false;
        // 滑动距离超过 50px 则切换图片
        if (offsetX.value > 50) {
            prevSlide();
        } else if (offsetX.value < -50) {
            nextSlide();
        }
        offsetX.value = 0;  // 复位偏移量
    }
}

</script>

<style scoped>
.carousel {
    position: relative;
    width: 100%;
    height: 100%;
    overflow: hidden;
    border-radius: 10px;
}

.carousel-images {
    display: flex;
    width: 100%;
    height: 100%;
    transition: transform 0.5s ease;
}

.carousel-image {
    width: 100%;
    flex-shrink: 0;
    height: 100%;
    background-size: cover;
    background-position: center;
}

.indicators {
    position: absolute;
    bottom: 10px;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
}

.indicator {
    width: 10px;
    height: 10px;
    background-color: white;
    border-radius: 50%;
    margin: 0 5px;
    cursor: pointer;
    opacity: 0.5;
}

.indicator.active {
    background-color: orange;
    opacity: 1;
}
</style>