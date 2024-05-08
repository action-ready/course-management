<script setup lang="ts">
import ListStudent from "@/components/students/ListStudent.vue";
import ListTeacher from "@/components/teachers/ListTeacher.vue";
import ModalCreateTeacher from "@/components/teachers/ModalCreateTeacher.vue";
import StudentService from "@/services/StudentService";
import TeacherService from "@/services/TeacherService";

import type { TeacherFilter, TeacherResponses } from "@/types/Teacher";

import Dialog from "primevue/dialog";
import { onMounted, reactive, ref, watch } from "vue";

const visible = ref(false);
const value = ref<string>("");
const teachers = ref<TeacherResponses[]>([]);
const teacherFilter = reactive<TeacherFilter>({
  page: 0,

  totalPage: 0,
});

const onSearch = (searchValue: string) => {
  teacherFilter.page = 0;
};

const onPrevious = () => {
  if (teacherFilter.page > 0) {
    teacherFilter.page = teacherFilter.page - 1;
  }
};

const onNext = () => {
  if (teacherFilter.page + 1 < teacherFilter.totalPage) {
    teacherFilter.page = teacherFilter.page + 1;
  }
};

const fetchTeachers = async () => {
  const { data } = await TeacherService.getAll(teacherFilter);

  teachers.value = data.content;
  teacherFilter.totalPage = data.totalPages;
  teacherFilter.page = data.number;
};

onMounted(() => {
  fetchTeachers();
});

watch(teacherFilter, () => {
  fetchTeachers();
});
</script>

<template>
  <div class="float-end items-center">
    <ModalCreateTeacher @fetchTeachers="fetchTeachers" />
  </div>
  <div class="mt-10">
    <ListTeacher
      :students="teachers"
      :studentFilter="teacherFilter"
      @onPrevious="onPrevious"
      @onNext="onNext"
    />
  </div>
</template>
