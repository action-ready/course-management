<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { ClazzResponse } from "../../types/Clazz";
import { Item } from "ant-design-vue/es/menu";

const props = defineProps(["clazzs"]);

const clazzs = ref<ClazzResponse[]>();

onMounted(() => {
  clazzs.value = props.clazzs;
});
watch(props, () => {
  clazzs.value = props.clazzs;
});
</script>

<template>
  <div class="relative overflow-x-auto rounded-lg">
    <table
      class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 border rounded-lg"
    >
      <thead
        class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
      >
        <tr>
          <th scope="col" class="px-6 py-3">#</th>
          <th scope="col" class="px-6 py-3">Mã Lớp</th>
          <th scope="col" class="px-6 py-3">Giảng Viên</th>
          <th scope="col" class="px-6 py-3">Khóa Học</th>
          <th scope="col" class="px-6 py-3">Action</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="(item, index) in clazzs"
          :key="item.id"
          class="bg-white border-b dark:bg-gray-800 text-black dark:border-gray-700"
        >
          <td class="px-6 py-4">{{ index + 1 }}</td>
          <td class="px-6 py-4">{{ item.clazzCode }}</td>
          <td class="px-6 py-4">{{ item.teacherFullName }}</td>
          <td class="px-6 py-4">{{ item.courseName }}</td>
          <td class="px-6 py-4">
            <RouterLink :to="'/clazz/' + item.id"
              ><i
                v-tooltip.top="'Chi Tiết Lớp Học '"
                class="fa-solid fa-eye text-2xl text-blue-500 cursor-pointer"
              ></i
            ></RouterLink>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
