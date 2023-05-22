<template>
    <div>
        <el-table :data="submitInfo" style="width: 100%">
            <el-table-column prop="id" label="ID" width="180">
                <template #default="scope">
                    <span @click="clickSubmit(scope.row.id)" style="cursor: pointer;">{{ scope.row.id }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="verdict" label="结果" width="180" />
            <el-table-column prop="createTime" label="time" />
        </el-table>
    </div>
</template>

<script setup lang="ts">

import { PageParam } from "@/api/pageParam/types";
import { getSubmitList } from "@/api/problem";
import { SubmitInfo } from "@/api/problem/types";
import router from "@/router";
import useStore from "@/store";

const route = useRoute();

const { user } = useStore();

const data = reactive({
    pageParam: {
        page: 1,
        pageSize: 10,
    } as PageParam,
    submitInfo: [] as SubmitInfo[]
})
const { pageParam, submitInfo } = toRefs(data);

onMounted(() => {
    handleSubmitList();
})

const handleSubmitList = () => {
    pageParam.value.problemId = route.params.id as string;
    pageParam.value.authorId = user.id;
    getSubmitList(pageParam.value).then(({ data }) => {
        if (data.code == 200) {
            submitInfo.value = data.data;
            console.log(submitInfo.value)
        } else {
            console.log('获取提交列表失败' + data.msg)
        }
    });
}

const clickSubmit = (id: number) => {
    router.push({ path: `/submit/${id}` })
}

</script>

<style scoped></style>