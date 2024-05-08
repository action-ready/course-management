<script setup lang="ts">
import ModalCreateClazz from "@/components/clazzs/ModalCreateClazz.vue";
import { onMounted, ref } from "vue";
import MainLayout from "../components/layout/MainLayout.vue";
import ListClazz from "@/components/clazzs/ListClazz.vue";
import ClazzService from "@/services/ClazzService";
import type { ClazzResponse } from "@/types/Clazz";
const value = ref<string>("");
const clazzs = ref<ClazzResponse[]>([]);

const onSearch = (searchValue: string) => {};

const fetchClazz = async () => {
  const { data } = await ClazzService.getAll();

  clazzs.value = data;
};

onMounted(() => {
  fetchClazz();
});
</script>

<template>
  <MainLayout>
    <h3 class="text-3xl">Quản Lý Lớp Học</h3>
    <div class="flex justify-between my-5">
      <div class="w-96 flex items-center">
        <a-input-search
          v-model:value="value"
          placeholder="Search..."
          size="large"
          @search="onSearch"
        >
          <template #enterButton>
            <a-button class="bg-blue-500" style="color: white">Search</a-button>
          </template>
        </a-input-search>
      </div>

      <ModalCreateClazz @fetchClazz="fetchClazz" />
    </div>
    <ListClazz :clazzs="clazzs" />
  </MainLayout>
</template>
