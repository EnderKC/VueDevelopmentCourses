<template>
    <h1>02-组合式API</h1>
    <div>
        <p style="color: brown;">响应式数据简单数据类型</p>
        <button @click="increaseCount">Counut:{{ count }}</button>
    </div>
    <hr>
    <div>
        <p style="color: brown;">响应式数据对象 计算属性</p>
        <p>name:{{ person.namePerson }}</p>
        <p>age:{{ person.age }}</p>
        <p>是否成年：{{ isAdult }}</p>
        <button @click="changeName">改变名字</button>
        <button @click="increaseAge">增长年龄</button>
        <button @click="reduceAge">减少年龄</button>
    </div>
    <hr>
    <div>
        <p style="color: brown;">响应式数据数组 侦听器</p>
        <p v-for="item in books">书名：{{ item.bookName }}价格：{{ item.price }}</p>
        <p>库存{{ bookInventory }}本书</p>
        <button @click="reduceBooks">减少书籍</button>
        <p>库存的书（侦听器数组）</p>
        <p v-for="item in sellingBooks">书名：{{ item.bookName }}价格：{{ item.price }}</p>
    </div>
    <hr>
    <div>
        <p style="color: brown;">响应式数据基本数据 对象</p>
        输入的值：<input v-model="dataMsg" /><br>
        旧值：{{ oldMsg }} <br> <br>
        <button @click="changePerson">改变名字</button> <br>
        有人改了名字,旧名字为{{ oldperson.namePerson }},新名字为{{ person.namePerson }}
    </div>
</template>
    
<script lang="ts">
// 导入相关函数
import { ref, reactive, computed, watch } from 'vue'
type Book = {
    bookName: string,
    price: number
}
type Person = {
    namePerson: string,
    age: number
}

export default {
    setup() {
        // 定义变量
        let count = ref(0)
        let person = reactive({
            namePerson: 'jack',
            age: 18
        })
        let books = reactive([
            { bookName: 'vue3', price: 20 },
            { bookName: 'myBatis', price: 25 },
            { bookName: 'SpringBoot', price: 30 },
            { bookName: 'MySql', price: 35 },
            { bookName: 'FastAPI', price: 40 }
        ])
        let sellingBooks: Book[] = reactive([])
        let dataMsg = ref('')
        let oldMsg = ref('')
        let newPerson: Person = reactive({
            namePerson: '雪碧',
            age: 18
        })
        let oldperson: Person = reactive({
            namePerson: '雪碧',
            age: 18
        })
        // 定义方法
        function increaseCount() {
            count.value++
        }
        function changeName() {
            person.namePerson = 'mark'
        }
        function increaseAge() {
            person.age++
        }
        function reduceAge() {
            person.age--
        }
        function reduceBooks() {
            books.pop()
        }
        function changePerson() {
            person.namePerson = newPerson.namePerson
        }
        // 计算属性
        const isAdult = computed(() => {
            if (person.age >= 18) {
                return '成年'
            } else {
                return '未成年'
            }
        })
        const bookInventory = computed(() => {
            return books.length
        })
        // 侦听器
        watch(books, (newValue: Book[], oldValue: Book[]) => {
            if (newValue == oldValue) {
                sellingBooks.length = 0; // 清空sellingBooks
                newValue.forEach((book) => {
                    sellingBooks.push({ ...book }); // 展开操作符,将books的元素添加到sellingBooks
                });
            }
            console.log("sellingBooks", sellingBooks)
            console.log('newValue:', newValue);
            console.log('oldValue:', oldValue);
        }
        );
        watch(dataMsg, (newValue, oldValue) => {
            oldMsg.value = oldValue
            console.log(newValue)
        });
        watch(
            () => ({ ...person }), // 使用对象的深度克隆来观察 person 对象的变化
            (newValue, oldValue) => {
                // oldValue 包含 person 的旧状态，而不是同一引用的对象
                oldperson = oldValue;
                console.log(newValue);
            }
        );
        return {
            // 手动暴露出来
            count,
            person,
            books,
            sellingBooks,
            dataMsg,
            oldMsg,
            newPerson,
            oldperson,
            // 暴露方法
            increaseCount,
            changeName,
            increaseAge,
            reduceAge,
            reduceBooks,
            changePerson,
            // 计算属性
            isAdult,
            bookInventory
        }
    }
}
</script>