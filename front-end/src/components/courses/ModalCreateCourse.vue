<template>
  <div>
    <Button
      class="bg-blue-500 px-5 py-3 text-white"
      label=""
      v-tooltip.top="'Tạo Khóa Học '"
      @click="visible = true"
    >
      <i class="fa-solid fa-plus"></i>
    </Button>

    <Dialog
      v-model:visible="visible"
      modal
      header="Thêm Mới Khóa Học"
      :style="{ width: '70vw' }"
      :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
    >
      <!-- <div class="">
        <a-form
          :model="formState"
          name="basic"
          :label-col="{ span: 8 }"
          :wrapper-col="{ span: 16 }"
          autocomplete="off"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
          class="grid grid-cols-2 -ml-12"
        >
          <a-form-item
            label="Tên khóa học"
            name="name"
            :rules="[
              { required: true, message: 'Tên khóa học không được để trống!' },
            ]"
          >
            <a-input v-model:value="formState.name" />
          </a-form-item>

          <a-form-item
            label="Ngày bắt đầu"
            name="startDate"
            :rules="[
              { required: true, message: 'Ngày bắt đầu không được để trống!' },
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
                class="border border-gray-200 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-1.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="Select date"
              />
            </div>
          </a-form-item>
          <a-form-item
            label="Ngày kết thúc"
            name="endDate"
            :rules="[
              { required: true, message: 'Ngày bắt đầu không được để trống!' },
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
                class="border border-gray-200 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-1.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="Select date"
              />
            </div>
          </a-form-item>

          <a-form-item
            label="Ảnh URL"
            name="imageUrl"
            :rules="[
              { required: true, message: 'Đường dẫn ảnh không được để trống!' },
            ]"
          >
            <a-input v-model:value="formState.imageUrl" />
          </a-form-item>

          <a-form-item style="margin-left: 850px">
            <a-button type="primary" class="bg-blue-500" html-type="submit"
              >Submit</a-button
            >
          </a-form-item>
        </a-form>
      </div> -->
      <div class="">
        <a-form
          :model="formState"
          name="basic"
          autocomplete="off"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
        >
          <div class="grid grid-cols-2 ms-3 gap-x-4">
            <a-form-item
              label="Tên khóa học"
              name="name"
              :rules="[
                {
                  required: true,
                  message: 'Tên khóa học không được để trống!',
                },
              ]"
            >
              <a-input v-model:value="formState.name" />
            </a-form-item>

            <a-form-item
              label="Giá khóa học"
              name="price"
              :rules="[{ required: true, message: 'Giá không được để trống!' }]"
            >
              <a-input-number
                id="inputNumber"
                v-model:value="formState.price"
                :min="1"
                defaultValue="100000"
                class="w-full"
              />
            </a-form-item>

            <a-form-item
              label="Ảnh URL"
              name="imageUrl"
              :rules="[
                {
                  required: true,
                  message: 'Đường dẫn ảnh không được để trống!',
                },
              ]"
            >
              <a-input
                v-model:value="formState.imageUrl"
                class="w-11/12 ms-8"
              />
            </a-form-item>
            <a-form-item
              label="Ngôn ngữ"
              name="language"
              :rules="[
                {
                  required: true,
                  message: 'Ngôn ngữ không được để trống!',
                },
              ]"
            >
              <Dropdown
                v-model="formState.language"
                :options="cities"
                optionLabel="name"
                placeholder="Chọn ngôn ngữ cho khóa học"
                class="w-12/12 ms-4 md:w-14rem border h-9 flex items-center justify-center"
              />
            </a-form-item>

            <a-form-item
              label="Thời Gian Học"
              name="studyTime"
              :rules="[
                {
                  required: true,
                  message: 'Thời gian học không được để trống!',
                },
              ]"
            >
              <a-input v-model:value="formState.studyTime" />
            </a-form-item>
            <!-- <a-form-item
              label="Ngày Bắt Đầu"
              name="startDate"
              :rules="[
                {
                  required: true,
                  message: 'Ngày bắt đầu không được để trống!',
                },
              ]"
            >
              <div
                class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none"
              >
                <svg
                  class="w-4 h-4 text-gray-500 dark:text-gray-400"
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
                v-model="formState.startDate"
                datepicker
                type="date"
                class="border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full h-9 ps-10 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="Select date"
              />
            </a-form-item> -->

            <!-- <a-form-item
              label="Ngày Kết Thúc"
              name="endDate"
              :rules="[
                {
                  required: true,
                  message: 'Ngày kết thúc không được để trống!',
                },
              ]"
            >
              <div
                class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none"
              >
                <svg
                  class="w-4 h-4 text-gray-500 dark:text-gray-400"
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
                v-model="formState.endDate"
                datepicker
                type="date"
                class="border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full h-9 ps-10 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="Select date"
              />
            </a-form-item> -->
          </div>
          <div class="w-full">
            <a-form-item
              label="Mô tả khóa học:"
              name="description"
              :rules="[
                {
                  required: true,
                  message: 'Mô tả khóa học không được để trống!',
                },
              ]"
            >
              <Editor
                v-model="formState.description"
                editorStyle="height: 200px "
                class="w-full"
              />
            </a-form-item>
          </div>
          <a-form-item style="margin-left: 850px">
            <a-button type="primary" class="bg-blue-500" html-type="submit"
              >Submit</a-button
            >
          </a-form-item>
        </a-form>
      </div>
    </Dialog>
  </div>
</template>
<script lang="ts" setup>
import Dialog from "primevue/dialog";
import Button from "primevue/button";
import { ref } from "vue";
import Editor from "primevue/editor";
import { reactive } from "vue";
import Dropdown from "primevue/dropdown";
import CourseService from "@/services/CourseService";
import type { CourseRequest } from "@/types/Course";

const visible = ref(false);
const emits = defineEmits(["fetchAll"]);

const cities = ref([{ name: "Tiếng Việt" }, { name: "Tiếng Anh" }]);
interface FormState {
  name: string;
  price: number | null;
  imageUrl: string;
  description: string;
  language: string;
  startDate: string;
  endDate: string;
  studyTime: string;
}

const formState = reactive<FormState>({
  name: "",
  description: "",
  price: null,
  imageUrl: "",
  language: "",
  startDate: "",
  endDate: "",
  studyTime: "",
});
const onFinish = async (values: CourseRequest) => {
  values.language = values.language.name.toString();

  const res = await CourseService.create(values);
  console.log(`<<<<<  res >>>>>`, res);
  emits("fetchAll");

  visible.value = !visible.value;
};

const onFinishFailed = (errorInfo: any) => {};
</script>
