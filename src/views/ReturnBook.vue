<template>
    <div class="returnbox">
        <FaultFont :content="'So many books, so little time.'" class="booksaid" />
        <div class="table-container">
            <!-- 动态计算的宽度绑定到 el-table -->
            <el-table :data="tableData" :style="{ width: tableWidth }" border class="table-container-table">
                <!-- 书名列 -->
                <el-table-column prop="bookName" label="书名" min-width="150" />

                <!-- 作者列 -->
                <el-table-column prop="writer" label="作者" min-width="120" />

                <!-- 出版日期列 -->
                <el-table-column prop="publishData" label="出版日期" min-width="150" />

                <!-- 操作列 -->
                <el-table-column label="操作" min-width="150">
                    <template #default="scope">
                        <el-button type="danger" size="small" @click="handleDelete(scope.row)">
                            归还
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script setup>
import FaultFont from '@/components/FaultFont.vue';
import { fetchBorrowedBooks, returnBook } from '@/service/selectbookservice';
import { ref, onMounted, onBeforeUnmount } from 'vue';
// 表格数据
const tableData = ref([]);

// 定义动态宽度
const tableWidth = ref('100%');

// 动态计算宽度的函数
const updateTableWidth = () => {
    const windowWidth = window.innerWidth;
    const newTableWidth = windowWidth > 300 ? `${windowWidth - 300}px` : '100%';
    tableWidth.value = newTableWidth;
};

// 监听窗口变化
onMounted(() => {
    updateTableWidth(); // 初次计算宽度
    window.addEventListener('resize', updateTableWidth); // 监听窗口调整
    fetchBorrowedData();
});

onBeforeUnmount(() => {
    window.removeEventListener('resize', updateTableWidth); // 组件卸载时移除监听
});

// 归还按钮点击事件
const handleDelete = async (row) => {
    console.log('归还:', row);
    await returnData(row);
    await fetchBorrowedData();
};

const fetchBorrowedData = async () => {
    const result = await fetchBorrowedBooks();
    tableData.value = result.data.rows;
}

const returnData = async (row) => {
    const params = {
        bookId: row.bookId,
        Id: sessionStorage.getItem('userId'),
    }

    try {
        const response = await returnBook(params);
        console.log(response.data); // 成功时的反馈
    } catch (error) {
        console.error('returnData error', error);
    }
}
</script>

<style>
.table-container-table {
    margin: 0 auto;
}
</style>
