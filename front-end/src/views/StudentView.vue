<script setup lang="ts">
import ListStudent from "@/components/students/ListStudent.vue";
import ModalAddAstudent from "@/components/students/ModalAddAstudent.vue";
import StudentService from "@/services/StudentService";
import type { StudentResponse } from "@/types/Account";
import type { studentFilter } from "@/types/Student";

import Dialog from "primevue/dialog";
import { onMounted, reactive, ref, watch } from "vue";

const visible = ref(false);
const value = ref<string>("");
const students = ref<StudentResponse[]>([]);
const studentFilter = reactive<studentFilter>({
  page: 0,
  studentCode: "",
  totalPage: 0,
});

const onSearch = (searchValue: string) => {
  studentFilter.page = 0;
  studentFilter.studentCode = searchValue;
};

const onPrevious = () => {
  if (studentFilter.page > 0) {
    studentFilter.page = studentFilter.page - 1;
  }
};

const onNext = () => {
  if (studentFilter.page + 1 < studentFilter.totalPage) {
    studentFilter.page = studentFilter.page + 1;
  }
};

const fetchStudent = async () => {
  const { data } = await StudentService.getAll(studentFilter);
  studentFilter.totalPage = data.totalPages;
  studentFilter.page = data.number;
  students.value = data.content;
};

onMounted(() => {
  fetchStudent();
});

watch(studentFilter, () => {
  fetchStudent();
});
</script>

<template>
  <div class="flex justify-between items-center mb-5">
    <a-input-search
      v-model:value="value"
      placeholder="Search..."
      size="large"
      class="w-1/2"
      @search="onSearch"
    >
      <template #enterButton>
        <a-button class="bg-blue-500" style="color: white">Search</a-button>
      </template>
    </a-input-search>
    <ModalAddAstudent @fetchStudent="fetchStudent" />
  </div>
  <ListStudent
    :students="students"
    :studentFilter="studentFilter"
    @onPrevious="onPrevious"
    @onNext="onNext"
  />
</template>
