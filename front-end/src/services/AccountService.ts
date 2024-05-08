import axios from "../utils/axiosCustomize";

export default class AccountService {
  static getAllTeacher() {
    return axios.get("/accounts/teachers");
  }
  static getAllStudent() {
    return axios.get("/accounts/students");
  }
}
