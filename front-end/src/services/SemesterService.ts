import axios from "../utils/axiosCustomize";

export default class SemesterService {
  static create(data: any, id: number): Promise<any> {
    return axios.post(`${id}/semesters`, data);
  }

  static getByCourseId(id: number) {
    return axios.get(`/course/${id}/semesters`);
  }
}
