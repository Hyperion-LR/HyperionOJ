<template>
    <div>
        <el-table :data="submitRecode" style="width: 100%">
            <el-table-column prop="id" label="ID" width="180">
                <template #default="scope">
                    <span @click="clickSubmit(scope.row.id)" style="cursor: pointer;">{{ scope.row.id }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="state" label="结果" width="180" />
            <el-table-column prop="time" label="time" />
        </el-table>
    </div>
</template>

<script setup lang="ts">

import router from "@/router";

const submitRecode = [
    {
        id: '123431414132122341234',
        state: 'ACCEPT',
        time: '2023-3-25 10:10:32',
    },
    {
        id: '412341214132411431213',
        state: 'ACCEPT',
        time: '2023-3-25 10:05:32',
    },
    {
        id: '3123123123123112312343',
        state: 'ACCEPT',
        time: '2023-3-25 9:11:22',
    },
    {
        id: '1233412324242312342343',
        state: 'ACCEPT',
        time: '2023-3-25 9:5:32',
    },
    {
        id: '1231231231231231231231',
        state: 'ACCEPT',
        time: '2023-3-25 8:51:32',
    },
    {
        id: '123233242342342434234',
        state: 'ACCEPT',
        time: '2023-3-25 8:51:22',
    },
]

const clickSubmit = (id: number) => {
    router.push({ path: `/submit/${id}` })
}

</script>

<style scoped></style>