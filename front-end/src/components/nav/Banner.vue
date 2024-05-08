<template>
  <div class="">
    <a-carousel style="height: 96px" :after-change="onChange">
      <div>
        <img
          src="https://www.bkacad.edu.vn/upload_images/images/H%E1%BB%87%20ch%E1%BB%A9ng%20ch%E1%BB%89/PYTHONKIDS.jpg"
          alt=""
          class="w-full object-cover"
        />
      </div>
      <div>
        <img
          src="https://www.bkacad.edu.vn/upload_images/images/H%E1%BB%87%20ch%E1%BB%A9ng%20ch%E1%BB%89/PYTHONKIDS.jpg"
          alt=""
          class="w-full object-cover min-h-64"
        />
      </div>
      <div>
        <img
          src="https://hocvienit.vn/wp-content/uploads/2020/09/khoa-hoc-lap-trinh-java-nang-cao.jpg"
          alt=""
          class="w-full object-cover min-h-64"
        />
      </div>
    </a-carousel>
  </div>
</template>
<script lang="ts" setup>
const onChange = (current: number) => {
  console.log(current);
};
</script>
<style scoped>
:deep(.slick-slide) {
  text-align: center;
  height: 660px;
  line-height: 360px;
  background: #364d79;
  overflow: hidden;
}

:deep(.slick-slide h3) {
  color: #fff;
}
</style>
