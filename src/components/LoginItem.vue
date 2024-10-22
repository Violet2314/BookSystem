<template>
    <div class="login-container">
        <div class="login-box">
            <img src="../assets/404team.png" alt="Apple Logo" class="apple-logo" />
            <h2 class="login-title">Sign in with your ID</h2>
            <el-form :model="loginForm" status-icon :rules="rules" label-position="top">
                <el-form-item label="404 ID" prop="email">
                    <el-input v-model="loginForm.email" placeholder="example@icloud.com" />
                </el-form-item>
                <el-form-item label="Password" prop="password">
                    <el-input type="password" v-model="loginForm.password" placeholder="Enter your password" />
                </el-form-item>
                <el-button type="primary" @click="handleLogin" class="login-button">Sign In</el-button>
                <div class="login-footer">
                    <router-link to="/">Forgot ID or password?</router-link>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { loginin } from '@/service/userlogin';

const router = useRouter();  // 引入 Vue Router

const isloginsuccess = ref(false);

const loginForm = ref({
    email: '',
    password: ''
});

const rules = ref({
    email: [
        { required: true, message: 'Please input your ID', trigger: 'blur' },
        { type: 'email', message: 'Please input a valid email address', trigger: ['blur', 'change'] }
    ],
    password: [{ required: true, message: 'Please input your password', trigger: 'blur' }]
});

const handleLogin = async () => {
    if (loginForm.value.email && loginForm.value.password) {
        await checklogin();  // 等待 checklogin 完成
        if (isloginsuccess.value) {
            ElMessage.success('Login successful');
            router.push('/');
        } else {
            ElMessage.error('The id or password is incorrect');
        }
    } else {
        ElMessage.error('Please fill out the form correctly');
    }
};

const checklogin = async () => {
    const params = {
        email: loginForm.value.email,
        password: loginForm.value.password
    };
    try {
        const result = await loginin(params);  // 获取完整的响应对象
        if (result.code === 1) {
            const { userId } = result.data;  // 解构 userId 从 result.data
            isloginsuccess.value = true;
            console.log('checklogin success');
            sessionStorage.setItem('userId', userId);
            // 如果有 token 返回，也要处理
            sessionStorage.setItem('token', result.data.token || '');
        } else {
            console.error("Login failed:", result.msg);
            isloginsuccess.value = false;
        }
    } catch (error) {
        console.log('checklogin error', error);
        isloginsuccess.value = false;
    }
};

</script>

<style scoped>
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    width: 100vw;
    z-index: 0;
    background: url('../image/loginbackground3.jpg') center center/cover;
}

.login-container::after {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 1;
    background: rgba(0, 0, 0, 0.376);
    box-shadow: inset 120px 100px 250px rgba(255, 255, 255, 0), inset -120px -100px 250px #00000050;
    transition: box-shadow 0.5s ease;
}

.login-box {
    z-index: 2;
    background-color: white;
    border-radius: 16px;
    padding: 40px;
    box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
    text-align: center;
    width: 320px;
}

.apple-logo {
    width: 60px;
    margin-bottom: 20px;
}

.login-title {
    font-weight: 500;
    color: #333;
    font-size: 22px;
    margin-bottom: 24px;
}

.el-form-item__label {
    color: #6e6e73;
}

.el-input {
    border-radius: 8px;
    height: 40px;
}

.login-button {
    width: 100%;
    height: 44px;
    border-radius: 8px;
    font-size: 16px;
    background-color: #007aff;
    border-color: #007aff;
    margin-top: 20px;
}

.login-footer {
    margin-top: 16px;
}

.login-footer a {
    color: #007aff;
    font-size: 14px;
    text-decoration: none;
}
</style>