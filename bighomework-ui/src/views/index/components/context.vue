
import { format } from 'path';

import { utimesSync } from 'fs';

import { utimesSync } from 'fs';

import { log } from 'console';

import { addEmitHelper } from 'typescript';
<template>
    <a-row align="middle" class="index-row" justify="center">
        <a-col :span="20">
            <a-button @click="newCoruse" type="primary">添加员工</a-button>
            <!-- 表格 -->
            <a-table :columns="columns" :data-source="empData.empList" :scroll="{ x: 650, y: 300 }">
                <template #bodyCell="{ column, record }">
                    <template v-if="column.key === 'operation'">
                        <a @click="update(record)">更新</a>
                        <a-divider type="vertical" />
                        <a @click="remove(record)">删除</a>
                    </template>
                </template>
            </a-table>
            <!-------新增/更新对话框开始--------->
            <a-modal v-model:open="modalVisible" :title="dialogTitle" ok-text="确认" cancel-text="取消" width="500px" centered
                @ok="clickOK">
                <a-form ref="dialogFormRef" :model="dialogFormState" :label-col="labelCol" :wrapper-col="wrapperCol">
                    <a-form-item has-feedback label="姓名" name="name" :rules="[
                        { required: true, message: '请输入姓名' }
                    ]">
                        <a-input v-model:value="dialogFormState.name" />
                    </a-form-item>
                    <a-form-item has-feedback label="工资" name="salary" :rules="[
                        { required: true, message: '请输入工资' }
                    ]">
                        <a-input v-model:value="dialogFormState.salary"></a-input>
                    </a-form-item>
                    <a-form-item has-feedback label="年龄" name="age" :rules="[
                        { required: true, message: '请输入年龄' }
                    ]">
                        <a-input v-model:value="dialogFormState.age"></a-input>
                    </a-form-item>
                    <a-form-item has-feedback label="生日" name="bir" :rules="[
                    ]">
                        <a-date-picker v-model:value="dialogFormState.bir" />
                    </a-form-item>
                </a-form>
            </a-modal>
            <!-------新增/更新对话框结束--------->
        </a-col>
    </a-row>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { notification } from 'ant-design-vue';
import { useRouter } from 'vue-router'
import EmpApi from '@/api/empApi'
import dayjs from 'dayjs'
import { Modal } from 'ant-design-vue'
import { ExclamationCircleOutlined, PlusOutlined } from '@ant-design/icons-vue'
import { createVNode, h } from 'vue'

const router = useRouter()
const empApi = new EmpApi()
const empData = reactive({
    empList: []
})

// 对话框表单数据
let dialogFormState = reactive({
    id: '',
    name: '',
    salary: '',
    age: '',
    bir: null
})

// 定义表格标题列
const columns: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id', width: 150 },
    { title: '姓名', dataIndex: 'name', key: 'name', width: 150 },
    { title: '工资', dataIndex: 'salary', key: 'salary', width: 150 },
    { title: '年龄', dataIndex: 'age', key: 'age', width: 150 },
    { title: '生日', dataIndex: 'bir', key: 'bir', width: 150 },
    {
        title: '操作',
        key: 'operation',
        fixed: 'right',
        width: 100
    }
]

// 获取员工数据
empApi.findAllEmp().then(res => {
    empData.empList = res.data.data
    console.log(res.data.data);
    console.log(empData.empList);
}).catch(err => {
    console.log(err);
})

// 对话框标题
const dialogTitle = ref<string>('新增员工')
// 对话框表单的引用变量
const dialogFormRef = ref()

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

// 更新课程
const update = (record) => {
    dialogTitle.value = '更新员工'
    record.bir = dayjs(record.bir)
    dialogFormState = record
    console.log(dialogFormState);
    setModalVisible(true)
}

// 新增员工
const newCoruse = () => {
    dialogTitle.value = '新增员工'
    dialogFormState = reactive({
        cid: '',
        cname: '',
    })
    setModalVisible(true)
}

// 对话框确定按钮点击事件
function clickOK() {
    dialogFormRef.value
        .validate()
        .then(() => {
            if (dialogTitle.value === '新增员工') {
                empApi.addEmp(dialogFormState).then(res => {
                    notification['success']({
                        message: '新增成功',
                        description:
                            '新增员工成功',
                    });
                    setModalVisible(false)
                    empApi.findAllEmp().then(res => {
                        empData.empList = res.data.data
                    }).catch(err => {
                        console.log(err);
                    })
                }).catch(err => {
                    console.log(err);
                })
            } else {
                console.log(dialogFormState);
                empApi.updateEmp(dialogFormState).then(res => {
                    notification['success']({
                        message: '更新成功',
                        description:
                            '更新员工成功',
                    });
                    setModalVisible(false)
                    empApi.findAllEmp().then(res => {
                        empData.empList = res.data.data
                    }).catch(err => {
                        console.log(err);
                    })
                }).catch(err => {
                    console.log(err);
                })
            }
        })
        .catch(() => {
            notification['error']({
                message: '表单校验失败',
                description:
                    '请仔细填写表单信息',
            });
        })
}

// 删除课程
const remove = (record) => {
    Modal.confirm({
        title: `你确定要删除${record.name}吗?`,
        icon: createVNode(ExclamationCircleOutlined),
        content: '',
        okText: '确定',
        okType: 'danger',
        cancelText: '取消',
        onOk() {
            empApi
                .deleteEmp({id:record.id})
                .then(() => {
                    notification['success']({
                        message: '删除成功',
                        description:
                            '',
                    });
                    empApi.findAllEmp().then(res => {
                        empData.empList = res.data.data
                    }).catch(err => {
                        console.log(err);
                    })
                })
                .catch((err) => {
                    notification['error']({
                        message: '删除失败',
                        description:
                            '请联系管理员',
                    });
                })
        },
        onCancel() {
            console.log('Cancel')
        }
    })
}


</script>

<style scoped>
.index-row {
    height: 60vh;
}
</style>