import axios from "../utils/axiosCustomize";

export default class ClazzService {
  static getAll() {
    return axios.get("/clazzs");
  }
  static getClazzById(id: number) {
    return axios.get("/clazzs/" + id);
  }
  static create(data: any) {
    return axios.post("/clazzs", data);
  }
  static createImportData(data: any, id: number) {
    return axios.put(`/clazzs/${id}`, data);
  }

  static getClazzByTeacherId(id: number) {
    return axios.get(`/teacher/${id}/clazzs`);
  }

  static getByClazzAndSchedule(clazzId: number, scheduleId: number) {
    if (clazzId && scheduleId) {
      return axios.get(`/clazz/${clazzId}/schedule/${scheduleId}/grades`);
    }
  }

  static getClazzIdByStudentId(studentId: number) {
    return axios.get(`/studentId/${studentId}/clazz`);
  }
}
