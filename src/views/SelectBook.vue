<template>
    <div class="Selectbox">
        <div class="Selectboxheader">
            <div class="childbox">
                <div class="headerchild">
                    <p>书名</p>
                    <el-input v-model="bookname" style="min-width: 140px" placeholder="Please input bookname"
                        class="wok" />
                </div>
                <div class="headerchild">
                    <p>作者</p>
                    <el-input v-model="writer" style="min-width: 140px" placeholder="Please input writer" class="wok" />
                </div>
            </div>
            <div class="headerchild">
                <p>出版日期</p>
                <el-date-picker v-model="value1" type="daterange" range-separator="至" start-placeholder="Start date"
                    end-placeholder="End date" :size="size" class="wok"
                    style="min-width: 240px;max-width: 300px; margin-left: 0;" />
            </div>
            <el-button type="primary" size="small" @click="handleView()" class="select">
                查询
            </el-button>
        </div>
        <div class="Selectboxmain">
            <div class="table-container">
                <!-- 动态计算的宽度绑定到 el-table -->
                <el-table :data="tableData" :style="{ width: tableWidth }" border>
                    <!-- 书名列 -->
                    <el-table-column prop="bookName" label="书名" min-width="150" />

                    <!-- 作者列 -->
                    <el-table-column prop="writer" label="作者" min-width="120" />

                    <!-- 出版日期列 -->
                    <el-table-column prop="publishData" label="出版日期" min-width="150" />

                    <!-- 余量列 -->
                    <el-table-column prop="stock" label="余量" min-width="80" />

                    <!-- 操作列 -->
                    <el-table-column label="操作" min-width="150">
                        <template #default="scope">
                            <el-button type="primary" size="small" @click="handleBorrow(scope.row)">
                                借书
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="dividepage">
                <el-pagination v-model:current-page="currentPage4" v-model:page-size="pageSize4"
                    :page-sizes="[5, 10, 20, 25]" :size="size" :disabled="disabled" :background="background"
                    layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
                    @current-change="handleCurrentChange" />
            </div>
        </div>
    </div>
</template>

<script setup>
import { borrowBook, fetchBooks } from '@/service/selectbookservice';
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { ElMessage } from 'element-plus';

// 替换为 JavaScript，不需要类型注释
const bookname = ref("");
const writer = ref("");
const value1 = ref("");
const currentPage4 = ref(1);
const pageSize4 = ref(5)
const size = ref('default'); // 不需要类型
const background = ref(false);
const disabled = ref(false);
const tableData = ref([]);
const total = ref(100);

const handleSizeChange = (val) => {
    console.log(`${val} items per page`);
    fetchData(); // 每次分页更新时重新获取数据
};

const handleCurrentChange = (val) => {
    console.log(`current page: ${val}`);
    fetchData(); // 每次分页更新时重新获取数据
};

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
    fetchData();
});

onBeforeUnmount(() => {
    window.removeEventListener('resize', updateTableWidth); // 组件卸载时移除监听
});

const handleView = () => {
    console.log('查询:');
    fetchData();
};

const handleBorrow = async (row) => {
    console.log('借书:', row);
    await borrowData(row);
    await fetchData();
};

const fetchData = async () => {
    const params = {
        bookName: bookname.value,
        writer: writer.value,
        startDate: value1.value ? value1.value[0] : null,
        endDate: value1.value ? value1.value[1] : null,
        page: currentPage4.value,
        size: pageSize4.value,
    }

    try {
        const result = await fetchBooks(params);
        tableData.value = result.data.rows;
        total.value = result.data.total;
    } catch (error) {
        console.log('fetchData error',error);
    }
}

const borrowData = async(row) => {
    const params = {
        bookId: row.bookId,
        userId: sessionStorage.getItem('userId'),
    }
    console.log(params);
    if(row.stock == 0){
        ElMessage.error('没有余量了');
        return;
    }
    try{
        const response = await borrowBook(params);
        console.log(response.data); // 成功时的反馈
    }catch(error){
        console.error('borrowData error', error);
    }
}
</script>

<style>
.childbox {
    display: flex;
}

.select {
    width: 100px;
    margin-left: 2em;
}

.Selectbox {
    display: flex;
    flex-direction: column;
    padding-left: 2em;
}

.Selectboxheader {
    display: flex;
    flex-direction: column;
    margin-bottom: 3em;
    margin-top: 3em;
}

.headerchild {
    display: flex;
    margin-right: 2em;
    margin-left: 1em;
    gap: 9px;
}

.Selectboxmain {
    max-width: 1400px;
}

.wok {
    margin: auto;
    height: 30px;
}

.headerchild p {
    white-space: nowrap;
}

.dividepage {
    margin-top: 2em;
    margin-left: 1em;
}
</style>