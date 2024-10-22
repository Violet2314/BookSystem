<template>
    <div class="infinitebox" ref="infiniteComponent">
        <div class="photos" ref="container" @mousedown="onMousedown" @mouseup="onMouseup" @mouseleave="onMouseleave"
            @mousemove="onMousemove" @touchstart="onTouchstart" @touchend="onTouchend" @touchmove="onTouchmove">
            <div class="photos_line">
                <div class="photos_line_photo" v-for="(photo, index) in imgs1" :key="index">
                    <img :src="photo.src" alt="">
                    <p>I love HK416</p>
                </div>
            </div>
            <div class="photos_line">
                <div class="photos_line_photo" v-for="(photo, index) in imgs2" :key="index">
                    <img :src="photo.src" alt="">
                    <p>I love HK416</p>
                </div>
            </div>
            <div class="photos_line">
                <div class="photos_line_photo" v-for="(photo, index) in imgs3" :key="index">
                    <img :src="photo.src" alt="">
                    <p>I love HK416</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import gsap from 'gsap';
import { onMounted, ref, onBeforeUnmount, nextTick, onActivated } from 'vue';

const container = ref(null);
const img_data = ref([]);
const imgs1 = ref([]);
const imgs2 = ref([]);
const imgs3 = ref([]);
const container_width = ref(0);
const container_height = ref(0);
const photo_width = ref(0);
const photo_height = ref(0);
const mouse_x = ref(0);
const mouse_y = ref(0);
const ifmoveable = ref(false);
const standard_width = ref(1440);
const scale_nums = ref(1);
const mediaQuery = window.matchMedia("(max-width: 800px)");
const wokk = ref(true);

const resize = () => {
    let imgs = [...document.querySelectorAll(".photos_line_photo")];
    container_width.value = container.value.offsetWidth;
    container_height.value = container.value.offsetHeight;
    photo_width.value = imgs[0].offsetWidth;
    photo_height.value = imgs[0].offsetHeight;
    if (mediaQuery.matches) {
        // 如果媒体查询匹配，将 myValue 改为其他值
        standard_width.value = 600;
    } else {
        // 否则恢复默认值
        standard_width.value = 1440;
    }
    scale_nums.value = document.body.offsetWidth / standard_width.value;
    container.value.style.transform = `scale(${scale_nums.value})`;
    gsap.to(imgs, { transform: `translate(0,0)`, duration: 0, ease: 'power4.out' });

    img_data.value = imgs.map((img) => ({
        node: img,
        x: img.offsetLeft,
        y: img.offsetTop,
        movx: 0,
        movy: 0,
        ani: null,
    }));
};

const move = (x, y) => {
    if (!ifmoveable.value) return;
    const distanceX = (x - mouse_x.value) / scale_nums.value;
    const distanceY = (y - mouse_y.value) / scale_nums.value;

    img_data.value.forEach((img) => {
        let duration = 1;
        img.movx += distanceX;

        if (img.movx + img.x > container_width.value) {
            img.movx -= container_width.value;
            duration = 0;
        }

        if (img.movx + img.x < -photo_width.value) {
            img.movx += container_width.value;
            duration = 0;
        }

        img.movy += distanceY;

        if (img.movy + img.y > container_height.value) {
            img.movy -= container_height.value;
            duration = 0;
        }

        if (img.movy + img.y < -photo_height.value) {
            img.movy += container_height.value;
            duration = 0;
        }
        if (img.ani) img.ani.kill();
        img.ani = gsap.to(img.node, {
            transform: `translate(${img.movx}px,${img.movy}px)`,
            duration,
            ease: 'power4.out'
        });
    });

    mouse_x.value = x;
    mouse_y.value = y;
};

const onMousedown = (event) => {
    ifmoveable.value = true;
    mouse_x.value = event.clientX;
    mouse_y.value = event.clientY;
};

const onMouseup = () => {
    ifmoveable.value = false;
};

const onMouseleave = () => {
    ifmoveable.value = false;
};

const onMousemove = (event) => {
    move(event.clientX, event.clientY);
};

// 触摸开始
const onTouchstart = (event) => {
    ifmoveable.value = true;
    const touch = event.touches[0]; // 获取第一个触摸点
    mouse_x.value = touch.clientX;
    mouse_y.value = touch.clientY;
};

// 触摸移动
const onTouchmove = (event) => {
    if (!ifmoveable.value) return;
    const touch = event.touches[0];
    move(touch.clientX, touch.clientY);
};

// 触摸结束
const onTouchend = () => {
    ifmoveable.value = false;
};

const init = () => {
    console.log("init");
    for (let i = 1; i <= 6; i++) {
        const img = new Image();
        img.src = require(`../photos/Infinitesphotos/photo${i}.jpg`);
        imgs1.value.push(img);
    }
    for (let i = 7; i <= 12; i++) {
        const img = new Image();
        img.src = require(`../photos/Infinitesphotos/photo${i}.jpg`);
        imgs2.value.push(img);
    }
    for (let i = 13; i <= 18; i++) {
        const img = new Image();
        img.src = require(`../photos/Infinitesphotos/photo${i}.jpg`);
        imgs3.value.push(img);
    }
};

onMounted(() => {
    if (wokk.value) {
        init();
        wokk.value = false;
    }
    nextTick(() => {
        resize();
        window.addEventListener('resize', resize);
    });
});
onBeforeUnmount(() => {
    window.removeEventListener('resize', resize);
});
onActivated(() => {
    //缓存执行
    console.log("组件激活");
});
</script>

<style>
.infinitebox {
    padding: 0;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    /* 宽高比为 16:9 */
    background-color: #171717;
    overflow: hidden;
}

.photos {
    position: absolute;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    cursor: pointer;
}

.photos_line {
    display: flex;
    font-size: 1.5px;
    height: 342em;
    margin-bottom: 48em;
    flex-shrink: 0;
}

.photos_line_photo {
    position: relative;
    font-size: 1.5px;
    width: 234em;
    height: 100%;
    margin-right: 36em;
    border-radius: 15em;
    background-color: #17f700;
    overflow: hidden;
    flex-shrink: 0;
}

.photos_line_photo img {
    object-fit: cover;
    /* 类似于 background-size: cover */
    object-position: center top;
    /* 类似于 background-position: center top */
    height: 100%;
    width: 100%;
    transition: 0.3s ease;
    pointer-events: none;
    user-select: none;
}

.photos_line_photo:hover img {
    transform: scale(1.2);
}
.photos_line p{
    position: absolute;
    bottom: 0px; /* 让文字显示在图片底部 */
    left: 10px; /* 调整文字距离左侧的位置 */
    height: auto; /* 允许文字调整高度 */
    width: auto; /* 允许文字调整宽度 */
    color: white;
    font-size: 19px; /* 设置合适的字体大小 */
    z-index: 10; /* 确保文字在图片之上 */
    background-color: rgba(0, 0, 0, 0.5); /* 给文字添加背景，确保可读性 */
    padding: 5px;
    border-radius: 5px;
    font-family: 'Anton', Impact;
}
@media screen and (min-aspect-ratio: 1.2/1) {

    .photos_line,
    .photos_line_photo {
        font-size: 1.7px;
    }
}

@media screen and (min-aspect-ratio: 1.9/1) {

    .photos_line,
    .photos_line_photo {
        font-size: 1.3px;
    }

    .infinitebox {
        aspect-ratio: 16 / 8;
    }
}

@media screen and (min-aspect-ratio: 1.5/1) {

    .photos_line,
    .photos_line_photo {
        font-size: 1.5px;
    }
}

@media screen and (max-aspect-ratio: 0.8/1) {

    .photos_line,
    .photos_line_photo {
        font-size: 1.3px;
    }

    .infinitebox {
        aspect-ratio: 9 / 13;
    }
}
</style>