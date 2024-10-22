import axios from "../axios"

// await 的本质是等待一个 Promise 对象的结果，而 async 函数包裹后，
// 函数内部的异步代码就会自动返回 Promise。如果没有 async，在函数外部使用 await，

export const fetchBooks = async (params) => {
    try {
        const response = await axios.get('/getbook', { params });//把params当参数传出去
        return response.data;
    } catch (error) {
        console.error('getbook is error', error);//输出error的具体错误
    }
};

export const borrowBook = async (params) => {//async 用于定义一个异步函数。这个函数会返回一个 Promise 对象
    try {
        const response = await axios.put('/book/borrow', null, { params })//await 用于等待一个 Promise 对象的解决
        return response.data;
    } catch (error) {
        console.error('book/borrow is error', error);
    }
}

export const fetchBorrowedBooks = async () => {
    try {
        const response = await axios.get('/borrowedbooks', {
            params: {
                userId: sessionStorage.getItem('userId'),  // 假设 userId 已经存储在 localStorage 中
            }
        });
        return response.data;
    } catch (error) {   
        console.log('borrowedBooks is error',error);
    }
}

export const returnBook = async (params) => {
    try {
        const response = await axios.put('/book/return' ,null ,{ params });
        return response.data;
    } catch (error) {
        console.log('returnBook is error',error);
    }
}

export const addBook = async (params) => {
    try {
        const response = await axios.post('/book/add',params,{
            headers: {
                'Content-Type': 'application/json', // 添加 Content-Type 头
            },
        });
        return response.data;
    } catch (error) {
        console.log("addBook is error",error);
    }
}

export const changeBookAmount = async(params) => {
    try {
        const response = await axios.put('/book/change',null,{params});
        return response.data;
    } catch (error) {
        console.log('changeBookAmount is error',error);
    }
}
