<script setup lang="ts">
import ClazzService from "@/services/ClazzService";
import { useRoute } from "vue-router";
import Panel from "primevue/panel";
import { onMounted, ref } from "vue";
import type { ClazzResponseDetails } from "@/types/Clazz";
import ModalImportStudent from "@/components/students/ModalImportStudent.vue";
import ModalCreateStudent from "@/components/students/ModalCreateStudent.vue";
import MainLayout from "@/components/layout/MainLayout.vue";
import dayjs from "dayjs";
const route = useRoute();
const clazzDetails = ref<ClazzResponseDetails>();
const getClazzById = async () => {
  const { data } = await ClazzService.getClazzById(+route.params.id);
  clazzDetails.value = data;
};

const columns = [
  {
    title: "#",
    dataIndex: "#",
    key: "#",
    customRender: ({ text, record, index }) => {
      return index + 1;
    },
  },
  {
    title: "Mã Sinh Viên",
    dataIndex: "studentCode",
    key: "#",
    with: 80,
  },
  {
    title: "Họ Và Tên",
    dataIndex: "fullName",
    key: "fullName",
  },
];

onMounted(() => {
  getClazzById();
});
</script>

<template>
  <MainLayout>
    <h3 class="text-2xl">Chi tiết Lớp Học</h3>
    <div class="card border rounded-lg border-gray-200">
      <Panel :header="'Mã Lớp Học: ' + clazzDetails?.clazzCode">
        <h2>Tên Khóa Học: {{ clazzDetails?.courseName }}</h2>
        <h2>Tên Giảng Viên: {{ clazzDetails?.teacherFullName }}</h2>
        <h2>
          Ngày Bắt Đầu:
          {{ dayjs(clazzDetails?.startDate).format("DD/MM/YYYY") }}
        </h2>
        <h2>
          Ngày Kết Thúc: {{ dayjs(clazzDetails?.endDate).format("DD/MM/YYYY") }}
        </h2>
      </Panel>
    </div>
    <div class="card my-10">
      <div class="flex justify-between items-center">
        <h3 class="text-2xl">Danh Sách Sinh Viên</h3>

        <div class="flex gap-4 mb-2">
          <ModalCreateStudent
            :courseId="clazzDetails?.courseId"
            @getClazzById="getClazzById"
          />
          <ModalImportStudent @getClazzById="getClazzById" />
        </div>
      </div>
      <a-table :columns="columns" :data-source="clazzDetails?.students">
        <template #bodyCell="{ column, text }">
          <template v-if="column.dataIndex === 'name'">
            <a>{{ text }}</a>
          </template>
        </template>
      </a-table>
    </div>
  </MainLayout>
</template>
