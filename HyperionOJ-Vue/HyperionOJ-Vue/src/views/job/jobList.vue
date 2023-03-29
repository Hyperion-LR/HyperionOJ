<template>
    <div style="width: 60%">

        <el-row>
            <!--搜索框-->
            <el-input size="default" placeholder="搜索作业" style="width: 50%" />
            <!--搜索按钮-->
            <el-button size="default" style="width: 20%" @click="searchProblem()">搜索</el-button>
            <!--创建作业按钮-->
            <el-button size="default" style="width: 20%" @click="searchProblem()">创建作业</el-button>
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
        <el-pagination background layout="prev, pager, next" :total="1000" />
    </div>
</template>

<script setup lang="ts">

import { getJobList } from "@/api/job";
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
})
const { pageParam, jobList, jobNumber } = toRefs(data);

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
        } else {
            console.log('获取题目列表失败' + data.msg)
        }
    });
}


</script>

<style scoped></style>