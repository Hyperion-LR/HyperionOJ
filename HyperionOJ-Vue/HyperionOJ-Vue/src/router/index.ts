import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const routes: RouteRecordRaw[] = [
  // 首页两个路由
  {
    path: "/",
    component: () => import("@/views/home/home.vue"),
    redirect: "/problem",
    children: [
      {
        path: "/problem",
        name: "题目",
        component: () => import("@/views/problem/problemList.vue"),
      },
      {
        path: "/job",
        name: "任务",
        component: () => import("@/views/job/jobList.vue"),
      },
    ],
    meta: {
      title: "首页",
    },
  },
  // 题目详情路由
  {
    path: "/problem/:id",
    component: () => import("@/views/problem/problem.vue"),
    redirect: "/problem/:id/detail",
    children:[
      {
        path: "/problem/:id/detail",
        name: "题目详情",
        component: () => import("@/views/problem/problemDetail.vue"),
      },
      {
        path: "/problem/:id/comment",
        name: "题目评论",
        component: () => import("@/views/problem/problemComment.vue"),
      },
      {
        path: "/problem/:id/submit",
        name: "提交记录",
        component: () => import("@/views/problem/problemSubmit.vue"),
      },
    ],
    meta: {
      title: "题目详情",
    },
  },
  // 作业路由
  {
    path: "/job/:id",
    component: () => import("@/views/job/jobDetail.vue"),
    meta: {
      title: "任务详情",
    },
  },
  //登录页路由
  {
    path: "/login/",
    component: () => import("@/views/user/login.vue"),
    meta: {
      title: "登录",
    },
  },
  //注册页路由
  {
    path: "/regiest/",
    component: () => import("@/views/user/regiest.vue"),
    meta: {
      title: "注册",
    },
  },
  //个人详情路由
  {
    path: "/user/:id",
    component: () => import("@/views/user/user.vue"),
    meta: {
      title: "个人详情",
    },
  },
  //代码提交详情路由
  {
    path: "/submit/:id",
    component: () => import("@/views/problem/submit.vue"),
    meta: {
      title: "提交详情",
    },
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;