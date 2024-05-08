<template>
  <div>
    <h1 class="text-2xl my-3">
      Tên Khóa Học: {{ studentDetails?.courseName }}
    </h1>
    <h3 class="text-xl">Bảng Điểm</h3>
    <div class="relative overflow-x-auto">
      <table
        class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400"
      >
        <thead
          class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
        >
          <tr>
            <th scope="col" class="px-6 py-3">STT</th>
            <th scope="col" class="px-6 py-3">Mã Lớp</th>
            <th scope="col" class="px-6 py-3">Tên Môn</th>
            <th scope="col" class="px-6 py-3">Kỳ Học</th>
            <th scope="col" class="px-6 py-3">Điểm</th>
            <!-- <th scope="col" class="px-6 py-3">Trạng Thái</th> -->
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(item, index) in studentDetails?.grades"
            :key="item.id"
            class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
          >
            <th
              scope="row"
              class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
            >
              {{ index + 1 }}
            </th>
            <td class="px-6 py-4">{{ item.clazzClazzCode }}</td>
            <td class="px-6 py-4">{{ item.scheduleName }}</td>
            <td class="px-6 py-4">{{ item.scheduleSemesterName }}</td>
            <td class="px-6 py-4">{{ item.grade }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuth } from "@/auth";
import StudentService from "@/services/StudentService";
import type { StudentResponseDetails } from "@/types/Student";
import { onMounted, ref } from "vue";

const studentDetails = ref<StudentResponseDetails>();
const auth = useAuth();

const fetchStudent = async () => {
  if (auth.state.studentId) {
    const { data } = await StudentService.getStudentById(auth.state.studentId);
    studentDetails.value = data;
  }
};

onMounted(() => {
  fetchStudent();
});
</script>

<style scoped></style>
