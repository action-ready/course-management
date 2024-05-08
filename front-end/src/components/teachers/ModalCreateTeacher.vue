<template>
  <div class="card flex justify-content-center items-center">
    <button
      v-tooltip.top="'Thêm Giảng Viên'"
      label="Show"
      @click="visible = true"
      class="px-4 py-2 bg-blue-500 me-3 rounded-lg text-white"
    >
      <i class="fa-solid fa-plus"></i>
    </button>
    <Dialog
      v-model:visible="visible"
      modal
      header="Thêm Giảng Viên"
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
import TeacherService from "@/services/TeacherService";
import type { CourseResponseSelect } from "@/types/Course";
import type { TeacherCerate } from "@/types/Teacher";
import Dialog from "primevue/dialog";
import Dropdown from "primevue/dropdown";
import { onMounted, ref } from "vue";
const visible = ref(false);
import { reactive } from "vue";

const courses = ref<CourseResponseSelect[]>([]);
const emits = defineEmits(["fetchTeachers"]);
const teacher = reactive<TeacherCerate>({
  fullName: "",
  username: "",
});
interface FormState {
  fullName: string;
  username: string;
}

const formState = reactive<FormState>({
  fullName: "",
  username: "",
});
const onFinish = async (values: any) => {
  teacher.fullName = values.fullName;
  teacher.username = values.username;
  const res = await TeacherService.create(teacher);

  emits("fetchTeachers");
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
