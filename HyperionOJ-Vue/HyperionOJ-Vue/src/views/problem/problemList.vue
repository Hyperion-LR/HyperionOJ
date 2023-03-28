<template>
    <div class="problem" style="width: 100%">
        <div style="width: 60%">
            <div>
                <el-select placeholder="选择分类" style="width: 20%">
                    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                    </el-option></el-select>
                <!--搜索框-->
                <el-input size="default" placeholder="搜索题目" style="width: 60%" />
                <!--搜索按钮-->
                <el-button size="default" style="width: 20%" @click="searchProblem()">搜索</el-button>
            </div>
            <el-table :data="problemList" stripe>
                <el-table-column prop="probelm" label="题目" width="360">
                    <template #default="scope">
                        <span @click="clickProblem(scope.row.id)" style="cursor: pointer;">{{ scope.row.probelm }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="difficulty" label="难度" width="120" />
                <el-table-column prop="passrate" label="通过率" />
            </el-table>
            <el-pagination background layout="prev, pager, next" :total="problemNumber" />
        </div>
        <div class="problem-tag" style="width: 40%">
            标签
        </div>
    </div>
</template>

<script setup lang="ts">

import { getProbelmNumber } from "@/api/problem";
import { getProbelmList } from "@/api/problem";
import { ProblemInfo } from "@/api/problem/types";
import { PageParam } from "@/api/pageParam/types";
import router from "@/router";

const searchProblem = () => {
    console.log("")
}

const clickProblem = (id: number) => {
    router.push({ path: `/problem/${id}` })
}

const handleProbelmNumber = ():number => {
    var res: number = 0;
    getProbelmNumber().then(({ data }) => {
        if (data.code == 200) {
            res = data.data;
        } else {
            console.log('获取题目数量失败' + data.msg)
        }
    });
    return res;
}

const problemNumber = handleProbelmNumber();

const handleProbelmList = (pageParam:PageParam):ProblemInfo[] => {
    var res:ProblemInfo[] = [];
    getProbelmList(pageParam).then(({ data }) => {
        if (data.code == 200) {
            res = data.data;
        } else {
            console.log('获取题目数量失败' + data.msg)
        }
    });
    return res;
}

const pageParam:PageParam = {
    page: 1,
    pageSize: 10
};

const problemList = handleProbelmList(pageParam);


// const problemList = [
//     {
//         id: 1,
//         probelm: '1.题目一',
//         difficulty: '简单',
//         passrate: '100%',
//     },
//     {
//         id: 2,
//         probelm: '2.题目二',
//         difficulty: '简单',
//         passrate: '50%',
//     },
//     {
//         id: 3,
//         probelm: '3.题目三',
//         difficulty: '简单',
//         passrate: '60%',
//     },
//     {
//         id: 4,
//         probelm: '4.题目四',
//         difficulty: '简单',
//         passrate: '12%',
//     },
//     {
//         id: 5,
//         probelm: '5.题目一',
//         difficulty: '简单',
//         passrate: '100%',
//     },
//     {
//         id: 6,
//         probelm: '6.题目二',
//         difficulty: '简单',
//         passrate: '50%',
//     },
//     {
//         id: 7,
//         probelm: '7.题目三',
//         difficulty: '简单',
//         passrate: '60%',
//     },
//     {
//         id: 8,
//         probelm: '8.题目四',
//         difficulty: '简单',
//         passrate: '12%',
//     }, {
//         id: 9,
//         probelm: '9.题目一',
//         difficulty: '简单',
//         passrate: '100%',
//     },
//     {
//         id: 10,
//         probelm: '10.题目二',
//         difficulty: '简单',
//         passrate: '50%',
//     }
// ]

const options = [
    {
        value: '选项1',
        label: '分类一',
    },
    {
        value: '选项2',
        label: '分类二',
    },
    {
        value: '选项3',
        label: '分类三',
    }
]

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