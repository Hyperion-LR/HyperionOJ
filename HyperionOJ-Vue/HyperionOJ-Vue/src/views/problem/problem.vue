<template>
    <el-container>
        <el-main>
            <el-menu :default-active="route.path" class="el-menu-demo" mode="horizontal" router>
                <el-menu-item :index="`/problem/${route.params.id}/detail`">
                    <el-icon>
                        <setting />
                    </el-icon>
                    <span>题目描述</span>
                </el-menu-item>
                <el-menu-item :index="`/problem/${route.params.id}/comment`">
                    <el-icon>
                        <setting />
                    </el-icon>
                    <span>评论区</span>
                </el-menu-item>
                <el-menu-item :index="`/problem/${route.params.id}/submit`">
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
            <el-form :model="submitInfo" label-width="120px">
                <el-row>
                    <el-select v-model="submitInfo.codeLang" placeholder="语言类型" style="width: 20%">
                        <el-option v-for="item in codeLang" :key="item.value" :label="item.label"
                            :value="item.value"></el-option>
                    </el-select>
                </el-row>
                <el-row>
                    <el-input v-model="submitInfo.codeBody" type="textarea" />
                </el-row>
                <el-row>
                    <el-button @click="handleSubmit">
                        提交
                    </el-button>
                </el-row>
                <el-row>
                    <el-card  class="card-box">
                        运行结果: {{ submitResultInfo.verdict }}
                    </el-card>
                </el-row>
            </el-form>

        </el-main>
    </el-container>
</template>

<script setup lang="ts">
import { submit } from '@/api/problem';
import { SubmitInfo, SubmitResultInfo } from '@/api/problem/types';

const route = useRoute();

console.log(route);


const data = reactive({
    submitInfo: {} as SubmitInfo,
    submitResultInfo: {} as SubmitResultInfo,
})
const { submitInfo, submitResultInfo } = toRefs(data);

const handleSubmit = () => {
    submitInfo.value.problemId = route.params.id as string;
    submit(submitInfo.value).then(({ data }) => {
        if (data.code == 200) {
            submitResultInfo.value = data.data;
            console.log(submitResultInfo.value)
        } else {
            console.log("提交失败" + data.msg)
        }
    });
}

const handleInit = () => {

}

onMounted(() => {
    handleInit();
})

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
        value: "cpp"
    }
]

</script>

<style scoped></style>