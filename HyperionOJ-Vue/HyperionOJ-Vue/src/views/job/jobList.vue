<template>
    <div style="width: 60%">

        <div>
            <!--搜索框-->
            <el-input size="default" placeholder="搜索题目" style="width: 50%" />
            <!--搜索按钮-->
            <el-button size="default" style="width: 20%" @click="searchProblem()">搜索</el-button>
            <!--创建作业按钮-->
            <el-button size="default" style="width: 20%" @click="searchProblem()">创建作业</el-button>
        </div>
        <el-table :data="jobList" stripe>
            <el-table-column prop="jobName" label="作业" width="360">
                <template #default="scope">
                    <span @click="clickProblem(scope.row.id)" style="cursor: pointer;">{{ scope.row.jobName }}</span>
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

import router from "@/router";

const searchProblem = () => {
    console.log("查找题目")
}

const clickProblem = (id: number) => {
    router.push({ path: `/job/${id}` })
}

const jobList = [
    {
        id: 1,
        jobName: '作业1',
        cpuUsage: '2',
        memUsage: '3072',
        state: 'RUNNING'
    },
    {
        id: 2,
        jobName: '作业2',
        cpuUsage: '4',
        memUsage: '3072',
        state: 'NEW'
    },
    {
        id: 3,
        jobName: '作业3',
        cpuUsage: '3',
        memUsage: '3072',
        state: 'STOP'
    },
    {
        id: 4,
        jobName: '作业4',
        cpuUsage: '1',
        memUsage: '3072',
        state: 'FINNESH'
    }, {
        id: 5,
        jobName: '作业1',
        cpuUsage: '2',
        memUsage: '3072',
        state: 'RUNNING'
    },
    {
        id: 6,
        jobName: '作业2',
        cpuUsage: '4',
        memUsage: '3072',
        state: 'NEW'
    },
    {
        id: 7,
        jobName: '作业3',
        cpuUsage: '3',
        memUsage: '3072',
        state: 'STOP'
    },
    {
        id: 8,
        jobName: '作业4',
        cpuUsage: '1',
        memUsage: '3072',
        state: 'FINNESH'
    }, {
        id: 9,
        jobName: '作业1',
        cpuUsage: '2',
        memUsage: '3072',
        state: 'RUNNING'
    },
    {
        id: 10,
        jobName: '作业2',
        cpuUsage: '4',
        memUsage: '3072',
        state: 'NEW'
    }
]

</script>

<style scoped></style>