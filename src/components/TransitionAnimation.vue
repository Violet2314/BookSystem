<template>
    <div class="loading" ref="loadingContainer">
        <div class="logo-container" ref="logoContainer">
            <img src="../assets/404team.png" alt="Logo" class="logo" />
        </div>
        <div class="brush-mask" ref="brushMask"></div>
    </div>
</template>

<script setup>
import { ref, defineExpose,defineProps} from 'vue';
const props = defineProps({
    checkLoading: {
        type: Function,
        required: true
    }
});
const loadingContainer = ref(null);
const logoContainer = ref(null);
const brushMask = ref(null);

const inLoading = (next) => {
    loadingContainer.value.style.display = "block";
    logoContainer.value.style.opacity = "0";
    brushMask.value.style.left = "100%";

    setTimeout(() => {
        brushMask.value.style.left = "-30%";
    }, 50);

    setTimeout(() => {
        logoContainer.value.style.opacity = "1";
    }, 300);

    setTimeout(() => {
        next();
        props.checkLoading();
    }, 1000);
};

const outLoading = () => {
    brushMask.value.style.left = "100%";
    setTimeout(() => {
        logoContainer.value.style.opacity = "0";
    }, 20);
    setTimeout(() => {
        loadingContainer.value.style.display = "none";
    }, 1000);
};

defineExpose({
    inLoading,
    outLoading,
});
</script>

<style scoped>
.loading {
    overflow: hidden;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 1000;
    display: none;
}

.logo-container {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1001;
    opacity: 0;
    transition: opacity 0.5s ease;
}

.logo {
    max-width: 200px;
    height: auto;
}

.brush-mask {
    position: absolute;
    top: 0;
    left: 100%;
    width: 140%;
    height: 100%;
    background-color: #469ce5;
    transition: left 0.7s ease-in-out;
    clip-path: polygon(20% 0, 100% 0, 100% 100%, 0% 100%);
}
</style>