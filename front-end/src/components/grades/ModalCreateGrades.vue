<template>
  <div class="card flex justify-content-center">
    <Toast />
    <button
      label="Show"
      @click="visible = true"
      class="bg-blue-500 px-4 py-2 text-white rounded-lg hover:bg-blue-700"
    >
      <i class="fa-solid fa-circle-plus"></i>
    </button>
    <Dialog
      v-model:visible="visible"
      modal
      header="Cập Nhật Điểm Sinh Viên"
      :style="{ width: '50vw' }"
      :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
    >
      <button
        @click="onSubmit"
        class="bg-blue-500 text-white px-3 py-2 rounded-lg float-end hover:bg-blue-700"
      >
        Cập Nhật
      </button>
      <p v-if="error">{{ error }}</p>

      <div
        class="relative overflow-x-auto mt-10"
        style="overflow-y: scroll; max-height: 300px"
      >
        <table
          class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400"
        >
          <thead
            style="position: sticky; top: 0"
            class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
          >
            <tr>
              <th scope="col" class="px-6 py-3">STT</th>
              <th scope="col" class="px-6 py-3">Mã Sinh Viên</th>
              <th scope="col" class="px-6 py-3">Họ Tên</th>
              <th scope="col" class="px-6 py-3">Điểm</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(item, index) in studentsGrades"
              :key="item.studentId"
              class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
            >
              <th
                class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
              >
                {{ index + 1 }}
              </th>
              <td class="px-6 py-4">{{ item.studentCode }}</td>
              <td class="px-6 py-4">{{ item.fullName }}</td>

              <td class="px-6 py-4 w-2/12">
                <div class="flex gap-x-2 justify-center items-center">
                  <span>{{ item.grade }}</span>
                  <input
                    v-model="grades[index]"
                    type="number"
                    class="w-8/12 border rounded-lg flex justify-center items-center text-center"
                  />
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
import GradesService from "@/services/GradesService";
import type { StudentResponseGrades } from "@/types/Student";
import Dialog from "primevue/dialog";
import { useToast } from "primevue/usetoast";
import { onMounted, ref, watch } from "vue";

import Toast from "primevue/toast";

import { useRoute } from "vue-router";

const toast = useToast();
const studentsGrades = ref<StudentResponseGrades[]>([]);
const props = defineProps(["studentsGrades", "selectedSchedule"]);

const emits = defineEmits([
  "fetchStudentGrades",
  "getScheduleById",
  "fetchSchedule",
]);
const visible = ref(false);
const route = useRoute();
const error = ref("");
const grades = ref<number[]>([]);
const selectedSchedule = ref<number>();
onMounted(() => {
  studentsGrades.value = props.studentsGrades;
  selectedSchedule.value = props.selectedSchedule.id;
});
watch(props, () => {
  studentsGrades.value = props.studentsGrades;
});
const onSubmit = async () => {
  error.value = "";

  let data = [];
  for (let i = 0; i < studentsGrades.value.length; i++) {
    const student = studentsGrades.value[i];
    const grade = grades.value[i];
    const item = {
      id: studentsGrades.value[i].gradesId,
      grade: grade || null,
      clazzId: +route.params.id,
      studentId: student.studentId,
      scheduleId: selectedSchedule.value,
    };
    data.push(item);
  }

  const res = await GradesService.create(data);
  toast.add({
    severity: "success",
    summary: "Success",
    detail: "Thêm Mới Thành Công",
    life: 3000,
  });

  data = [];
  emits("fetchStudentGrades");
  emits("getScheduleById");
  emits("fetchSchedule");
  studentsGrades.value = [];
  visible.value = !visible.value;
};
</script>
