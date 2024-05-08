import axios from "../utils/axiosCustomize";

export default class GradesService {
  static create(data: any) {
    return axios.post("/grades", data);
  }
}
