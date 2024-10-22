<template>
    <div class="FaultFont-box">
        <div class="FaultFont-container" @click="triggerFault">
            <p class="faulttext" v-for="(text, index) in texts" :key="index" :data-content="text"
                :class="{ faulttext_fault: faultActive }" :style="{
                    transform: transforms[index] || '',
                    clipPath: clipPaths[index] || '',
                }">
                {{ text }}
            </p>
        </div>
    </div>
</template>

<script setup>
import { defineProps, onMounted, ref, defineExpose} from 'vue';

const texts = ref([]);
const faultActive = ref(false);
const transforms = ref([]);
const clipPaths = ref([]);
const haveclick = ref(false);
let player = null;

const props = defineProps({
    content: {
        type: String,
        Required: true,
    }
});

const init = () => {
    texts.value = [props.content, props.content, props.content, props.content];
};

const fault = () => {
    haveclick.value = true;
    setTimeout(() => {
        clearInterval(player);
        faultActive.value = false;
        clipPaths.value = [];
        transforms.value = [];
        haveclick.value = false;
    }, 1000);

    player = setInterval(() => {
        faultActive.value = true;
        texts.value.forEach((_, index) => {
            transforms.value[index] = `translate(${Math.random() * 60 - 30}%, ${Math.random() * 60 - 30}%)`

            const x = Math.random() * 100;
            const y = Math.random() * 100;
            const h = Math.random() * 50 + 50;
            const w = Math.random() * 40 + 10;

            clipPaths.value[index] = `polygon(${x}% ${y}%, ${x + w}% ${y}%, ${x + w}% ${y + h}%, ${x}% ${y + h}%)`
        })
    }, 30);
};

const triggerFault = () => {
    if(!haveclick.value){
        fault();
    }
}

onMounted(() => {
    init();
});

defineExpose({ fault });//暴露方法
</script>

<style>
.FaultFont-container {
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    height: 20vh;
}

.faulttext {
    position: absolute;
    font-family: 'Anton', Impact;
    letter-spacing: 0.5rem;
    user-select: none;
    font-size: 3rem;
}

.faulttext_fault::after,
.faulttext_fault::before {
    content: attr(data-content);
    position: absolute;
    left: 0;
    top: 0;
    mix-blend-mode: screen;
}

.faulttext_fault::after {
    color: #ff0000;
    transform: translateX(3%);
}

.faulttext_fault::before {
    color: #0000ff;
    transform: translateX(-3%);
}
</style>