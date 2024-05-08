<script setup lang="ts">
import GradesManagement from "@/components/grades/GradesManagement.vue";
import MainLayout from "../components/layout/MainLayout.vue";
import Schedule from "./Schedule.vue";
import { onMounted, ref } from "vue";
import ScheduleService from "@/services/ScheduleService";
import { useRoute } from "vue-router";
import { useAuth } from "@/auth";
const route = useRoute();
const scheduleGroup = ref<any>([]);

const fetchSchedule = async () => {
  const { data } = await ScheduleService.getByClazzId(+route.params.id);

  const groupedSchedules = data.reduce((result: any, schedule: any) => {
    const semesterName = schedule.semesterName;
    if (!result[semesterName]) {
      result[semesterName] = [];
    }
    result[semesterName].push(schedule);
    return result;
  }, {});

  scheduleGroup.value = groupedSchedules;
};

const auth = useAuth();

onMounted(() => {
  fetchSchedule();
});
</script>

<template>
  <MainLayout>
    <a-tabs v-model:activeKey="activeKey">
      <a-tab-pane
        v-if="auth.state.role == 'TEACHER'"
        key="1"
        tab="Quản Lý Điểm"
      >
        <GradesManagement @fetchSchedule="fetchSchedule" />
      </a-tab-pane>
      <a-tab-pane key="2" tab="Quản Lý Môn Học" force-render>
        <Schedule :scheduleGroup="scheduleGroup" />
      </a-tab-pane>
    </a-tabs>
  </MainLayout>
</template>
