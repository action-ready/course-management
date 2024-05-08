import axios from "../utils/axiosCustomize";

export type LoginRequest = {
  username: string;
  password: string;
};

export default class AuthService {
  static login(request: LoginRequest) {
    return axios.post("/auth/login", request);
  }
  static getRefreshToken(refreshToken: string) {
    return axios.get("/auth/refreshToken?refreshToken=" + refreshToken);
  }
}
