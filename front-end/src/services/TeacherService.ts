import type { StudentCreate, StudentFilter } from "@/types/Student";
import axios from "../utils/axiosCustomize";
import type { TeacherCerate, TeacherFilter } from "@/types/Teacher";

export default class TeacherService {
  static create(data: TeacherCerate): Promise<any> {
    return axios.post(`/teachers`, data);
  }

  static getAll(teacherFilter: TeacherFilter): Promise<any> {
    let url = `/teachers?page=${teacherFilter.page}`;

    return axios.get(url);
  }

  static getAllByCourseId(courseId: number): Promise<any> {
    return axios.get(`/course/${courseId}/students`);
  }
}
