<script setup lang="ts">
import ClazzService from "@/services/ClazzService";
import { onMounted, ref } from "vue";

import Card from "primevue/card";

import Panel from "primevue/panel";
import type { ClazzResponseGrades } from "@/types/Clazz";
import MainLayout from "@/components/layout/MainLayout.vue";
import { useAuth } from "@/auth";
const clazzs = ref<ClazzResponseGrades[]>([]);
const auth = useAuth();
const getClazzByTeacherId = async () => {
  if (auth.state.id) {
    const { data } = await ClazzService.getClazzByTeacherId(auth.state.id);

    clazzs.value = data;
  }
};

onMounted(() => {
  getClazzByTeacherId();
});
</script>

<template>
  <MainLayout>
    <h3 class="text-2xl">Quản Lý Điểm</h3>
    <Panel header="Lớp Học Của Tôi" class="border">
      <div class="grid grid-cols-3 gap-5">
        <Card
          v-for="item in clazzs"
          :key="item.id"
          style="width: 25rem; overflow: hidden"
        >
          <template #header>
            <RouterLink :to="'/grades/' + item.id">
              <img
                alt="user header"
                :src="item.courseImageUrl"
                class="w-full h-64"
            /></RouterLink>
          </template>
          <template #title>{{ item.courseName }}</template>
          <template #subtitle
            >Mã Lớp Học:
            <span class="text-black font-bold">{{
              item.clazzCode
            }}</span></template
          >
        </Card>
      </div>
    </Panel>
  </MainLayout>
</template>
