import axios from "../utils/axiosCustomize";

export default class ScheduleService {
  static create(data: any, id: number): Promise<any> {
    return axios.post(`${id}/schedule`, data);
  }

  static getBySemesterId(id: number): Promise<any> {
    return axios.get(`/semester/${id}/schedules`);
  }
  static getById(id: number): Promise<any> {
    return axios.get(`/schedule/${id}`);
  }
  static getByClazzId(id: number): Promise<any> {
    return axios.get(`/clazz/${id}/schedules`);
  }

  static updateScheduleStatus(id: number) {
    return axios.put("/schedules/" + id + "/block");
  }
  static updateScheduleStatusActive(id: number) {
    return axios.put("/schedules/" + id + "/active");
  }
}
