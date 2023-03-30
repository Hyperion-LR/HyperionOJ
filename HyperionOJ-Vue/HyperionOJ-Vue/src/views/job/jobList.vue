<template>
    <div style="width: 60%">
        <el-row>
            <!--搜索框-->
            <el-input size="default" placeholder="搜索作业" style="width: 50%" />
            <!--搜索按钮-->
            <el-button size="default" style="width: 20%" @click="searchProblem()">搜索</el-button>
            <!--创建作业按钮-->
            <el-button size="default" style="width: 20%" @click="createJobVisible = true">创建作业</el-button>
            <el-dialog v-model="createJobVisible" title="创建作业">
                <el-form :model="jobInfo">
                    <el-form-item label="作业名称">
                        <el-input v-model="jobInfo.name" />
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input v-model="jobInfo.description" />
                    </el-form-item>
                    <el-form-item label="jm内存">
                        <el-input v-model="jobInfo.jmMem" />
                    </el-form-item>
                    <el-form-item label="tm内存">
                        <el-input v-model="jobInfo.tmMem" />
                    </el-form-item>
                    <el-form-item label="tmSlot">
                        <el-input v-model="jobInfo.tmSlot" />
                    </el-form-item>
                    <el-form-item label="并发度">
                        <el-input v-model="jobInfo.parallelism" />
                    </el-form-item>
                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="createJobVisible = false;">取消</el-button>
                        <el-button type="primary" @click="handleCreateJob">创建</el-button>
                    </span>
                </template>
            </el-dialog>

        </el-row>
        <el-table :data="jobList" stripe>
            <el-table-column prop="name" label="作业" width="360">
                <template #default="scope">
                    <span @click="clickProblem(scope.row.id)" style="cursor: pointer;">{{ scope.row.name }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="cpuUsage" label="cpu使用情况" width="120" />
            <el-table-column prop="memUsage" label="内存使用情况" />
            <el-table-column prop="state" label="当前状态" />
        </el-table>
        <el-pagination background layout="prev, pager, next" :total="jobNumber" />
    </div>
</template>

<script setup lang="ts">

import { getJobList } from "@/api/job";
import { createJob } from "@/api/job";
import { JobInfo } from "@/api/job/types";
import { PageParam } from "@/api/pageParam/types";
import router from "@/router";

const data = reactive({
    pageParam: {
        page: 1,
        pageSize: 10,
    } as PageParam,
    jobList: [] as JobInfo[],
    jobNumber: 0,
    jobInfo: {} as JobInfo,
    createJobVisible: false
})
const { pageParam, jobList, jobNumber, jobInfo, createJobVisible } = toRefs(data);

onMounted(() => {
    handleJobList();
})

const searchProblem = () => {
    console.log("查找题目")
}

const clickProblem = (id: number) => {
    router.push({ path: `/job/${id}` })
}

const handleJobList = () => {
    getJobList(pageParam.value).then(({ data }) => {
        if (data.code == 200) {
            jobList.value = data.data;
            jobNumber.value = jobList.value.length;
        } else {
            console.log('获取题目列表失败' + data.msg)
        }
    });
}

const handleCreateJob = () => {
    createJob(jobInfo.value).then(({ data }) => {
        if (data.code == 200) {
            jobInfo.value = data.data;
            createJobVisible.value = false;
            router.push({ path: `/job/${jobInfo.value.id}` })
        } else {
            console.log('获取题目列表失败' + data.msg)
        }
    });
    console.log(jobInfo.value)
}


</script>

<style scoped></style>