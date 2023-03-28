<template>
    <el-container>
        <el-main>
            <el-row>
                <el-card id="userInfo">
                    <el-row>
                        <el-col :span="6">
                            <el-avatar shape="square" :size="100" :src=user.avatar />
                        </el-col>
                        <el-col :span="12">
                            <el-descriptions>
                                <el-descriptions-item label="username">{{ user.username }}</el-descriptions-item>
                                <el-descriptions-item label="phone">{{ user.id }}</el-descriptions-item>
                                <el-descriptions-item label="mail">{{ user.mail }}</el-descriptions-item>
                                <el-descriptions-item label="createTime">{{ user.createTime }}</el-descriptions-item>
                                <el-descriptions-item label="lastLoginTime">{{ user.lastLoginTime }}</el-descriptions-item>
                            </el-descriptions>
                        </el-col>
                        <el-col :span="6">
                            <el-dialog v-model="updateFormVisible" title="更新个人信息">
                                <el-form :model="user">
                                    <el-form-item label="用户名">
                                        <el-input v-model="user.username" />
                                    </el-form-item>
                                    <el-form-item label="头像">
                                        <el-input v-model="user.avatar" />
                                    </el-form-item>
                                </el-form>
                                <template #footer>
                                    <span class="dialog-footer">
                                        <el-button @click="updateFormVisible = false">
                                            取消
                                        </el-button>
                                        <el-button type="primary" @click="updateUser">
                                            修改
                                        </el-button>
                                    </span>
                                </template>
                            </el-dialog>
                            <el-button @click="updateFormVisible = true">
                                修改个人信息
                            </el-button>
                            <el-dialog v-model="updatePasswordFormVisible" title="更新账号密码">
                                <el-form :model="updatePasswordParam">
                                    <el-form-item label="密码">
                                        <el-input v-model="updatePasswordParam.password" />
                                    </el-form-item>
                                    <el-form-item label="再次输入密码">
                                        <el-input v-model="updatePasswordParam.rePassword" />
                                    </el-form-item>
                                    <el-form-item label="邮箱验证码">
                                        <el-input v-model="updatePasswordParam.code" />
                                        <el-button @click="getUpdatePasswordCode">获取邮箱验证码</el-button>
                                    </el-form-item>
                                </el-form>
                                <template #footer>
                                    <span class="dialog-footer">
                                        <el-button @click="updatePasswordFormVisible = false">
                                            取消
                                        </el-button>
                                        <el-button type="primary" @click="updatePassword">
                                            修改
                                        </el-button>
                                    </span>
                                </template>
                            </el-dialog>
                            <el-button @click="updatePasswordFormVisible = true">
                                修改账号密码
                            </el-button>
                        </el-col>
                    </el-row>
                </el-card>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-row style="min-height: 50%;">
                        <el-card class="card-box">
                            {{ user.problemAcNumber }}<br>
                            题目通过个数
                        </el-card>
                        <el-card class="card-box">
                            {{ user.problemSubmitAcNumber }} <br>
                            提交AC次数
                        </el-card>
                        <el-card class="card-box">
                            {{ user.problemSubmitNumber }} <br>
                            总提交次数
                        </el-card>
                    </el-row>
                    <el-row style="min-height: 50%;">
                        <el-card class="card-box">
                            {{ user.jobNumber }} <br>
                            job总数量
                        </el-card>
                        <el-card class="card-box">
                            {{ user.cpuUsage }} <br>
                            cpu使用情况
                        </el-card>
                        <el-card class="card-box">
                            {{ user.memUsage }} <br>
                            内存使用情况
                        </el-card>
                    </el-row>
                </el-col>
                <el-col :span="12">
                    <el-card>
                        <el-table :data="submitList" stripe>
                            <el-table-column prop="submitId" label="提交ID">
                                <template #default="scope">
                                    <span @click="clickSubmit(scope.row.submitId)" style="cursor: pointer;">{{
                                        scope.row.submitId }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="problemId" label="题目" />
                            <el-table-column prop="submitTime" label="提交时间" />
                            <el-table-column prop="statu" label="结果" />
                            <el-table-column prop="runtime" label="运行时间" />
                        </el-table>
                        <el-pagination background layout="prev, pager, next" :total="1000" />
                    </el-card>
                </el-col>
            </el-row>
        </el-main>
    </el-container>
</template>

<script setup lang="ts">

import router from "@/router";
import useStore from "@/store";
import { getCode } from "@/api/verCode";

const { user } = useStore();


const data = reactive({
    updateFormVisible: false,
    updatePasswordFormVisible: false,
    updatePasswordParam: {
        password: '',
        rePassword: '',
        code: ''
    },
    getCodeParam: {
        mail: '',
        subject: ''
    },
});
const {
    updateFormVisible,
    updatePasswordFormVisible,
    updatePasswordParam,
    getCodeParam,
} = toRefs(data);


const clickSubmit = (id: number) => {
    router.push({ path: `/submit/${id}` })
}

const updateUser = () => {

}

const getUpdatePasswordCode = () => {
    getCodeParam.value.mail = user.mail;
    getCodeParam.value.subject = 'updatePassword';
    getCode(getCodeParam.value).then(({ data }) => {
        if (data.code == 200) {
            console.log('邮箱验证码获取成功');
        } else {
            console.log('邮箱验证码获取失败');
        }
    })
}

const updatePassword = () => {

}


const submitList = [
    {
        submitId: '123123123',
        problemId: '1',
        submitTime: '2023-3-25 16:16:45',
        statu: 'ACCEPT',
        runtime: '52ms'
    }, {
        submitId: '123123123',
        problemId: '1',
        submitTime: '2023-3-25 16:16:45',
        statu: 'ACCEPT',
        runtime: '52ms'
    }, {
        submitId: '123123123',
        problemId: '1',
        submitTime: '2023-3-25 16:16:45',
        statu: 'ACCEPT',
        runtime: '52ms'
    }, {
        submitId: '123123123',
        problemId: '1',
        submitTime: '2023-3-25 16:16:45',
        statu: 'ACCEPT',
        runtime: '52ms'
    }, {
        submitId: '123123123',
        problemId: '1',
        submitTime: '2023-3-25 16:16:45',
        statu: 'ACCEPT',
        runtime: '52ms'
    }, {
        submitId: '123123123',
        problemId: '1',
        submitTime: '2023-3-25 16:16:45',
        statu: 'ACCEPT',
        runtime: '52ms'
    }, {
        submitId: '123123123',
        problemId: '1',
        submitTime: '2023-3-25 16:16:45',
        statu: 'ACCEPT',
        runtime: '52ms'
    },
]

</script>

<style scoped>
.card-box {
    min-height: 100%;
    min-width: 33%;
}

#userInfo {
    min-height: 20%;
    min-width: 100%;
}
</style>