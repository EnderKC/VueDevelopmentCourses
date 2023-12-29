<template>
    <contextHolder/>
    <a-form layout="inline" :model="sarchBook" @finish="handleFinish" style=" padding-left: 20px">
        <a-form-item>
            <a-input v-model:value="sarchBook.goodsName" placeholder="书籍名称">
                <template #prefix>
                    <UserOutlined style="color: rgba(0, 0, 0, 0.25)" />
                </template>
            </a-input>
        </a-form-item>
        <a-form-item>
            <a-input v-model:value="sarchBook.goodsDescription" placeholder="描述">
                <template #prefix>
                    <LockOutlined style="color: rgba(0, 0, 0, 0.25)" />
                </template>
            </a-input>
        </a-form-item>
        <a-form-item>
            <a-button type="primary" html-type="submit">
                搜索！
            </a-button>
        </a-form-item>
    </a-form>
    <div style=" padding: 20px">
        <a-row :gutter="16" wrap="true">
            <a-col v-for="book in bookList" class="book-list" :span="8">
                <a-card hoverable :title=book.goodsName>
                    <p>描述: {{ book.goodsDescription }}</p>
                    <p>库存: {{ book.goodsStock }}</p>
                    <a-button @click="showModal(book)">下单</a-button>
                </a-card>
            </a-col>
        </a-row>
    </div>

    <!-- 下单提示框 -->
    <a-modal v-model:open="open" title="下单提醒" @ok="handleOk">
        <p>您真的要下单吗？</p>
    </a-modal>
</template>

<script lang="ts" setup>
import { Book, BookSearch } from '@/api/model/bookModel'
import { ref,reactive } from 'vue'
import BookAPI from '@/api/bookApi'
import OrderAPI from '@/api/orderApi'
import {Order} from '@/api/model/orderModel'
import { message } from 'ant-design-vue';
const [messageApi, contextHolder] = message.useMessage();

const orderApi = new OrderAPI()

const sarchBook = ref<BookSearch>({
    goodsName: '',
    goodsDescription: ''
})

const bookApi = new BookAPI()
const bookList = ref<Book[]>([])
const getBookList = async () => {
    bookApi.listBooks(sarchBook.value).then(res => {
        bookList.value = res.data.data.content
        console.log(bookList.value)
    }).catch(err => {
        console.log(err)
    })
}

// 下单商品

const open = ref<boolean>(false);

const orderBook = reactive<Order>({
    goodsID: '0'
})

const showModal = (book:Book) => {
    console.log(book)
    open.value = true;
    orderBook.goodsID = book.id
};

const handleOk = (e: MouseEvent) => {
    console.log(e);
    open.value = false;
    orderApi.addOrder({
        goodsID: orderBook.goodsID,
    }).then(res => {
        messageApi.success('下单成功')
        console.log(res)
    }).catch(err => {
        messageApi.error('下单失败')
        console.log(err)
    })
    
};

const handleFinish = () => {
    getBookList()
}
getBookList()

</script>
<style scoped>
.book-list {
    margin-top: 20px;
}
</style>