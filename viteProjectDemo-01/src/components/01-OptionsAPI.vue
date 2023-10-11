<template>
<h1>01-选项试API</h1>
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
  旧值：{{oldMsg}} <br> <br>
  <button @click="changePerson">改变名字</button>  <br>
  有人改了名字,旧名字为{{ oldperson.namePerson }},新名字为{{ person.namePerson }}
</div>
</template>

<script lang="ts">
type Book = {
  bookName:string,
  price:number
}
type Person = {
  namePerson:string,
  age:number
}
export default {
  // data() 返回的属性将会成为响应式的状态
  // 并且暴露在 `this` 上
  data() {
    // data中的都是响应式数据
    return{
      count:0,
      person:{
        namePerson:'jack',
        age:18
      },
      books:[
        {bookName:'vue3',price:20},
        {bookName:'myBatis',price:25},
        {bookName:'SpringBoot',price:30},
        {bookName:'MySql',price:35},
        {bookName:'FastAPI',price:40}
      ],
      sellingBooks:[] as Book[],  // 类型注解
      dataMsg:'' as string,
      oldMsg:'' as string,
      newPerson:{
        namePerson:'雪碧',
        age:18
      },
      oldperson: {
        namePerson:'雪碧',
        age:18
      },
    }
  },

  // methods 是一些用来更改状态与触发更新的函数
  // 它们可以在模板中作为事件处理器绑定
  methods: {
    increaseCount(){
      this.count++
    },
    changeName(){
      this.person.namePerson = 'mark'
    },
    increaseAge(){
      this.person.age++
    },
    reduceAge(){
      this.person.age--
    },
    reduceBooks(){
      this.books.pop()
    },
    changePerson(){
      this.person = this.newPerson
    }
  },
  // 计算属性
  computed:{
    isAdult(){
      if (this.person.age >= 18) {
        return '成年'
      }else{
        return '未成年'
      }
    },
    bookInventory(){
      return this.books.length
    }
  },
  // 侦听器 显示还有什么书，刚才卖出去了什么书
  watch: {
    books: {
      handler(newValue: Book[], oldValue: Book[]) {
        if(newValue == oldValue){
          this.sellingBooks = newValue
        }
        console.log('newValue:', newValue);
        console.log('oldValue:', oldValue);
        // this.sellingBooks = oldValue.filter(oldBook => !newValue.includes(oldBook));
        // if (newValue && oldValue) {
        // this.sellingBooks = oldValue.filter(oldBook => {
        //   return !newValue.some(newBook =>
        //     newBook.bookName === oldBook.bookName && newBook.price === oldBook.price
        //   );
        // });
        // }
        // console.log('newValue:', newValue);
        // console.log('oldValue:', oldValue);
      },
      deep: true,
      immediate: true // 立即触发侦听器
    },
    dataMsg:{
      handler(newValue,oldValue){
        this.oldMsg = oldValue
        console.log(newValue)
        }
    },
    person:{
      handler(newValue:Person,oldValue:Person){
        this.oldperson = oldValue
        console.log(newValue)
        },
        deep:true
    }
  }
}
</script>