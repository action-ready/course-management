<script setup lang="ts">
import Dialog from "primevue/dialog";
import { read, utils, writeFileXLSX } from "xlsx";
import { onMounted, ref } from "vue";
import { watch } from "fs";
import { useRoute } from "vue-router";
import ClazzService from "@/services/ClazzService";
import { useToast } from "primevue/usetoast";
const toast = useToast();
const visible = ref(false);
const students = ref([]);
const studentCode = ref<string[]>([]);
const route = useRoute();
const errorResponse = ref("");

const emits = defineEmits(["getClazzById"]);
const onchangeFile = (event) => {
  const file = event.target.files[0];
  const reader = new FileReader();

  reader.onload = (e) => {
    const data = new Uint8Array(e.target.result);
    const workbook = read(data, { type: "array" });
    const worksheet = workbook.Sheets[workbook.SheetNames[0]];
    const jsonData = utils.sheet_to_json(worksheet, { header: 1 });

    students.value = jsonData.slice(1);
    studentCode.value = jsonData.slice(1).map((student: any) => student[2]);
  };

  reader.readAsArrayBuffer(file);
};

const onSubmit = async () => {
  const datas = {
    studentCode: Object.values(studentCode.value),
  };
  try {
    const res = await ClazzService.createImportData(datas, +route.params.id);
    toast.add({
      severity: "success",
      summary: "Success",
      detail: "Thêm Mới Thành Công",
      life: 3000,
    });
    resetData();
    visible.value = !visible.value;
    emits("getClazzById");
  } catch (error) {
    errorResponse.value = error.response.data.message;
  }
};

const resetData = () => {
  students.value = [];
  errorResponse.value = "";
};
</script>
<template>
  <div class="card flex justify-content-center">
    <button
      @click="visible = true"
      class="bg-blue-500 px-4 rounded-lg py-2 me-3"
      v-tooltip.top="'Thêm Sinh Viên'"
    >
      <i class="fa-solid fa-file-arrow-up text-3xl text-white"></i>
    </button>
    <Dialog
      v-model:visible="visible"
      modal
      header="Thêm Sinh Viên"
      style="width: 100vh; margin-top: -5px"
      :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
    >
      <h3 for="">Import Student</h3>
      <input
        accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
        class="block mb-2 text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 p-2 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400"
        id="file_input"
        type="file"
        @change="onchangeFile"
      />
      <h3 class="text-red-500" v-if="errorResponse">{{ errorResponse }}</h3>
      <a
        href="http://localhost:8080/api/v1/files/excel/templates/Import"
        class="underline text-blue-500 my-5"
        >Download template
      </a>
      <div class="" v-if="studentCode.length > 0">
        <button
          @click="onSubmit"
          class="bg-blue-500 ms-3 px-5 py-2 float-end hover:bg-blue-700 rounded-lg text-white"
        >
          Upload
        </button>
        <button
          @click="resetData"
          class="bg-slate-500 px-5 py-2 float-end hover:bg-slate-700 rounded-lg text-white"
        >
          Reset
        </button>
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
              <th scope="col" class="px-6 py-3">Mã Sinh Viên</th>
              <th scope="col" class="px-6 py-3">Họ Tên</th>
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
              <td class="px-6 py-4">{{ item[2] }}</td>
              <td class="px-6 py-4">{{ item[1] }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </Dialog>
  </div>
</template>
