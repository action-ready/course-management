<script setup lang="ts">
import Dialog from "primevue/dialog";
import * as XLSX from "xlsx";

import Toast from "primevue/toast";

import { ref } from "vue";
import GradesService from "@/services/GradesService";
import { useToast } from "primevue/usetoast";
import { useRoute } from "vue-router";
import ModalCreateGrades from "./ModalCreateGrades.vue";
const props = defineProps(["selectedSchedule"]);
const toast = useToast();
const route = useRoute();
const emits = defineEmits([
  "fetchStudentGrades",
  "getScheduleById",
  "fetchSchedule",
]);
const visible = ref(false);
const students = ref([]);
let formattedData: any = null;
const onchangeFile = (event) => {
  const file = event.target.files[0];
  const reader = new FileReader();

  reader.onload = (e) => {
    const data = new Uint8Array(e.target.result);
    const workbook = XLSX.read(data, { type: "array" });
    const worksheet = workbook.Sheets[workbook.SheetNames[0]];
    const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });
    students.value = jsonData.slice(1);
    formattedData = jsonData.map((row) => {
      return {
        grade: row[3],
        clazzId: +route.params.id,
        studentId: row[1],
        scheduleId: props.selectedSchedule.id,
      };
    });
    console.log(`<<<<<  formattedData >>>>>`, formattedData);
  };
  reader.readAsArrayBuffer(file);
};

const onSubmit = async () => {
  const data = formattedData.slice(1);

  const res = await GradesService.create(data);

  toast.add({
    severity: "success",
    summary: "Success",
    detail: "Thêm Mới Thành Công",
    life: 3000,
  });
  emits("fetchStudentGrades");
  emits("getScheduleById");
  emits("fetchSchedule");
  visible.value = !visible.value;
};
</script>

<template>
  <div class="card flex justify-content-center">
    <Toast />
    <button
      v-tooltip.top="'Import Điểm '"
      label="Show"
      @click="visible = true"
      class="bg-blue-500 px-4 py-2 text-white rounded-lg hover:bg-blue-700"
    >
      <i class="fa-solid fa-file-arrow-up"></i>
    </button>
    <Dialog
      v-model:visible="visible"
      modal
      header="Cập Nhật Điểm Sinh Viên"
      :style="{ width: '50vw' }"
      :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
    >
      <div class="flex justify-between items-center">
        <div class="flex items-center gap-x-3">
          <h3 for="">Import Điểm</h3>
          <input
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
            class="block mb-2 text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 p-2 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400"
            id="file_input"
            type="file"
            @change="onchangeFile"
          />
        </div>
        <div class="">
          <button
            @click="onSubmit"
            v-tooltip.top="'Cập nhật điểm '"
            class="bg-blue-500 px-3 py-2 w-24 text-white rounded-lg font-bold hover:bg-blue-700 float-end"
          >
            <i class="fa-solid fa-file-arrow-up"></i>
          </button>
        </div>
      </div>
      <div
        v-if="students.length > 0"
        class="relative overflow-x-auto mt-10"
        style="overflow-y: scroll; max-height: 300px"
      >
        <h3 class="text-2xl">Danh Sách Sinh Viên</h3>
        <table
          class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400"
        >
          <thead
            style="position: sticky; top: 0"
            class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
          >
            <tr>
              <th scope="col" class="px-6 py-3">#</th>
              <th scope="col" class="px-6 py-3">Mã Lớp</th>
              <th scope="col" class="px-6 py-3">Họ Tên</th>
              <th scope="col" class="px-6 py-3">Điểm</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(item, index) in students"
              :key="item"
              class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
            >
              <th
                scope="row"
                class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
              >
                {{ index + 1 }}
              </th>
              <td class="px-6 py-4">{{ item[0] }}</td>
              <td class="px-6 py-4">{{ item[2] }}</td>
              <td class="px-6 py-4">{{ item[3] }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </Dialog>
  </div>
</template>
