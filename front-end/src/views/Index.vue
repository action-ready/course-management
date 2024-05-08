<script setup lang="ts">
import Footer from "../components/footer/Footer.vue";
import Navbar from "@/components/nav/Navbar.vue";
</script>
<template>
  <div>
    <Navbar />
    <div class="h-screen"></div>
    <div class=""><slot /></div>
    <Footer />
  </div>
</template>
