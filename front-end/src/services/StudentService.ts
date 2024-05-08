import type { StudentCreate, StudentFilter } from "@/types/Student";
import axios from "../utils/axiosCustomize";

export default class StudentService {
  static create(data: StudentCreate): Promise<any> {
    return axios.post(`/students`, data);
  }

  static getAll(studenTFilter: StudentFilter): Promise<any> {
    let url = `/students?page=${studenTFilter.page}`;
    if (studenTFilter.studentCode) {
      url += `&studentCode=${studenTFilter.studentCode}`;
    }
    return axios.get(url);
  }

  static getAllByCourseId(courseId: number): Promise<any> {
    return axios.get(`/course/${courseId}/students`);
  }
  static getStudentById(id: number): Promise<any> {
    return axios.get("/students/" + id);
  }
}
