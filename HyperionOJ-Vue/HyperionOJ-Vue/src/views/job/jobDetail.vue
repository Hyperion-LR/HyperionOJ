<template>
    <el-form :model="jobInfo" label-width="120px">
        <el-row>
            <el-text>
                {{ jobInfo.name }}
            </el-text>
            <el-text>
                {{ jobInfo.status }}
                {{ jobInfo.flinkUrl }}
            </el-text>
        </el-row>
        <el-form-item label="jar文件">
            <el-input v-model="jobInfo.jarName" />
            <el-upload class="upload-demo" action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" multiple>
                <el-button type="primary">Click to upload</el-button>
            </el-upload>
        </el-form-item>
        <el-form-item label="jar主类">
            <el-input v-model="jobInfo.mainClass" type="textarea" />
        </el-form-item>
        <el-form-item label="jar运行参数">
            <el-input v-model="jobInfo.mainArgs" type="textarea" />
        </el-form-item>
        <el-form-item label="solt">
            <el-input v-model="jobInfo.tmSlot" />
        </el-form-item>
        <el-form-item label="job manager 内存">
            <el-input v-model="jobInfo.jmMem" />
        </el-form-item>
        <el-form-item label="task manager 内存">
            <el-input v-model="jobInfo.tmMem" />
        </el-form-item>
        <el-form-item label="并行度">
            <el-input v-model="jobInfo.parallelism" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="runJob">运行</el-button>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="deleteJob">删除</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
import { getJobDetail } from '@/api/job';
import { JobInfo } from '@/api/job/types';
import { reactive } from 'vue'
const data = reactive({
    jobInfo: {} as JobInfo
})
const { jobInfo } = toRefs(data);

onMounted(() => {
    handleProblemDetail();
})

const handleProblemDetail = () => {
    const jobId = '1638461422685437953';
    getJobDetail(jobId).then(({ data }) => {
        if (data.code == 200) {
            jobInfo.value = data.data;
            console.log(jobInfo.value)
        } else {
            console.log('获取题目详情失败' + data.msg)
        }
    });
}

const runJob = () => {
    console.log('开始运行!')
}

const deleteJob = () => {
    console.log('开始运行!')
}

</script>

<style scoped></style>