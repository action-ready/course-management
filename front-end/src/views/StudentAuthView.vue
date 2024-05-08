<template>
  <div>
    <MainLayout>
      <a-tabs v-model:activeKey="activeKey">
        <a-tab-pane key="1" tab="Quản Lý Bảng Điểm">
          <GradesStudent />
        </a-tab-pane>
        <a-tab-pane key="2" tab="Quản Lý Môn Học" force-render>
          <Schedule :scheduleGroup="scheduleGroup" />
        </a-tab-pane>
      </a-tabs>
    </MainLayout>
  </div>
</template>

<script setup lang="ts">
import Schedule from "./Schedule.vue";
import { useAuth } from "@/auth";
import MainLayout from "@/components/layout/MainLayout.vue";
import GradesStudent from "@/components/students/GradesStudent.vue";
import ClazzService from "@/services/ClazzService";
import ScheduleService from "@/services/ScheduleService";
import { onMounted, ref } from "vue";
const clazzId = ref();
const auth = useAuth();
const scheduleGroup = ref<any>([]);

const fetchClazzId = async () => {
  const studentId = auth.state.studentId;
  if (studentId) {
    const { data } = await ClazzService.getClazzIdByStudentId(studentId);
    clazzId.value = data;
    fetchSchedule(clazzId.value);
  }
};
const fetchSchedule = async (clazzId: number) => {
  const { data } = await ScheduleService.getByClazzId(clazzId);

  const groupedSchedules = data.reduce((result: any, schedule: any) => {
    const semesterName = schedule.semesterName;
    if (!result[semesterName]) {
      result[semesterName] = [];
    }
    result[semesterName].push(schedule);
    return result;
  }, {});

  scheduleGroup.value = groupedSchedules;
  console.log(`<<<<< scheduleGroup  >>>>>`, scheduleGroup.value);
};

onMounted(() => {
  fetchClazzId();
});
</script>

<style scoped></style>
