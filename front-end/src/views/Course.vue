<script lang="ts" setup>
import ListCourse from "@/components/courses/ListCourse.vue";
import ModalCreateCourse from "../components/courses/ModalCreateCourse.vue";
import MainLayout from "../components/layout/MainLayout.vue";
import { ref, watchEffect } from "vue";
import CourseService from "@/services/CourseService";
import type { CourseResponse } from "@/types/Course";

const value = ref<string>("");
const courses = ref<CourseResponse[]>([]);

const onSearch = (searchValue: string) => {};

const fetchAll = async () => {
  const { data } = await CourseService.getAll();
  console.log(`<<<<<  data >>>>>`, data);
  courses.value = data.content;
};

watchEffect(() => {
  fetchAll();
});
</script>
<template>
  <MainLayout>
    <h3 class="text-3xl">Quản Lý Khóa Học</h3>
    <div class="flex justify-between my-5">
      <div class="w-96 flex items-center">
        <a-input-search
          v-model:value="value"
          placeholder="Search..."
          size="large"
          @search="onSearch"
        >
          <template #enterButton>
            <a-button class="bg-blue-500" style="color: white">Search</a-button>
          </template>
        </a-input-search>
      </div>
      <ModalCreateCourse @fetchAll="fetchAll" />
    </div>
    <ListCourse :courses="courses" />
  </MainLayout>
</template>
