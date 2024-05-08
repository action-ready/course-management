<script setup lang="ts">
import MainLayout from "@/components/layout/MainLayout.vue";
import StudentView from "./StudentView.vue";

import { ref } from "vue";
import TeacherView from "./TeacherView.vue";
const activeKey = ref("1");
</script>

<template>
  <MainLayout>
    <a-tabs v-model:activeKey="activeKey">
      <a-tab-pane key="1" tab="Quản Lý Sinh Viên">
        <StudentView />
      </a-tab-pane>
      <a-tab-pane key="2" tab="Quản Lý Giảng Viên" force-render>
        <TeacherView />
      </a-tab-pane>
    </a-tabs>
  </MainLayout>
</template>
