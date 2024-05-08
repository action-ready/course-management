<template>
  <div class="grid grid-cols-3 gap-x-2 gap-y-4">
    <Card
      style="width: 25rem; overflow: hidden"
      class="border-spacing-2"
      v-for="item in data"
      :key="item.id"
    >
      <template #header>
        <RouterLink :to="`/course/${item.id}`">
          <img
            alt="user header"
            style="height: 30vh"
            class="object-cover w-full"
            :src="item.imageUrl"
        /></RouterLink>
      </template>
      <template #title>
        <h3 class="h-10">{{ item.name }}</h3>
      </template>

      <template #content>
        <div>
          <!-- <p>Ngày bắt đầu: {{ dayjs(item.startDate).format("DD/MM/YYYY") }}</p>
          <p>Ngày kết thúc: {{ dayjs(item.endDate).format("DD/MM/YYYY") }}</p> -->
          <p>Thòi Gian: {{ item.studyTime }}</p>
          <span
            >Ngôn ngữ: <span class="font-bold"> {{ item.language }}</span></span
          >

          <p
            class="mt-5 bg-blue-500 w-1/3 text-center p-2 text-white rounded-xl"
          >
            {{ item.price }} <span class="underline">đ</span>
          </p>
        </div>
      </template>
    </Card>
  </div>
</template>
<script lang="ts" setup>
import Card from "primevue/card";

import type { CourseResponse } from "@/types/Course";
import dayjs from "dayjs";
import { onMounted, ref, watch, watchEffect } from "vue";

const data = ref<CourseResponse[]>([]);
interface Props {
  courses: CourseResponse[];
}

const props = defineProps<Props>();

watch(props, () => {
  data.value = props.courses;
});

onMounted(() => {
  data.value = props.courses;
});
</script>
