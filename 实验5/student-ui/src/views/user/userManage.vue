<template>
  <div class="bodyStyle">
    <!-------搜索框开始--------->
    <a-form
      ref="searchFormRef"
      name="advanced_search"
      class="ant-advanced-search-form formStyle"
      :model="searchFormState"
      @finish="onFinish">
      <a-row :gutter="24">
        <a-col :span="8">
          <a-form-item :name="`username`" :label="`用户名`">
            <a-input
              v-model:value="searchFormState[`username`]"
              placeholder="请输入用户名"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item :name="`role`" :label="`角色`">
            <a-input v-model:value="searchFormState[`role`]" placeholder="请输入角色"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item :name="`stuid`" :label="`学号`">
            <a-input v-model:value="searchFormState[`stuid`]" placeholder="请输入学号"></a-input>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12" style="text-align: left">
          <a-button
            type="primary"
            :icon="h(PlusOutlined)"
            v-if="userRole === 'admin'"
            @click="newUser">
            新增
          </a-button>
        </a-col>
        <a-col :span="12" style="text-align: right">
          <a-button type="primary" html-type="submit">查询</a-button>
          <a-button style="margin: 0 8px" @click="resetForm">
            重置
          </a-button>
        </a-col>
      </a-row>
    </a-form>
    <!-------搜索框结束--------->

    <!--消息提示的占位符-->
    <context-holder />

    <!-------表格开始--------->
    <a-table
      :columns="columns"
      :data-source="data.list"
      :pagination="pagination"
      :scroll="{ x: 650, y: 300 }">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'operation'">
          <a @click="update(record)">更新</a>
          <a-divider type="vertical" />
          <a @click="remove(record)">删除</a>
        </template>
      </template>
    </a-table>
    <!-------表格结束--------->

    <!-------新增/更新对话框开始--------->
    <a-modal
      v-model:open="modalVisible"
      :title="dialogTitle"
      ok-text="确认"
      cancel-text="取消"
      width="500px"
      centered
      @ok="clickOK">
      <a-form
        ref="dialogFormRef"
        :model="dialogFormState"
        :label-col="labelCol"
        :wrapper-col="wrapperCol">
        <a-form-item
          has-feedback
          ref="username"
          label="用户名"
          name="username"
          :rules="[
            { required: true, message: '请输入用户名!' },
            { min: 3, max: 30, message: '长度在 3 to 30', trigger: 'blur' }
          ]"
          class="error-align">
          <a-input
            v-model:value="dialogFormState.username"
            v-if="dialogTitle !== '新增用户'"
            disabled />
          <a-input v-model:value="dialogFormState.username" v-else />
        </a-form-item>
        <a-form-item
          has-feedback
          label="密码"
          name="password"
          :rules="[
            { required: true, message: '请输入密码!' },
            { min: 3, max: 30, message: '长度在 3 to 30', trigger: 'blur' }
          ]"
          class="error-align">
          <a-input-password v-model:value="dialogFormState.password"></a-input-password>
        </a-form-item>
        <a-form-item
          v-if="userRole === 'admin'"
          ref="role"
          label="角色"
          name="role"
          class="error-align">
          <a-radio-group v-model:value="dialogFormState.role">
            <a-radio value="admin">管理员</a-radio>
            <a-radio value="student">学生</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item has-feedback ref="stuid" label="学号" name="stuid" class="error-align">
          <a-input
            v-model:value="dialogFormState.stuid"
            v-if="dialogTitle !== '新增用户'"
            disabled />
          <a-input v-model:value="dialogFormState.stuid" v-else />
        </a-form-item>
      </a-form>
    </a-modal>
    <!-------新增/更新对话框结束--------->
  </div>
</template>

<script lang="ts" setup>
import type { TableColumnsType } from 'ant-design-vue'
import type { FormInstance } from 'ant-design-vue'
import { useUserStore } from '@/store/modules/user'
import UserApi from '@/api/user'
import { message } from 'ant-design-vue'
import { ExclamationCircleOutlined, PlusOutlined } from '@ant-design/icons-vue'
import { createVNode, h } from 'vue'
import { Modal } from 'ant-design-vue'

// 使用useUserStore()获取userStore，在userStore中定义了role
const userStore = useUserStore()
// 获取用户角色
const userRole = userStore.userInfo?.role
// 生成消息实例，用于显示消息提示
const [messageApi, contextHolder] = message.useMessage()

// 创建userApi实例，用于访问后端接口
const userApi = new UserApi()

// FormInstance 是指表单实例对象，可以通过 FormInstance 对象控制表单的校验、提交等操作，从而简化表单的处理。
const searchFormRef = ref<FormInstance>()
// 定义表单数据
const searchFormState = reactive({
  username: '',
  role: '',
  stuid: ''
})

// 定义表格标题列
const columns: TableColumnsType = [
  { title: '用户名', dataIndex: 'username', key: 'username', width: 150 },
  { title: '密码', dataIndex: 'password', key: 'password', width: 150 },
  { title: '角色', dataIndex: 'role', key: 'role', width: 100 },
  { title: '学号', dataIndex: 'stuid', key: 'stuid', width: 150 },
  {
    title: '操作',
    key: 'operation',
    fixed: 'right',
    width: 100
  }
]
// 定义表格数据，初始为空
const data = reactive({
  list: []
})

// 分页配置
const pagination = reactive({
  pageSize: 3, // 每页显示的条数
  showSizeChanger: true, // 是否可以改变每页显示的条数
  pageSizeOptions: ['3', '20', '30', '40', '50'], // 可选的每页显示条数
  showQuickJumper: true, // 是否可以快速跳转到指定页
  showTotal: (total:number) => `共 ${total} 条`, // 显示总条数和当前数据范围
  current: 1, // 当前页数
  total: 0, // 总条数
  onChange: handlePageChange // 页码改变时的回调函数
})

// 对话框表单的引用变量
const dialogFormRef = ref()
// 对话框表单数据
let dialogFormState = reactive({
  username: '',
  password: '',
  role: 'student',
  stuid: ''
})
// 对话框标题
const dialogTitle = ref<string>('新增用户')

// 对话框是否可见
const modalVisible = ref<boolean>(false)
// label列宽度
const labelCol = { span: 5 }
// 文本框列宽度
const wrapperCol = { span: 13 }

// 设置对话框是否可见
const setModalVisible = (open: boolean) => {
  modalVisible.value = open
}

// 组件挂载后，获取表格数据
onMounted(() => {
  userApi
    .list({}, pagination.current, pagination.pageSize)
    .then((res) => {
      data.list = res.data.list
      pagination.total = res.data.total
    })
    .catch((err) => {
      messageApi.error(err.msg)
    })
})

// 定义点击查询按钮的事件
const onFinish = (values: any) => {
  userApi
    .list(values, pagination.current, pagination.pageSize)
    .then((res) => {
      data.list = res.data.list
      pagination.total = res.data.total
    })
    .catch((err) => {
      messageApi.error(err.msg)
    })
}

// 页码改变时的处理函数
function handlePageChange(pageNum: number, pageSize: number) {
  userApi
    .list(searchFormState, pageNum, pageSize)
    .then((res) => {
      data.list = res.data.list
      pagination.total = res.data.total
      pagination.current = pageNum
      pagination.pageSize = pageSize
    })
    .catch((err) => {
      messageApi.error(err.msg)
    })
}

// 新增用户
const newUser = () => {
  dialogTitle.value = '新增用户'
  dialogFormState = reactive({
    username: '',
    password: '',
    role: 'student',
    stuid: ''
  })

  setModalVisible(true)
}

// 更新用户
const update = (record) => {
  dialogTitle.value = '更新用户'
  dialogFormState = record
  setModalVisible(true)
}

// 对话框确定按钮点击事件
function clickOK() {
  dialogFormRef.value
    .validate()
    .then(() => {
      if (dialogTitle.value === '新增用户') {
        userApi
          .add(dialogFormState)
          .then(() => {
            messageApi.success('新增成功！')
            setModalVisible(false)
            handlePageChange(pagination.current, pagination.pageSize)
          })
          .catch((err) => {
            messageApi.error(err.msg)
          })
      } else {
        userApi
          .update(dialogFormState)
          .then(() => {
            messageApi.success('更新成功！')
            setModalVisible(false)
            handlePageChange(pagination.current, pagination.pageSize)
          })
          .catch((err) => {
            messageApi.error(err.msg)
          })
      }
    })
    .catch(() => {
      messageApi.error('表单校验失败！')
    })
}

// 删除用户
const remove = (record) => {
  Modal.confirm({
    title: `你确定要删除${record.username}吗?`,
    icon: createVNode(ExclamationCircleOutlined),
    content: '',
    okText: '确定',
    okType: 'danger',
    cancelText: '取消',
    onOk() {
      userApi
        .delete(record.username)
        .then(() => {
          messageApi.success('删除成功！')
          handlePageChange(pagination.current, pagination.pageSize)
        })
        .catch((err) => {
          messageApi.error(err.msg)
        })
    },
    onCancel() {
      console.log('Cancel')
    }
  })
}

// 清空表单
const resetForm = () => {
  searchFormState.username = ''
  searchFormState.role = ''
  searchFormState.stuid = ''
}
</script>

<style lang="scss" scoped>
.bodyStyle {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
.formStyle {
  margin: 10px;
}
/* 检验信息左对齐 */
.error-align {
  text-align: left;
}
</style>
