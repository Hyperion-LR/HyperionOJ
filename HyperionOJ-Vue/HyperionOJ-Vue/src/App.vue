<template>
  <div class="app-wrapper">
    <el-container>

      <el-dialog v-model="loginVisible" title="登录">
        <el-form :model="loginParam">
          <el-form-item label="邮箱或手机号码">
            <el-input v-model="loginParam.account" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="loginParam.password" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="loginVisible = false; regiestVisible = true">注册</el-button>
            <el-button type="primary" @click="handleLogin">
              登录
            </el-button>
          </span>
        </template>
      </el-dialog>

      <el-dialog v-model="regiestVisible" title="注册">
        <el-form :model="regiestParam">
          <el-form-item label="手机号码">
            <el-input v-model="regiestParam.account" />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="regiestParam.account" />
          </el-form-item>
          <el-form-item label="验证码">
            <el-input v-model="regiestParam.code" />
            <el-button @click="getVarCode">获取验证码</el-button>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="regiestParam.password" type="password" />
          </el-form-item>
          <el-form-item label="重复输入密码">
            <el-input v-model="regiestParam.password" type="password" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="regiestVisible = false; loginVisible = true">登录</el-button>
            <el-button type="primary" @click="regiest">
              注册
            </el-button>
          </span>
        </template>
      </el-dialog>

      <el-header class="header">
        <span @click="router.push({ path: '/' })" style="cursor: pointer;">HyperionOJ</span>
        <user-avatar @click="userInfo" style="cursor: pointer;" class="user-avatar"></user-avatar>
      </el-header>
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

import router from "@/router";
import { login } from "@/api/login";
import { setToken } from "@/utils/token";
import useStore from "@/store";

const { user } = useStore();

const data = reactive({
  loginVisible: false,
  regiestVisible: false,
  loginParam: {
    account: '',
    password: ''
  },
  regiestParam: {
    account: '',
    password: '',
    mail: '',
    code: ''
  },
});
const {
  loginVisible,
  regiestVisible,
  loginParam,
  regiestParam,
} = toRefs(data);

const userInfo = () => {
  const userId = user.id;
  console.log(userId);
  if (userId != undefined && userId != '') {
    router.push({ path: `/user/${userId}` })
  } else {
    loginVisible.value = true;
  }

}

const handleLogin = () => {
  login(loginParam.value).then(({ data }) => {
    if (data.code == 200) {
      setToken(data.data);
      user.GetUserInfo(loginParam.value.account);
      loginVisible.value = false;
      const userId = user.id;
      router.push({ path: `/user/${userId}` })
    } else {
      console.log('登陆失败')
    }
  })
}

const regiest = () => {
  console.log('注册')
}

const getVarCode = () => {
  console.log('获取验证码')
}

</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
}

.user-avatar {
  display: flex;
}

.app-wrapper {
  position: relative;
  min-height: 100vh;
  width: 100%;
}
</style>


