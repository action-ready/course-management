import axios from "../utils/axiosCustomize";
import type { CourseRequest } from "@/types/Course";

export default class CourseService {
  static getAll(): Promise<any> {
    return axios.get("/courses");
  }
  static create(data: CourseRequest): Promise<any> {
    return axios.post("/courses", data);
  }
  static getById(id: number) {
    return axios.get("/courses/" + id);
  }
  static getAllSelect(): Promise<any> {
    return axios.get("/courses/select");
  }
}
