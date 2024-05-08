<template>
  <div class="card flex justify-content-center items-center">
    <button
      v-tooltip.top="'Thêm Sinh Viên'"
      label="Show"
      @click="visible = true"
      class="px-4 py-2 bg-blue-500 me-3 rounded-lg text-white"
    >
      <i class="fa-solid fa-plus"></i>
    </button>
    <Dialog
      v-model:visible="visible"
      modal
      header="Thêm Sinh Viên"
      :style="{ width: '50vw' }"
      :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
    >
      <a-form
        :model="formState"
        name="basic"
        :label-col="{ span: 4 }"
        autocomplete="off"
        @finish="onFinish"
        @finishFailed="onFinishFailed"
      >
        <a-form-item
          label="Họ Tên"
          name="fullName"
          :rules="[{ required: true, message: 'Vui lòng nhập họ tên!' }]"
        >
          <a-input v-model:value="formState.fullName" />
        </a-form-item>

        <a-form-item
          label="Email"
          name="username"
          :rules="[{ required: true, message: 'Vui lòng nhập email!' }]"
        >
          <a-input type="email" v-model:value="formState.username" />
        </a-form-item>

        <a-form-item
          label="Khóa Học"
          name="courseId"
          :rules="[{ required: true, message: 'Vui lòng chọn khóa học!' }]"
        >
          <Dropdown
            v-model="formState.courseId"
            :options="courses"
            optionLabel="name"
            placeholder="Chọn Khóa Học"
            class="w-full md:w-14rem border h-8 flex items-center"
          />
        </a-form-item>

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
  </div>
</template>

<script setup lang="ts">
import CourseService from "@/services/CourseService";
import StudentService from "@/services/StudentService";
import type { CourseResponseSelect } from "@/types/Course";
import type { StudentCreate } from "@/types/Student";
import Dialog from "primevue/dialog";
import Dropdown from "primevue/dropdown";
import { onMounted, ref } from "vue";
const visible = ref(false);
import { reactive } from "vue";

const courses = ref<CourseResponseSelect[]>([]);
const emits = defineEmits(["fetchStudent"]);
const student = reactive<StudentCreate>({
  courseId: null,
  fullName: "",
  username: "",
});
interface FormState {
  fullName: string;
  username: string;
  courseId: number | null;
}

const formState = reactive<FormState>({
  fullName: "",
  username: "",
  courseId: null,
});
const onFinish = async (values: any) => {
  student.courseId = values.courseId.id;
  student.fullName = values.fullName;
  student.username = values.username;
  const res = await StudentService.create(student);

  emits("fetchStudent");
  visible.value = !visible.value;
};

const onFinishFailed = (errorInfo: any) => {
  console.log("Failed:", errorInfo);
};

const fetchCourse = async () => {
  const { data } = await CourseService.getAllSelect();
  courses.value = data;
};

onMounted(() => {
  fetchCourse();
});
</script>
