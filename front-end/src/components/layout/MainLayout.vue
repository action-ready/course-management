<script lang="ts" setup>
import { ref } from "vue";
import {
  UserOutlined,
  VideoCameraOutlined,
  UploadOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
} from "@ant-design/icons-vue";
import { RouterLink, useRouter } from "vue-router";
import { Role, useAuth } from "@/auth";
const selectedKeys = ref<string[]>(["1"]);
const collapsed = ref<boolean>(false);
const router = useRouter();
const { state } = useAuth();

const logout = () => {
  const auth = useAuth();
  auth.logout();
  localStorage.removeItem("token");
  localStorage.removeItem("refreshToken");
  router.push("/login");
};
</script>
<template>
  <a-layout style="min-height: 800px">
    <a-layout-sider v-model:collapsed="collapsed" :trigger="null" collapsible>
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Vue.js_Logo_2.svg/1200px-Vue.js_Logo_2.svg.png"
        alt=""
        style="width: 50%"
        class="ms-10 p-5"
      />
      <div class="logo" />
      <a-menu v-model:selectedKeys="selectedKeys" theme="light " mode="inline">
        <div class="" v-if="state.role == Role.Admin">
          <a-menu-item key="1">
            <div class="flex items-center gap-2">
              <user-outlined />
              <RouterLink to="/">Quản Lý Khóa Học</RouterLink>
            </div>
          </a-menu-item>
          <a-menu-item key="2">
            <div class="flex items-center gap-2">
              <video-camera-outlined />
              <RouterLink to="/clazz">Quản Lý Lớp Học</RouterLink>
            </div>
          </a-menu-item>

          <a-menu-item key="3">
            <div class="flex items-center gap-2">
              <upload-outlined />
              <RouterLink to="/account"> Quản Lý Tài Khoản</RouterLink>
            </div>
          </a-menu-item>
        </div>
        <a-menu-item key="3" v-if="state.role == Role.Teacher">
          <div class="flex items-center gap-2">
            <upload-outlined />
            <RouterLink to="/grades"> Quản Lý Điểm</RouterLink>
          </div>
        </a-menu-item>

        <a-menu-item
          key="3"
          v-if="state.role != Role.Teacher && state.role != Role.Admin"
        >
          <div class="flex items-center gap-2">
            <upload-outlined />
            <RouterLink to="/student"> Quản Lý Điểm</RouterLink>
          </div>
        </a-menu-item>

        <a-menu-item key="3">
          <div class="flex items-center gap-2">
            <upload-outlined />
            <span @click="logout" to="/grades"> Logout</span>
          </div>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header style="background: #fff; padding: 0">
        <menu-unfold-outlined
          v-if="collapsed"
          class="trigger"
          @click="() => (collapsed = !collapsed)"
        />
        <menu-fold-outlined
          v-else
          class="trigger"
          @click="() => (collapsed = !collapsed)"
        />
      </a-layout-header>
      <a-layout-content
        :style="{
          margin: '24px 16px',
          padding: '24px',
          background: '#fff',
          minHeight: '280px',
        }"
      >
        <slot />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<style>
.ant-layout-sider-children {
  background: white;
}
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.3);
  margin: 16px;
}

.site-layout .site-layout-background {
  background: white;
}
</style>
