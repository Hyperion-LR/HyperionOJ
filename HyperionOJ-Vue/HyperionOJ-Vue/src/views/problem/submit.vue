<template>
    <el-main>
        <el-row>
            <el-card id="提交记录">
                <el-row>
                    <el-descriptions>
                        <el-descriptions-item label="结果">{{ submitInfo.verdict }}</el-descriptions-item>
                        <el-descriptions-item label="语言">{{ submitInfo.codeLang }}</el-descriptions-item>
                        <el-descriptions-item label="运行时间">{{ submitInfo.runTime }}</el-descriptions-item>
                        <el-descriptions-item label="提交时间">{{ submitInfo.createTime }}</el-descriptions-item>
                    </el-descriptions>
                </el-row>
                <el-row>
                    <el-card>
                        <el-descriptions>
                            <el-descriptions-item label="代码">
                                {{ submitInfo.codeBody }}
                            </el-descriptions-item>
                        </el-descriptions>
                    </el-card>
                </el-row>
            </el-card>
        </el-row>
    </el-main>
</template>

<script setup lang="ts">import { getSubmit } from '@/api/problem';
import { SubmitInfo } from '@/api/problem/types';

const route = useRoute();

const data = reactive({
    submitInfo: {} as SubmitInfo,
})
const {  submitInfo } = toRefs(data);

const handleGetSubmit = () => {
    const submit = route.params.id as string;
    getSubmit(submit).then(({ data }) => {
        if (data.code == 200) {
            submitInfo.value = data.data;
            console.log(submitInfo.value)
        } else {
            console.log("获取提交详情失败" + data.msg)
        }
    });
}

onMounted(() => {
    handleGetSubmit();
})

</script>

<style scoped></style>