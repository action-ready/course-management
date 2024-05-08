<template>
  <div class="flex h-screen justify-center items-center">
    <section class="bg-gray-50 w-full dark:bg-gray-900">
      <div
        class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0"
      >
        <a
          href="#"
          class="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white"
        >
          <img
            class="w-8 h-8 mr-2"
            src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/logo.svg"
            alt="logo"
          />
          Newwave Solutions
        </a>
        <div
          class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700"
        >
          <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1
              class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white"
            >
              Sign in to your account
            </h1>
            <span v-if="error" class="text-red-500"> {{ error }}</span>

            <a-form
              :model="formState"
              class="space-y-4 md:space-y-6"
              autocomplete="off"
              @finish="onFinish"
              @finishFailed="onFinishFailed"
            >
              <div>
                <label
                  for="password"
                  class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >Username</label
                >
                <a-form-item
                  name="username"
                  :rules="[
                    { required: true, message: 'Vui lòng nhập username!' },
                  ]"
                >
                  <a-input v-model:value="formState.username" />
                </a-form-item>
              </div>
              <div>
                <label
                  for="password"
                  class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >Password</label
                >
                <a-form-item
                  name="password"
                  :rules="[
                    { required: true, message: 'Vui lòng nhập mật khẩu!' },
                  ]"
                >
                  <a-input-password v-model:value="formState.password" />
                </a-form-item>
              </div>
              <div class="flex items-center justify-between">
                <div class="flex items-start">
                  <div class="flex items-center h-5">
                    <input
                      id="remember"
                      aria-describedby="remember"
                      type="checkbox"
                      class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800"
                    />
                  </div>
                  <div class="ml-3 text-sm">
                    <label
                      for="remember"
                      class="text-gray-500 dark:text-gray-300"
                      >Remember me</label
                    >
                  </div>
                </div>
                <a
                  href="#"
                  class="text-sm font-medium text-primary-600 hover:underline dark:text-primary-500"
                  >Forgot password?</a
                >
              </div>
              <button
                type="submit"
                class="w-full text-white bg-blue-500 hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
              >
                Sign in
              </button>
              <p class="text-sm font-light text-gray-500 dark:text-gray-400">
                Don’t have an account yet?
                <a
                  href="#"
                  class="font-medium text-primary-600 hover:underline dark:text-primary-500"
                  >Sign up</a
                >
              </p>
            </a-form>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
<script lang="ts" setup>
import { useAuth } from "@/auth";
import type { LoginRequest } from "@/services/AuthService";
import AuthService from "@/services/AuthService";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const error = ref("");
interface FormState {
  username: string;
  password: string;
}

const formState = reactive<FormState>({
  username: "nam963",
  password: "123456",
});
const onFinish = async (values: any) => {
  localStorage.removeItem("token");
  localStorage.removeItem("refreshToken");
  const data: LoginRequest = values;
  const res = await AuthService.login(data);
  console.log(`<<<<<  res >>>>>`, res);
  if (String(res) === "Incorrect username or password") {
    error.value = String("Tài khoản hoặc mật khẩu không đúng");
    return;
  }
  if (res.status == 200) {
    localStorage.setItem("token", res.data.token);
    localStorage.setItem("refreshToken", res.data.refreshToken);
    const auth = useAuth();
    auth.login(res.data.role, res.data.teacherId, res.data.studentId);
    console.log(`<<<<<  auth >>>>>`, auth.state.role);
    router.push("/");
  }
};

const onFinishFailed = (errorInfo: any) => {
  console.log("Failed:", errorInfo);
};
</script>
