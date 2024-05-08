<template>
  <MainLayout>
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

<script setup lang="ts">
import Card from "primevue/card";

import Panel from "primevue/panel";
import type { ClazzResponseGrades } from "@/types/Clazz";
import MainLayout from "../layout/MainLayout.vue";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import ClazzService from "@/services/ClazzService";
const route = useRoute();
const clazzs = ref<ClazzResponseGrades>();
const getClazzById = async () => {
  const { data } = await ClazzService.getClazzByTeacherId(+route.params.id);
  clazzs.value = data;
  console.log(`<<<<<  data >>>>>`, data);
};

onMounted(() => {
  getClazzById();
});
</script>
