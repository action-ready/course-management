import {
  createRouter,
  createWebHistory,
  type NavigationGuardNext,
  type RouteLocationNormalized,
  type RouteRecordRaw,
} from "vue-router";
import Course from "../views/Course.vue";
import { Role, useAuth } from "@/auth";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "home",
    meta: { requiresAuth: true },
    component: Course,
  },
  {
    path: "/about",
    name: "about",
    component: () => import("../views/AboutView.vue"),
  },
  {
    path: "/course/:id",
    name: "course-details",
    meta: { requiresAuth: true, role: Role.Admin },
    component: () => import("../views/CourseDetails.vue"),
  },
  {
    path: "/clazz",
    name: "clazz",
    meta: { requiresAuth: true, role: Role.Admin },
    component: () => import("../views/Clazz.vue"),
  },
  {
    path: "/clazz/:id",
    name: "clazz-details",
    meta: { requiresAuth: true, role: Role.Admin },
    component: () => import("../views/ClazzDetails.vue"),
  },
  {
    path: "/grades",
    name: "grades",
    meta: { requiresAuth: true, role: Role.Teacher },
    component: () => import("../views/Grades.vue"),
  },
  {
    path: "/grades/:id",
    name: "grades-details",
    meta: { requiresAuth: true },
    component: () => import("../views/GradesDetails.vue"),
  },
  {
    path: "/account",
    name: "account",
    meta: { requiresAuth: true, role: Role.Admin },
    component: () => import("../views/Account.vue"),
  },
  {
    path: "/login",
    name: "login",
    component: () => import("../views/LoginView.vue"),
  },
  {
    path: "/teacher/:id",
    name: "teacher",
    meta: { requiresAuth: true, role: Role.Admin },
    component: () => import("../components/teachers/TeacherDetails.vue"),
  },
  {
    path: "/student",
    name: "student",
    meta: { requiresAuth: true },
    component: () => import("../views/StudentAuthView.vue"),
  },
  {
    path: "/index",
    name: "index",

    component: () => import("../views/Index.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.beforeEach(
  (
    to: RouteLocationNormalized,
    from: RouteLocationNormalized,
    next: NavigationGuardNext
  ) => {
    const { state } = useAuth();

    if (to.meta.requiresAuth && !state.isAuthenticated) {
      next("/login");
    } else if (to.meta.role && to.meta.role !== state.role) {
      next("/access-denied");
    } else {
      next();
    }
  }
);

// const router = createRouter({
//   history: createWebHistory(import.meta.env.BASE_URL),
//   routes: [
//     {
//       path: "/",
//       name: "home",
//       component: Course,
//     },
//     {
//       path: "/about",
//       name: "about",
//       component: () => import("../views/AboutView.vue"),
//     },
//     {
//       path: "/course/:id",
//       name: "course-details",
//       component: () => import("../views/CourseDetails.vue"),
//     },
//     {
//       path: "/clazz",
//       name: "clazz",
//       component: () => import("../views/Clazz.vue"),
//     },
//     {
//       path: "/clazz/:id",
//       name: "clazz-details",
//       component: () => import("../views/ClazzDetails.vue"),
//     },
//     {
//       path: "/grades",
//       name: "grades",
//       component: () => import("../views/Grades.vue"),
//     },
//     {
//       path: "/grades/:id",
//       name: "grades-details",
//       component: () => import("../views/GradesDetails.vue"),
//     },
//     {
//       path: "/account",
//       name: "account",
//       component: () => import("../views/Account.vue"),
//     },
//     {
//       path: "/login",
//       name: "login",
//       component: () => import("../views/LoginView.vue"),
//     },
//   ],
// });

export default router;
