<script lang="ts" setup>
import Dialog from "primevue/dialog";
import Button from "primevue/button";

import Toast from "primevue/toast";
import { useToast } from "primevue/usetoast";

import { onMounted, reactive, ref } from "vue";
import CourseService from "@/services/CourseService";
import type { CourseResponseSelect } from "@/types/Course";
import AccountService from "@/services/AccountService";
import type { TeacherResponse } from "@/types/Account";
import ClazzService from "@/services/ClazzService";

interface FormState {
  teacherId: number | string;
  courseId: number | string;
  startDate: string;
  endDate: string;
}

const formState = reactive<FormState>({
  teacherId: "",
  courseId: "",
  startDate: "",
  endDate: "",
});

const emits = defineEmits(["fetchClazz"]);
const visible = ref(false);
const courses = ref<CourseResponseSelect[]>([]);
const teachers = ref<TeacherResponse[]>([]);
const toast = useToast();
const validDate = reactive({
  errStartDate: "",
  errEndDate: "",
});

const onFinish = async (values: any) => {
  const { startDate, endDate } = values;

  const start = new Date(startDate);
  start.setHours(0, 0, 0, 0);
  const end = new Date(endDate);

  const currentDate = new Date();
  currentDate.setHours(0, 0, 0, 0);

  if (start < currentDate) {
    validDate.errStartDate =
      "Ngày bắt đầu phải lớn hơn hoặc bằng ngày hiện tại";
    return;
  } else {
    validDate.errStartDate = "";
  }

  if (end <= start) {
    validDate.errEndDate = "Ngày kết thúc phải lớn hơn ngày bắt đầu";
    return;
  } else {
    validDate.errEndDate = "";
  }
  const res = await ClazzService.create(values);
  toast.add({
    severity: "success",
    summary: "Success",
    detail: "Thêm Mới Thành Công",
    life: 3000,
  });
  emits("fetchClazz");
  visible.value = !visible.value;
};

const onFinishFailed = (errorInfo: any) => {
  console.log(`<<<<< errorInfo  >>>>>`, errorInfo);
};

const fetchCourse = async () => {
  const { data } = await CourseService.getAllSelect();

  courses.value = data;
};
const fetchTeacher = async () => {
  const { data } = await AccountService.getAllTeacher();

  teachers.value = data;
};

onMounted(() => {
  fetchCourse();
  fetchTeacher();
});
</script>

<template>
  <div class="card flex justify-content-center">
    <Button
      class="bg-blue-500 px-5 py-3 text-white"
      label=""
      v-tooltip.top="'Tạo Lớp '"
      @click="visible = true"
    >
      <i class="fa-solid fa-plus"></i>
    </Button>
    <Dialog
      v-model:visible="visible"
      modal
      header="Thêm Mới Lớp Học"
      :style="{ width: '50vw' }"
      :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
    >
      <Toast />
      <div class="">
        <a-form
          :model="formState"
          name="form"
          :label-col="{ span: 24 }"
          :wrapper-col="{ span: 24 }"
          autocomplete="off"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
        >
          <div class="grid grid-cols-2 justify-between ms-7">
            <a-form-item
              label="Giảng Viên "
              name="teacherId"
              :rules="[
                { required: true, message: 'Vui lòng chọn giảng viên!' },
              ]"
            >
              <select
                v-model="formState.teacherId"
                id="countries"
                class="border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-72 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              >
                <option selected disabled value="">Chọn Giảng Viên</option>
                <option
                  :value="item.id"
                  v-for="item in teachers"
                  :key="item.id"
                >
                  {{ item.fullName }}
                </option>
              </select>
            </a-form-item>

            <a-form-item
              label="Khóa Học "
              name="courseId"
              :rules="[{ required: true, message: 'Vui lòng chọn khóa học!' }]"
            >
              <select
                v-model="formState.courseId"
                id="countries"
                class="border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-72 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              >
                <option selected disabled value="">Chọn Khóa Học</option>
                <option :value="item.id" v-for="item in courses" :key="item.id">
                  {{ item.name }}
                </option>
              </select>
            </a-form-item>

            <a-form-item
              label="Ngày Bắt Đầu "
              name="startDate"
              :rules="[
                { required: true, message: 'Vui lòng chọn ngày bắt đầu!' },
              ]"
            >
              <div class="relative max-w-sm">
                <div
                  class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none"
                >
                  <svg
                    class="w-4 text-gray-500 dark:text-gray-400"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                  >
                    <path
                      d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z"
                    />
                  </svg>
                </div>
                <input
                  datepicker
                  v-model="formState.startDate"
                  type="date"
                  class="border border-gray-200 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-72 ps-10 p-1.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="Select date"
                />
                <span v-if="validDate.errStartDate" class="text-red-500">{{
                  validDate.errStartDate
                }}</span>
              </div>
            </a-form-item>
            <a-form-item
              label="Ngày Kết Thúc "
              name="endDate"
              :rules="[
                { required: true, message: 'Vui lòng chọn ngày kết thúc!' },
              ]"
            >
              <div class="relative max-w-sm">
                <div
                  class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none"
                >
                  <svg
                    class="w-4 text-gray-500 dark:text-gray-400"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                  >
                    <path
                      d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z"
                    />
                  </svg>
                </div>
                <input
                  datepicker
                  v-model="formState.endDate"
                  type="date"
                  class="border border-gray-200 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-72 ps-10 p-1.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="Select date"
                />
                <span v-if="validDate.errEndDate" class="text-red-500">{{
                  validDate.errEndDate
                }}</span>
              </div>
            </a-form-item>
          </div>

          <a-form-item class="float-end">
            <a-button
              type="primary"
              class="bg-blue-500 float-end me-4"
              html-type="submit"
              >Tạo Mới</a-button
            >
          </a-form-item>
        </a-form>
      </div>
    </Dialog>
  </div>
</template>
