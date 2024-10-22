<template>
    <el-popover
      placement="bottom"
      :width="50"
      trigger="click"
      v-model:visible="popoverVisible"
    >
      <template #reference>
        <img 
          src="../assets/404team.png" 
          alt="team logo" 
          class="cursor-pointer w-10 h-10 rounded-full"
        />
      </template>
      <div class="py-2 text-center">
        <el-button type="text" @click="logout" class="w-full justify-center">
          退出登录
        </el-button>
      </div>
    </el-popover>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  
  const popoverVisible = ref(false)
  const router = useRouter()
  
  const logout = () => {
    // 这里应该添加实际的退出登录逻辑，比如清除token等
    console.log('Logging out...')
    
    // 关闭popover
    popoverVisible.value = false
  
    // 跳转到登录页面
    router.push('/login')
  }
  </script>
  
  <style scoped>
  .el-button.is-text {
    padding: 8px 16px;
  }
  
  :deep(.el-button.is-text) {
    justify-content: center;
  }
  </style>