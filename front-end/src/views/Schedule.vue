<script setup lang="ts">
import ScheduleService from "@/services/ScheduleService";
import Message from "primevue/message";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import Tag from "primevue/tag";
import dayjs from "dayjs";

interface Props {
  scheduleGroup: [];
}
const scheduleGroup = ref([]);
const props = defineProps<Props>();

watch(props, () => {
  scheduleGroup.value = props.scheduleGroup;
});
</script>

<template>
  <div class="grid grid-rows-1 gap-5">
    <div
      class="card border p-3 rounded-xl"
      v-for="(schedules, semesterName) in scheduleGroup"
      :key="semesterName"
    >
      <h3 class="text-2xl">{{ semesterName }}</h3>

      <div class="relative overflow-x-auto border rounded-lg">
        <table
          class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400"
        >
          <thead
            class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
          >
            <tr>
              <th scope="col" class="px-6 py-3">STT</th>
              <th scope="col" class="px-6 py-3">Tên Môn</th>
              <th scope="col" class="px-6 py-3">Số Lượng Buổi</th>
              <th scope="col" class="px-6 py-3">Ngày Bắt Đầu</th>
              <th scope="col" class="px-6 py-3">Ngày Kết Thúc</th>
              <th scope="col" class="px-6 py-3 w-44">Trạng Thái</th>
            </tr>
          </thead>
          <tbody>
            <tr
              class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
              v-for="(schedule, index) in schedules"
              :key="schedule.scheduleId"
            >
              <td class="px-6 py-4">{{ index + 1 }}</td>
              <td class="px-6 py-4">{{ schedule.name }}</td>
              <td class="px-6 py-4">{{ schedule.numberOfSessions }}</td>
              <td class="px-6 py-4">
                {{ dayjs(schedule.startDate).format("DD/MM/YYYY") }}
              </td>
              <td class="px-6 py-4">
                {{ dayjs(schedule.endDate).format("DD/MM/YYYY") }}
              </td>
              <td
                class="px-6 py-4"
                v-if="schedule.status == 0 || schedule.status == null"
              >
                <Tag severity="secondary" value="Chưa Học" rounded></Tag>
              </td>
              <td class="px-6 py-4" v-if="schedule.status == 1">
                <Tag severity="info" value="Đang Học" rounded></Tag>
              </td>
              <td class="px-6 py-4" v-if="schedule.status == 2">
                <Tag severity="success" value="Đã Học" rounded></Tag>
              </td>
              <td class="px-6 py-4" v-if="schedule.status == 3">
                <Tag severity="warning" value="Đang Cập Nhật" rounded></Tag>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
