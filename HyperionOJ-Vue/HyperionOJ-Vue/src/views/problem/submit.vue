<template>
    <el-main>
        <el-row>
            <el-card id="提交记录">
                <el-row>
                    <el-descriptions>
                        <el-descriptions-item label="结果">{{ submitResult.status }}</el-descriptions-item>
                        <el-descriptions-item label="语言">{{ submitResult.codeLang }}</el-descriptions-item>
                        <el-descriptions-item label="运行时间">{{ submitResult.runtime }}</el-descriptions-item>
                        <el-descriptions-item label="提交时间">{{ submitResult.submitTime }}</el-descriptions-item>
                    </el-descriptions>
                </el-row>
                <el-row>
                    <el-card>
                        <el-descriptions>
                            <el-descriptions-item label="代码">
                                {{ submitResult.codeBody }}
                            </el-descriptions-item>
                        </el-descriptions>
                    </el-card>
                </el-row>
            </el-card>
        </el-row>
    </el-main>
</template>

<script setup lang="ts">

const submitResult = {
    status: 'ACCEPT',
    runtime: '52ms',
    codeLang: 'Java',
    submitTime: '2023-3-26 16:23:12',
    codeBody: ''
}

</script>

<style scoped></style>