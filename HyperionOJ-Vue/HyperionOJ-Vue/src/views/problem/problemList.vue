<template>
    <div class="problem" style="width: 100%">
        <div style="width: 60%">
            <div>
                <el-select placeholder="选择分类" style="width: 20%">
                    <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName" :value="item.id">
                    </el-option></el-select>
                <!--搜索框-->
                <el-input size="default" placeholder="搜索题目" style="width: 60%" />
                <!--搜索按钮-->
                <el-button size="default" style="width: 20%" @click="searchProblem()">搜索</el-button>
            </div>
            <el-table :data="problemList" stripe>
                <el-table-column prop="title" label="题目" width="360">
                    <template #default="scope">
                        <span @click="clickProblem(scope.row.id)" style="cursor: pointer;">{{ scope.row.probelm }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="problemLevel" label="难度" width="120" />
                <el-table-column prop="acNumber" label="通过次数" />
                <el-table-column prop="submitNumber" label="提交次数" />
            </el-table>
            <el-pagination background layout="prev, pager, next" :total="problemNumber" />
        </div>
        <div class="problem-tag" style="width: 40%">
            标签
        </div>
    </div>
</template>

<script setup lang="ts">

import { getCategoryList, getProbelmNumber } from "@/api/problem";
import { getProbelmList } from "@/api/problem";
import { CategoryInfo, ProblemInfo } from "@/api/problem/types";
import { PageParam } from "@/api/pageParam/types";
import router from "@/router";

const data = reactive({
    pageParam: {
        page: 1,
        pageSize: 10,
    } as PageParam,
    categoryList: [] as CategoryInfo[],
    problemList: [] as ProblemInfo[],
    problemNumber: 0,
})
const { pageParam, categoryList, problemList, problemNumber } = toRefs(data);

const searchProblem = () => {
    console.log("")
}

const clickProblem = (id: number) => {
    router.push({ path: `/problem/${id}` })
}

const handleProbelmNumber = () => {
    getProbelmNumber().then(({ data }) => {
        if (data.code == 200) {
            problemNumber.value = data.data;
        } else {
            console.log('获取题目数量失败' + data.msg)
        }
    });
}


const handleProbelmList = () => {
    getProbelmList(pageParam.value).then(({ data }) => {
        if (data.code == 200) {
            problemList.value = data.data;
            console.log(problemList.value)
        } else {
            console.log('获取题目列表失败' + data.msg)
        }
    });
}

const handleCategoryList = () => {
    getCategoryList().then(({ data }) => {
        if (data.code == 200) {
            categoryList.value = data.data;
        } else {
            console.log('获取分类列表失败' + data.msg);
        }
    });
}

onMounted(() => {
    handleCategoryList();
    handleProbelmNumber();
    handleProbelmList();
})
</script>

<style scoped>
.problem {
    display: flex;
    justify-content: space-between;
}

.problem-tag {
    display: flex;
}
</style>