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
  <p>{{ sellingBooks }}</p>
</div>
</template>

<script lang="ts">
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
      sellingBooks:[]
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
    // watchBooks(newBooks:Array<{
    //   name:string,
    //   price:number
    // }>, oldBooks:Array<{
    //   name:string,
    //   price:number
    // }>){
    //   this.bookMsg =  {
    //     books:newBooks,
    //     oldBooks:oldBooks
    //   }
    // }
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
  watch:{
    books(newBooks, oldBooks){
      this.sellingBooks = oldBooks
      // this.watchBooks(newBooks,oldBooks)
      this.sellingBooks.map((item,index)=>{
          if(item in newBooks){
            this.sellingBooks.splice(index,1)
          }
      })
    }
  }
}
</script>