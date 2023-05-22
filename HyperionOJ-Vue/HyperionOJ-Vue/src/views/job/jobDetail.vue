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
            <el-upload ref="uploadRef" class="upload-demo" :action="`/job/resource/${jobId}`" :auto-upload="false"
                accept=".jar" :http-request="handleUpload">
                <template #trigger>
                    <el-button type="primary">提交jar文件</el-button>
                </template>
            </el-upload>
        </el-form-item>
        <el-form-item label="jar名称">
            <el-input v-model="jobInfo.jarName" disabled />
        </el-form-item>
        <el-form-item label="jar主类">
            <el-input v-model="jobUpdateInfo.mainClass" type="textarea" />
        </el-form-item>
        <el-form-item label="jar运行参数">
            <el-input v-model="jobUpdateInfo.mainArgs" type="textarea" />
        </el-form-item>
        <el-form-item label="solt">
            <el-input v-model="jobUpdateInfo.tmSlot" />
        </el-form-item>
        <el-form-item label="job manager 内存">
            <el-input v-model="jobUpdateInfo.jmMem" />
        </el-form-item>
        <el-form-item label="task manager 内存">
            <el-input v-model="jobUpdateInfo.tmMem" />
        </el-form-item>
        <el-form-item label="并行度">
            <el-input v-model="jobUpdateInfo.parallelism" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="handleSaveJob">保存</el-button>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="handleRunJob">{{jobActionInfo.action === "STOP" ? "结束":"运行"}}</el-button>
        </el-form-item>
        <el-form-item>
            <el-dialog v-model="deleteJobVisible">
                <el-text>
                    是否删除
                </el-text>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="deleteJobVisible = false;">取消</el-button>
                        <el-button type="primary" @click="handeldeleteJob">确定</el-button>
                    </span>
                </template>
            </el-dialog>
            <el-button type="danger" @click="deleteJobVisible = true">删除</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
import { actionJob, deleteJob, getJobDetail, saveJob, uploadJar } from '@/api/job';
import { JobActionInfo, JobInfo } from '@/api/job/types';
import { reactive } from 'vue'
import router from "@/router";
import { UploadRequestOptions, UploadInstance } from 'element-plus';

const uploadRef = ref<UploadInstance>()

const route = useRoute();

const jobId = route.params.id as string;

const data = reactive({
    jobInfo: {} as JobInfo,
    jobUpdateInfo: {} as JobInfo,
    jobActionInfo: {} as JobActionInfo,
    deleteJobVisible: false,
})
const { jobInfo, jobUpdateInfo, jobActionInfo, deleteJobVisible } = toRefs(data);

onMounted(() => {
    handleJobDetail();
    setInterval(() => {
        heartbeatJob()
    }, 3000);
})

const handleJobDetail = () => {
    getJobDetail(jobId).then(({ data }) => {
        if (data.code == 200) {
            jobInfo.value = data.data;
            jobUpdateInfo.value.id = jobInfo.value.id;
            jobUpdateInfo.value.name = jobInfo.value.name;
            jobUpdateInfo.value.description = jobInfo.value.description;
            jobUpdateInfo.value.ownerId = jobInfo.value.ownerId;
            jobUpdateInfo.value.status = jobInfo.value.status;
            jobUpdateInfo.value.startTime = jobInfo.value.startTime;
            jobUpdateInfo.value.createTime = jobInfo.value.createTime;
            jobUpdateInfo.value.cpuUsage = jobInfo.value.cpuUsage;
            jobUpdateInfo.value.memUsage = jobInfo.value.memUsage;
            jobUpdateInfo.value.jmMem = jobInfo.value.jmMem;
            jobUpdateInfo.value.tmMem = jobInfo.value.tmMem;
            jobUpdateInfo.value.parallelism = jobInfo.value.parallelism;
            jobUpdateInfo.value.tmSlot = jobInfo.value.tmSlot;
            jobUpdateInfo.value.flinkUrl = jobInfo.value.flinkUrl;
            jobUpdateInfo.value.monitorUrl = jobInfo.value.monitorUrl;
            jobUpdateInfo.value.outerUrl = jobInfo.value.outerUrl;
            jobUpdateInfo.value.type = jobInfo.value.type;
            jobUpdateInfo.value.jarName = jobInfo.value.jarName;
            jobUpdateInfo.value.mainClass = jobInfo.value.mainClass;
            jobUpdateInfo.value.mainArgs = jobInfo.value.mainArgs;
            jobUpdateInfo.value.userSql = jobInfo.value.userSql;
        } else {
            console.log('获取job详情失败' + data.msg);
        }
    });
}


const heartbeatJob = () => {
    getJobDetail(jobId).then(({ data }) => {
        if (data.code == 200) {
            jobInfo.value = data.data;
            jobActionInfo.value.jobId = jobInfo.value.id;
            if(jobInfo.value.status != "RUNNING"){
                jobActionInfo.value.action = "START";
                jobInfo.value.flinkUrl = "";
            }else{
                jobActionInfo.value.action = "STOP";
            }
        } else {
            console.log('获取job详情失败' + data.msg);
        }
    });
}

const handleSaveJob = () => {
    uploadRef.value?.submit();
    saveJob(jobUpdateInfo.value).then(({ data }) => {
        if (data.code == 200) {
            jobInfo.value = data.data;
        } else {
            console.log('获取job详情失败' + data.msg)
        }
    });
}

function handleUpload(options: UploadRequestOptions): XMLHttpRequest | Promise<unknown> {
    let formData = new FormData();
    formData.append("resourceList", options.file);
    jobUpdateInfo.value.jarName = options.file.name;
    uploadJar(jobUpdateInfo.value.id, formData).then(({ data }) => {
        if (data.code == 200) {
            console.log('上传成功' + data.msg)
        } else {
            console.log('上传失败' + data.msg)
        }
    });
    return new XMLHttpRequest();
}

const handleRunJob = () => {
    actionJob(jobActionInfo.value).then(({ data }) => {
        if (data.code == 200) {
            console.log('启动成功' + data.msg)
        } else {
            console.log('启动失败' + data.msg)
        }
    });
}

const handeldeleteJob = () => {
    deleteJob(jobInfo.value.id).then(({ data }) => {
        if (data.code == 200) {
            console.log('删除成功' + data.msg)
            deleteJobVisible.value = false;
            router.push({ path: `/job` })
        } else {
            console.log('删除失败' + data.msg)
        }
    });
}

</script>

<style scoped></style>