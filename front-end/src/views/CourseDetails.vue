<script setup lang="ts">
import MainLayout from "@/components/layout/MainLayout.vue";
import CourseService from "@/services/CourseService";

import { onMounted, onUnmounted, ref, watchEffect } from "vue";
import { useRoute } from "vue-router";
import dayjs from "dayjs";
import Dialog from "primevue/dialog";
import type { CourseResponseDetails } from "@/types/Course";
import { reactive } from "vue";

import Dropdown from "primevue/dropdown";
import Editor from "primevue/editor";
import SemesterService from "@/services/SemesterService";
import type { SemesterResponse } from "@/types/Semester";
import ScheduleService from "@/services/ScheduleService";

const visible = ref(false);
const route = useRoute();

const semesters = ref<SemesterResponse[]>([]);

const courseDetails = ref<CourseResponseDetails | undefined>();
interface FormState {
  name: string;
  description: string;
  detailsSemester: string;
  semester: number | null;
  startDate: string;
  endDate: string;
  numberOfSessions: number | null;
}

const formState = reactive<FormState>({
  name: "",
  description: "",
  detailsSemester: "",
  semester: null,
  endDate: "",
  startDate: "",
  numberOfSessions: null,
});
const onFinish = async (values: any) => {
  const res = await ScheduleService.create(values, values.semester.id);
  console.log(`<<<<<  res >>>>>`, res);
  getCourseById();
  resetForm();
  visible.value = !visible.value;
};

const onFinishFailed = (errorInfo: any) => {};

const getCourseById = async () => {
  const { data } = await CourseService.getById(+route.params.id);

  courseDetails.value = data;
};

const getSemesterByCourseId = async () => {
  const { data } = await SemesterService.getByCourseId(+route.params.id);

  semesters.value = data;
};

onMounted(() => {
  getCourseById();
  getSemesterByCourseId();
});

const onEdit = (schedule: any, item: any) => {
  console.log(`<<<<<  schedule >>>>>`, item);
  formState.name = schedule.name;

  formState.description = schedule.description;
  visible.value = !visible.value;
};

const resetForm = () => {
  (formState.description = ""),
    (formState.name = ""),
    (formState.semester = null),
    (formState.detailsSemester = "");
};
</script>
<template>
  <MainLayout>
    <h3 class="text-2xl">
      Chi Tiết Khóa Học:
      <span class="font-bold">{{ courseDetails?.name }}</span>
    </h3>
    <div class="border h-auto my-3 p-5">
      <h3 class="text-xl">Những gì bạn học được:</h3>
      <p class="ms-5 text-base" v-html="courseDetails?.description"></p>
    </div>
    <div class="">
      <button
        @click="visible = true"
        class="bg-blue-500 float-end px-4 py-2 text-white font-bold rounded-lg"
      >
        Cập nhật kỳ học
      </button>
    </div>

    <div class="grid grid-cols-2 gap-4 mt-16">
      <div
        v-for="item in courseDetails?.semesters"
        :key="item.id"
        class="border rounded-lg p-4"
      >
        <h1 class="text-2xl ms-3">{{ item.name }}</h1>
        <a-collapse v-model:activeKey="activeKey" accordion>
          <a-collapse-panel
            v-for="x in item.schedules.sort((a, b) => +a.id - +b.id)"
            :key="String(x.id)"
            :header="x.name"
          >
            <div class="flex justify-between">
              <!-- <h3>
                Ngày Bắt Đầu: {{ dayjs(x.startDate).format("DD/MM/YYYY") }}
              </h3>
              <h3>
                Ngày Kết Thúc: {{ dayjs(x.endDate).format("DD/MM/YYYY") }}
              </h3> -->
              <h3>Số lượng buổi: {{ x.numberOfSessions }}</h3>
            </div>
            <span v-html="x.description"></span>
            <!-- <p
              @click="onEdit(x, item)"
              class="bg-slate-500 flex items-center py-2 justify-center rounded-lg text-white w-full"
            >
              <i class="fa-solid fa-pen-to-square text-xl"></i>
            </p> -->
          </a-collapse-panel>
        </a-collapse>
      </div>
    </div>

    <Dialog
      v-model:visible="visible"
      modal
      header="Thêm Chi Tiết"
      class="ms-20"
      style="width: 1000px; margin-top: -50px"
      :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
    >
      <a-form
        :model="formState"
        name="basic"
        :label-col="{ span: 2 }"
        autocomplete="off"
        @finish="onFinish"
        @finishFailed="onFinishFailed"
      >
        <div class="grid grid-cols-2 gap-x-5 items-center">
          <a-form-item
            name="name"
            :rules="[{ required: true, message: 'Vui lòng nhập tên môn học!' }]"
          >
            <label for="" class="font-bold">Tên Môn Học</label>
            <a-input
              v-model:value="formState.name"
              class="ms-1 h-9"
              placeholder="Nhập tên môn học"
            />
          </a-form-item>
          <a-form-item
            name="semester"
            :rules="[{ required: true, message: 'Vui lòng chọn kỳ học!' }]"
          >
            <label for="" class="font-bold">Kỳ Học</label>
            <Dropdown
              v-model="formState.semester"
              :options="semesters"
              optionLabel="name"
              placeholder="Chọn Kỳ Học"
              class="w-full md:w-14rem border h-9 flex items-center"
            />
          </a-form-item>
          <a-form-item
            name="numberOfSessions"
            :rules="[{ required: true, message: 'Vui lòng chọn số buổi!' }]"
          >
            <label for="" class="font-bold">Số Buổi</label>
            <a-input-number
              id="inputNumber"
              class="w-full"
              v-model:value="formState.numberOfSessions"
              :min="1"
              :max="30"
            />
          </a-form-item>
          <!-- <a-form-item
            name="endDate"
            :rules="[
              { required: true, message: 'Vui lòng chọn ngày kết thúc!' },
            ]"
          >
            <label for="" class="font-bold">Ngày Kết Thúc</label>
            <input
              v-model="formState.endDate"
              datepicker
              type="date"
              class="border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full h-9 ps-10 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Select date"
            />
          </a-form-item> -->
        </div>

        <div class="grid grid-cols-1 gap-5">
          <div class="">
            <a-form-item
              name="description"
              :rules="[
                { required: true, message: 'Vui lòng nhập chi tiết môn học!' },
              ]"
            >
              <label for="" class="font-bold">Chi Tiết Môn Học</label>
              <Editor
                v-model="formState.description"
                editorStyle="height: 220px"
              />
            </a-form-item>
          </div>
          <!-- <div class="">
            <a-form-item
              name="detailsSemester"
              :rules="[
                {
                  required: true,
                  message: 'Vui lòng nhập kết quả đạt được sau kỳ học!',
                },
              ]"
            >
              <label for="" class="font-bold">Kết Quả Đạt Được Kỳ Học</label>
              <Editor
                v-model="formState.detailsSemester"
                editorStyle="height: 220px"
              />
            </a-form-item>
          </div> -->
        </div>

        <a-form-item>
          <a-button
            type="primary"
            class="bg-blue-500 float-end"
            html-type="submit"
            >Thêm Mới</a-button
          >
        </a-form-item>
      </a-form>
    </Dialog>
  </MainLayout>
</template>
