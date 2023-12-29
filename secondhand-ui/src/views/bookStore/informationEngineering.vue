<template>
    <a-form layout="inline" :model="sarchBook" @finish="handleFinish" style=" padding-left: 20px">
        <a-form-item>
            <a-input v-model:value="sarchBook.goodsName" placeholder="书籍名称">
                <template #prefix>
                    <UserOutlined style="color: rgba(0, 0, 0, 0.25)" />
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
                </a-card>
            </a-col>
        </a-row>
    </div>
</template>

<script lang="ts" setup>
import {Book,BookSearch} from '@/api/model/bookModel.ts'
import { ref } from 'vue'
import BookAPI from '@/api/bookApi'

const sarchBook = ref<BookSearch>({
    goodsName: '',
    goodsDescription: '信息工程学院',

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