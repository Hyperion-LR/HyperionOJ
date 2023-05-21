<template>
    <div class="app-wrapper">
        <el-container>
            <el-aside width="10vw">
                <el-menu class="el-menu-vertical-demo" router :default-active="route.path">
                    <el-menu-item index="/problem">
                        <el-icon>
                            <Notebook />
                        </el-icon>
                        <span>问题</span>
                    </el-menu-item>
                    <el-menu-item index="/job">
                        <el-icon>
                            <List/>
                        </el-icon>
                        <span>任务</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main>
                <router-view v-slot="{ Component, route }">
                    <keep-alive>
                        <component :is="Component" :key="route.path" />
                    </keep-alive>
                </router-view>
            </el-main>
        </el-container>
    </div>
</template>

<script setup lang="ts">
const route = useRoute();
</script>

<style scoped>
.app-wrapper {
    position: relative;
    min-height: 100vh;
    height: 100%;
    width: 100%;
}
</style>