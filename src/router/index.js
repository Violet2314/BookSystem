import { createRouter, createWebHashHistory } from 'vue-router'
import NoticeBoard from '../views/NoticeBoard.vue'
import RecommendView from '@/views/RecommendView.vue'
import SelectBook from '@/views/SelectBook.vue'
import ReturnBook from '@/views/ReturnBook.vue'
import AddBook from '@/views/AddBook.vue'
import FunctionView from '@/FunctionView.vue'
import LoginView from '@/LoginView.vue'
import LoginItem from '@/components/LoginItem.vue'

const routes = [
    {
        path: '/',
        component: FunctionView,
        children: [
            {
                path: '',
                name: 'noticeboard',
                component: NoticeBoard
            },
            {
                path: 'recommend',
                name: 'recommend',
                component: RecommendView,
            },
            {
                path: 'selectbook',
                name: 'SelectBook',
                component: SelectBook,
            },
            {
                path: 'returnbook',
                name: 'ReturnBook',
                component: ReturnBook,
            },
            {
                path: 'addbook',
                name: 'AddBook',
                component: AddBook,
            }
        ]
    },
    {
        path: '/login',
        component: LoginView,
        children: [
            {
                path: '',
                name: 'LoginItem',
                component: LoginItem,
            }
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
