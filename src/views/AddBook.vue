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
            <div class="button">
                <el-button type="primary" size="small" @click="handleView()" class="select">
                    查询
                </el-button>
                <el-button type="primary" size="small" @click="openAddBookDialog()" class="select">
                    +增加书本
                </el-button>
            </div>
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
                            <el-button type="primary" size="small" @click="handleChange(scope.row)">
                                更改数量
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="dividepage">
                <el-pagination v-model:current-page="currentPage4" v-model:page-size="pageSize4"
                    :page-sizes="[100, 200, 300, 400]" :size="size" :disabled="disabled" :background="background"
                    layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
                    @current-change="handleCurrentChange" />
            </div>
        </div>
    </div>
    <el-dialog v-model="dialogVisible" title="增加书本" width="30%" :before-close="handleClose">
        <el-form :model="newBook" label-width="120px">
            <el-form-item label="书名">
                <el-input v-model="newBook.bookName" />
            </el-form-item>
            <el-form-item label="作者">
                <el-input v-model="newBook.writer" />
            </el-form-item>
            <el-form-item label="出版日期">
                <el-date-picker v-model="newBook.publishData" type="date" placeholder="选择日期" style="width: 100%;" />
            </el-form-item>
            <el-form-item label="数量">
                <el-input-number v-model="newBook.stock" :min="0" :max="1000" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submitNewBook">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>
    <el-dialog v-model="changeAmountVisible" title="改变数量" width="30%" :before-close="handleClose">
        <el-form label-width="120px">
            <el-form-item label="数量">
                <el-input-number v-model="num" :min="0" :max="1000" @change="handleChangeNum" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="changeAmountVisible = false">取消</el-button>
                <el-button type="primary" @click="submitBookAmount">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { addBook, changeBookAmount, fetchBooks } from '@/service/selectbookservice';
import { ElMessage } from 'element-plus';
import { ref, onMounted, onBeforeUnmount } from 'vue';

// 替换为 JavaScript，不需要类型注释
const bookname = ref("");
const writer = ref("");
const value1 = ref("");
const currentPage4 = ref(1);
const pageSize4 = ref(5);
const size = ref('default'); // 不需要类型
const background = ref(false);
const disabled = ref(false);
const dialogVisible = ref(false);
const changeAmountVisible = ref(false);
const total = ref(100);
const num = ref(1);
const nowrow = ref();
const newBook = ref({
    bookName: '',
    writer: '',
    publishData: '',
    stock: 0
});

const handleSizeChange = (val) => {
    console.log(`${val} items per page`);
    fetchData();
};

const handleCurrentChange = (val) => {
    console.log(`current page: ${val}`);
    fetchData();
};

// 表格数据
const tableData = ref([]);

// 定义动态宽度
const tableWidth = ref('100%');

// 动态计算宽度的函数
const updateTableWidth = () => {
    const windowWidth = window.innerWidth;
    const newTableWidth = windowWidth > 240 ? `${windowWidth - 240}px` : '100%';
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

// 查看按钮点击事件
const handleView = () => {
    console.log('查看:');
    fetchData();
};

const handleChange = (row) =>{
    console.log('改变数量',row);
    nowrow.value = row;
    num.value = row.stock;
    changeAmountVisible.value = true;
}

const handleChangeNum = () => {
    console.log('改变数量');
}
// Function to open the dialog
const openAddBookDialog = () => {
    dialogVisible.value = true;
};

// Function to handle dialog close
const handleClose = (done) => {
    // You can add confirmation here if needed
    done();
};

const submitBookAmount = async() => {
    await changeData();
    await fetchData();
    changeAmountVisible.value = false;
}

// Function to handle form submission
const submitNewBook = async () => {
    if(newBook.value.bookName == '' || newBook.value.writer == '' || newBook.value.publishData == ''){
        ElMessage.error("输入有空");
        return;
    }
    // Here you would typically send the data to your backend
    console.log('New book to be added:', newBook.value);
    await addData();

    // Reset the form and close the dialog
    newBook.value = {
        bookName: '',
        writer: '',
        publishData: '',
        stock: 0
    };
    dialogVisible.value = false;
    ElMessage.success("成功插入");
    await fetchData();
};

const changeData = async() => {
    const params = {
        bookId: nowrow.value.bookId,
        stock: num.value,
    };
    try {
        const response = await changeBookAmount(params);
        console.log(response.data);
    } catch (error) {
        console.log('changeData is error',error);
    }
}

const addData = async() => {
    const params = newBook.value;
    try {
        const response = await addBook(params);
        console.log(response.data);
    } catch (error) {
        console.log("addData is error",error);
    }
}

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
        total.value = result.data.total;
        tableData.value = result.data.rows;
    } catch (error) {
        console.log('fetchData error',error);
    }
}
</script>

<style></style>