<template>
  <div class="relative overflow-x-auto">
    <table
      class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 mb-5"
    >
      <thead
        class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
      >
        <tr>
          <th scope="col" class="px-6 py-3">STT</th>
          <th scope="col" class="px-6 py-3">Mã Sinh Viên</th>
          <th scope="col" class="px-6 py-3">Họ Tên</th>
          <th scope="col" class="px-6 py-3">Khóa Học</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="(item, index) in props.students"
          :key="item.id"
          class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
        >
          <td class="px-6 py-4">{{ index + 1 }}</td>
          <td class="px-6 py-4">{{ item.studentCode }}</td>
          <td class="px-6 py-4">{{ item.fullName }}</td>
          <td class="px-6 py-4">{{ item.courseName }}</td>
        </tr>
      </tbody>
    </table>
    <nav
      aria-label="Page navigation example"
      class="flex justify-center items-center"
    >
      <ul class="flex items-center -space-x-px h-8 text-sm">
        <li>
          <a
            @click="onPrevious"
            class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
          >
            <span class="sr-only">Previous</span>
            <svg
              class="w-2.5 h-2.5 rtl:rotate-180"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 6 10"
            >
              <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5 1 1 5l4 4"
              />
            </svg>
          </a>
        </li>
        <li v-for="(item, index) in props.studentFilter.totalPage" :key="index">
          <a
            :class="[
              'flex items-center justify-center px-3 h-8 leading-tight text-gray-500 border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white',
              index === props.studentFilter.page
                ? 'bg-blue-500 text-white'
                : '',
            ]"
            >{{ item }}</a
          >
        </li>

        <li>
          <a
            @click="onNext"
            class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
          >
            <span class="sr-only">Next</span>
            <svg
              class="w-2.5 h-2.5 rtl:rotate-180"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 6 10"
            >
              <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="m1 9 4-4-4-4"
              />
            </svg>
          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>
<script lang="ts" setup>
import type { StudentResponse } from "@/types/Account";
import type { StudentFilter } from "@/types/Student";
import { ref } from "vue";

interface Props {
  students: StudentResponse[];
  studentFilter: StudentFilter;
}

const props = defineProps<Props>();
const emits = defineEmits(["onNext", "onPrevious"]);

const onNext = () => {
  emits("onNext");
};

const onPrevious = () => {
  emits("onPrevious");
};
</script>
