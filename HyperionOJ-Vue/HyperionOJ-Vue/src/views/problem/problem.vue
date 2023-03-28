<template>
    <el-container>
        <el-main>
            <el-menu default-active="3" class="el-menu-demo" mode="horizontal" router>
                <el-menu-item index="/problem/{{router.params.id}}/detail">
                    <el-icon>
                        <setting />
                    </el-icon>
                    <span>题目描述</span>
                </el-menu-item>
                <el-menu-item index="/problem/{{router.params.id}}/comment">
                    <el-icon>
                        <setting />
                    </el-icon>
                    <span>评论区</span>
                </el-menu-item>
                <el-menu-item index="/problem/{{router.params.id}}/submit">
                    <el-icon>
                        <setting />
                    </el-icon>
                    <span>提交记录</span>
                </el-menu-item>
            </el-menu>
            <router-view v-slot="{ Component, route }">
                <keep-alive>
                    <component :is="Component" :key="route.path" />
                </keep-alive>
            </router-view>
        </el-main>

        <el-main>
            <el-form :model="submit" label-width="120px">
                <el-row>
                    <el-select v-model="submit.codeLang" placeholder="语言类型" style="width: 20%">
                        <el-option v-for="item in codeLang" :key="item.value" :label="item.label"
                            :value="item.value"></el-option>
                    </el-select>
                </el-row>
                <el-row>
                    <el-input v-model="submit.codeBody" type="textarea" />
                </el-row>
                <el-row>
                    <el-button @click="handleSubmit">
                        提交
                    </el-button>
                </el-row>
            </el-form>

        </el-main>
    </el-container>
</template>

<script setup lang="ts">

const handleSubmit = () => {
    console.log(submit);
}

const codeLang = [
    {
        id: 1,
        label: "Java",
        value: "java"
    },
    {
        id: 2,
        label: "Python3",
        value: "python"
    },
    {
        id: 3,
        label: "C++",
        value: "c++"
    }
]

const problem = {
    problemId: '',
    runTime: "1000",
    runMemory: "128",
}

const submit = {
    codeLang: '',
    codeBody: '',
    problemId: problem.problemId,

}

</script>

<style scoped></style>