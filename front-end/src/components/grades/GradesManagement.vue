<script setup lang="ts">
import ModalCreateGrades from "./ModalCreateGrades.vue";
import ClazzService from "@/services/ClazzService";
import ScheduleService from "@/services/ScheduleService";
import SemesterService from "@/services/SemesterService";
import type { ClazzResponseDetails } from "@/types/Clazz";
import type { Schedules } from "@/types/Course";
import type { SemesterResponse } from "@/types/Semester";
import type { StudentResponseGrades } from "@/types/Student";
import Message from "primevue/message";

import Dropdown from "primevue/dropdown";
import Panel from "primevue/panel";

import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import * as XLSX from "xlsx";
import ModalImportGrades from "./ModalImportGrades.vue";
const selectedSemester = ref<number>();
const selectedSchedule = ref<number>();
const clazzDetails = ref<ClazzResponseDetails>();
const semesters = ref<SemesterResponse[]>();
const studentsGrades = ref<StudentResponseGrades[]>([]);
const schedules = ref<Schedules[]>([]);
const columns = [
  {
    title: "#",
    dataIndex: "#",
    key: "na#me",
  },
  {
    title: "Mã Lớp",
    dataIndex: "studentCode",
    key: "studentCode 1",
  },
  {
    title: "Họ Và Tên",
    dataIndex: "fullName",
    key: "fullName",
  },
  {
    title: "Điểm",
    dataIndex: "grade",
    key: "grade",
  },
];
const route = useRoute();
const status = ref("");
const emits = defineEmits(["fetchSchedule"]);
const getClazzById = async () => {
  const { data } = await ClazzService.getClazzById(+route.params.id);
  clazzDetails.value = data;
  if (clazzDetails.value) {
    const { data } = await SemesterService.getByCourseId(
      clazzDetails.value.courseId
    );

    semesters.value = data;
  }
};

watch(selectedSemester, async () => {
  if (selectedSemester.value) {
    const { data } = await ScheduleService.getBySemesterId(
      selectedSemester.value.id
    );
    schedules.value = data;
  }
});

const fetchStudentGrades = async () => {
  const { data } = await ClazzService.getByClazzAndSchedule(
    clazzDetails.value?.id,
    selectedSchedule.value.id
  );
  // if (!data) {
  //   return;
  // }
  // const uniqueStudents = data.reduce((acc, current) => {
  //   const existing = acc.find((item) => item.studentId === current.studentId);
  //   if (!existing) {
  //     return [...acc, current];
  //   }
  //   return acc;
  // }, []);

  studentsGrades.value = data;
};
const getScheduleById = async () => {
  if (selectedSchedule.value) {
    const { data } = await ScheduleService.getById(selectedSchedule.value.id);

    status.value = data.status;
  }
};

watch(selectedSchedule, () => {
  if (selectedSchedule.value) {
    fetchStudentGrades();
    getScheduleById();
  }
});
const exportStudents = () => {
  const data = studentsGrades.value;
  const rearrangedData = data.map((student) => {
    return {
      studentCode: student.studentCode,
      studentId: student.studentId,
      fullName: student.fullName,
      grade: student.grade,
    };
  });

  const worksheet = XLSX.utils.json_to_sheet(rearrangedData);

  const workbook = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(workbook, worksheet, "Students");

  const wbout = XLSX.write(workbook, { bookType: "xlsx", type: "array" });

  const blob = new Blob([wbout], { type: "application/octet-stream" });

  const url = window.URL.createObjectURL(blob);
  const anchor = document.createElement("a");
  anchor.href = url;
  anchor.download = "Students.xlsx";
  document.body.appendChild(anchor);
  anchor.click();

  window.URL.revokeObjectURL(url);
  document.body.removeChild(anchor);
};

const handleUpdateBlockStatus = async () => {
  const res = await ScheduleService.updateScheduleStatus(
    selectedSchedule.value.id
  );
  emits("fetchSchedule");
  getScheduleById();
};

const handleUpdateActiveStatus = async () => {
  const res = await ScheduleService.updateScheduleStatusActive(
    selectedSchedule.value.id
  );
  emits("fetchSchedule");
  getScheduleById();
};
const fetchSchedule = () => {
  emits("fetchSchedule");
};
onMounted(() => {
  getClazzById();
});
</script>

<template>
  <div class="card border">
    <Panel>
      <div class="flex items-center gap-x-6 ms-4">
        <span>Chọn Kỳ Học: </span>
        <div class="card flex justify-content-center border rounded-lg">
          <Dropdown
            v-model="selectedSemester"
            :options="semesters"
            optionLabel="name"
            placeholder="Chọn kỳ học"
            class="w-44 md:w-14rem"
          />
        </div>
        <span>Chọn Môn Học: </span>
        <div class="card flex justify-content-center border rounded-lg">
          <Dropdown
            v-model="selectedSchedule"
            :options="schedules"
            optionLabel="name"
            placeholder="Chọn môn học"
            class="w-44 md:w-14rem"
          />
        </div>
        <div v-if="studentsGrades.length > 0" class="">
          <span>Xuất Danh Sách Sinh Viên: </span>
          <button @click="exportStudents" class="text-blue-500 underline">
            Xuất Danh Sách
          </button>
        </div>

        <div
          class="flex justify-between gap-5"
          v-if="status === 'ACTIVE' || status == 'UNFINISHED'"
        >
          <ModalCreateGrades
            @fetchSchedule="fetchSchedule"
            @fetchStudentGrades="fetchStudentGrades"
            @getScheduleById="getScheduleById"
            :studentsGrades="studentsGrades"
            :selectedSchedule="selectedSchedule"
          />
          <ModalImportGrades
            @fetchSchedule="fetchSchedule"
            :selectedSchedule="selectedSchedule"
            @fetchStudentGrades="fetchStudentGrades"
            @getScheduleById="getScheduleById"
          />

          <button
            @click="handleUpdateBlockStatus"
            class="bg-stone-500 text-white rounded-lg hover:bg-slate-700"
          >
            Đóng Lớp
          </button>
        </div>
        <Message
          v-if="studentsGrades.length > 0 && status === 'BLOCK'"
          :closable="false"
          severity="success"
          ><span class="text-xs">Đã Hoàn Thành</span></Message
        >
        <Message
          v-if="
            (studentsGrades.length > 0 && status === 'COMING_SOON') ||
            status === null
          "
          :closable="false"
          severity="secondary"
          ><span class="text-xl"> Chưa Học</span></Message
        >

        <button
          @click="handleUpdateActiveStatus"
          v-if="
            status &&
            status != 'ACTIVE' &&
            status != 'BLOCK' &&
            status != 'UNFINISHED'
          "
          class="bg-blue-500 p-2 text-white rounded-lg hover:bg-blue-500"
        >
          Mở Lớp
        </button>
      </div>
    </Panel>

    <div class="my-5">
      <a-table :columns="columns" :data-source="studentsGrades">
        <template #bodyCell="{ column, text }">
          <template v-if="column.dataIndex === 'name'">
            <a>{{ text }}</a>
          </template>
        </template>
      </a-table>
    </div>
  </div>
</template>
