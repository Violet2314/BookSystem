const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
    transpileDependencies: true,
    //处理异域的关键
    //配置文件改了要重启服务才会生效的，不支持热部署的
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8080', // 代理的后端地址
                changeOrigin: true,
                pathRewrite: { '^/api': '' }, // 重写路径
            },
        },
    },
});
