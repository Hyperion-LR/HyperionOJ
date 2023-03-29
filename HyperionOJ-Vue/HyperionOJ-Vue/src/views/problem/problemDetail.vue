<template>
    <el-row>
        <el-text class="mx-1" size="large">{{ problemInfo.title }}</el-text>
    </el-row>
    <el-row>
        <el-text class="mx-1" size="large">运行时间限制：{{ problemInfo.runTime }} 运行内存限制：{{ problemInfo.runMemory }}</el-text>
    </el-row>
    <el-row>
        <el-text class="mx-1" size="large">{{ problemInfo.problemBody }}</el-text>
    </el-row>
</template>

<script setup lang="ts">
import { getProblemDetail } from "@/api/problem";
import { ProblemInfo } from "@/api/problem/types";
const data = reactive({
    problemInfo: {} as ProblemInfo
})
const { problemInfo } = toRefs(data);

onMounted(() => {
    handleProblemDetail();
})

const handleProblemDetail = () => {
    const problemId = '1';
    getProblemDetail(problemId).then(({ data }) => {
        if (data.code == 200) {
            problemInfo.value = data.data;
        } else {
            console.log('获取题目详情失败' + data.msg)
        }
    });
}

</script>

<style scoped></style>