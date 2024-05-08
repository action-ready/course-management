//in main.js
import "primevue/resources/themes/aura-light-green/theme.css";
import "primeicons/primeicons.css";
import "ant-design-vue/dist/reset.css";
import { createApp } from "vue";
import PrimeVue from "primevue/config";
import Antd from "ant-design-vue";
import ToastService from "primevue/toastservice";
import Tooltip from "primevue/tooltip";

import App from "./App.vue";
import router from "./router";

const app = createApp(App);

app.use(router);
app.use(PrimeVue);
app.use(Antd);
app.use(ToastService);
app.directive("tooltip", Tooltip);

app.mount("#app");
