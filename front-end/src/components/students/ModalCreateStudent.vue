<script setup lang="ts">
import AccountService from "@/services/AccountService";
import ClazzService from "@/services/ClazzService";
import type { StudentResponse } from "@/types/Account";
import Dialog from "primevue/dialog";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import { useToast } from "primevue/usetoast";

import Toast from "primevue/toast";
import StudentService from "@/services/StudentService";

const toast = useToast();
const selectedStudents = ref<string[]>([]);
const visible = ref(false);
const students = ref<StudentResponse[]>([]);
const route = useRoute();
const emits = defineEmits(["getClazzById"]);
const props = defineProps(["courseId"]);

const fetchStudents = async () => {
  if (props.courseId) {
    const { data } = await StudentService.getAllByCourseId(props.courseId);
    console.log(`<<<<<  data >>>>>`, data);
    students.value = data;
  }
};

const onSubmit = async () => {
  const datas = {
    studentCode: Object.values(selectedStudents.value),
  };
  const res = await ClazzService.createImportData(datas, +route.params.id);

  toast.add({
    severity: "success",
    summary: "Success",
    detail: "Thêm Mới Thành Công",
    life: 3000,
  });
  selectedStudents.value = [];
  emits("getClazzById");
  visible.value = !visible.value;
};
watch(props, () => {
  fetchStudents();
});
</script>

<template>
  <div class="card flex justify-content-center">
    <button
      v-tooltip.top="'Thêm Sinh Viên'"
      label="Show"
      class="bg-blue-500 px-4 text-white rounded-lg"
      @click="visible = true"
    >
      <i class="fa-solid fa-plus text-2xl"></i>
    </button>
    <Toast />
    <Dialog
      v-model:visible="visible"
      modal
      header="Thêm Sinh Viên"
      :style="{ width: '50vw' }"
      :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
    >
      <button
        @click="onSubmit"
        class="float-end bg-blue-500 px-2 py-2 text-white rounded-lg hover:bg-blue-700"
      >
        Thêm Mới
      </button>
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
              <th scope="col" class="px-6 py-3">Checkbox</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(item, index) in students"
              :key="item.id"
              class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
            >
              <th
                scope="row"
                class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
              >
                {{ index + 1 }}
              </th>
              <td class="px-6 py-4">{{ item.studentCode }}</td>
              <td class="px-6 py-4">{{ item.fullName }}</td>
              <td class="px-6 py-4">
                <input
                  v-model="selectedStudents"
                  class="ms-10"
                  type="checkbox"
                  name=""
                  id=""
                  :value="item.studentCode"
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </Dialog>
  </div>
</template>
