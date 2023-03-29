<template>
    <el-form :model="job" label-width="120px">
        <el-row>
            <el-text>
                {{ job.name }}
            </el-text>
            <el-text>
                {{ job.status }}
            </el-text>
        </el-row>
        <el-form-item label="jar文件">
            <el-input v-model="job.jarFlile" />
            <el-upload class="upload-demo" action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" multiple>
                <el-button type="primary">Click to upload</el-button>
            </el-upload>
        </el-form-item>
        <el-form-item label="jar运行参数">
            <el-input v-model="job.jarArgs" type="textarea" />
        </el-form-item>
        <el-form-item label="solt">
            <el-input v-model="job.solt" />
        </el-form-item>
        <el-form-item label="job manager 内存">
            <el-input v-model="job.jm" />
        </el-form-item>
        <el-form-item label="task manager 内存">
            <el-input v-model="job.tm" />
        </el-form-item>
        <el-form-item label="并行度">
            <el-input v-model="job.p" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="run">运行</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
import { reactive } from 'vue'

// do not use same name with ref
const job = reactive({
    id: '123123',
    name: '作业一',
    status: '运行中',
    jarFlile: 'flink.jar',
    jarArgs: '',
    solt: 2,
    jm: 1024,
    tm: 1024,
    p: 1
})

const run = () => {
    console.log('开始运行!')
}
</script>

<style scoped></style>