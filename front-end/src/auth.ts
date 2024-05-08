import { reactive, readonly } from "vue";

export enum Role {
  Admin = "ADMIN",
  Teacher = "TEACHER",
  Student = "Student",
}

interface AuthState {
  isAuthenticated: boolean;
  role: Role | null;
  id: number | null;
  studentId: number | null;
  clazzId: number | null;
}

const state = reactive<AuthState>({
  isAuthenticated: false,
  role: null,
  id: null,
  studentId: null,
  clazzId: null,
});

export function useAuth() {
  function login(role: Role, id: number, studentId: number) {
    state.isAuthenticated = true;
    state.role = role;
    state.id = id;
    state.studentId = studentId;
  }

  function logout() {
    state.isAuthenticated = false;
    state.role = null;
    state.id = null;
  }

  return {
    state: readonly(state),
    login,
    logout,
  };
}
