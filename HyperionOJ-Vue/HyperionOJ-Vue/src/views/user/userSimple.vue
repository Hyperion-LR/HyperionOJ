<template>
    <div>
        <el-avatar shape="square" :size="30" :src=user.avatar />
    </div>
</template>

<script setup lang="ts">
const user = {
    userId: '15570357290',
    avatar: 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
    username: '冰箱的主人LR',
    phone: '15570357290',
    mail: 'Hyperion_LR@foxmail.com',
    createTime: '2023-1-25 15:15:15',
    lastLoginTime: '2023-3-25 15:15:15',
    problemAcNumber: '3',
    problemSubmitAcNumber: '2',
    problemSubmitNumber: '1',
    jobNumber: '2',
    cpuUsage: '2',
    memUsage: '3072'
}
</script>

<style scoped>

</style>