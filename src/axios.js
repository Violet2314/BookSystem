import axios from 'axios';
axios.defaults.baseURL = process.env.VUE_APP_API

// 从 sessionStorage 获取 token
const getToken = () => {
  return sessionStorage.getItem('token');
};

const instance = axios.create({
  baseURL: '/api', // 设置后端 API 的基础 URL
  withCredentials: true // 发送 Cookie 或其他凭据
});

// 请求拦截器，在每次请求发出之前，附加 token 到请求头
instance.interceptors.request.use(config => {
    const token = getToken(); // 从 sessionStorage 获取 token
    if (token) {
        config.headers['authorization'] = token; 
        config.headers['Content-Type'] = 'application/json'
    }
    return config; // 返回修改后的请求配置
}, error => {
    return Promise.reject(error);
});

instance.interceptors.response.use(
    response => {
        // 处理正常响应
        if (response.data.redirect) {
            window.location.href = response.data.redirect; // 执行重定向
        }
        return response;
    },
    error => {
        return Promise.reject(error);
    }
);

export default instance;
