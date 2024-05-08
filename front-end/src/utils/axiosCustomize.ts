import { useAuth } from "@/auth";
import AuthService from "@/services/AuthService";
import axios from "axios";
import { useRouter } from "vue-router";

const instance = axios.create({
  baseURL: "http://localhost:8080/api/v1/",
});

instance.interceptors.request.use(
  function (config) {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);
// Add a response interceptor
instance.interceptors.response.use(
  function (response): any {
    return {
      status: response.status,
      data: response.data,
    };
  },
  async function (error) {
    const originalConfig = error.config;
    if (error.response.status == 401) {
      const refreshToken = localStorage.getItem("refreshToken");
      if (refreshToken) {
        const res = await AuthService.getRefreshToken(refreshToken);

        if (res.status == 200) {
          console.log(`<<<<<  res.data >>>>>`, res.data);
          localStorage.setItem("token", res.data.token);
          localStorage.setItem("refreshToken", res.data.refreshToken);
          originalConfig.headers.Authorization = `Bearer ${res.data.token}`;
          return axios(originalConfig);
        }
      }
    }
    return error.response.data ? error.response.data : Promise.reject(error);
  }
);
export default instance;
