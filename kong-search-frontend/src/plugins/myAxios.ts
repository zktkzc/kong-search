import axios from "axios";

const myAxios = axios.create({
  baseURL: "http://localhost:8081/api",
  timeout: 10000,
});

myAxios.interceptors.response.use(
  (response) => {
    const data = response.data;
    if (data.code === 0) {
      return data.data;
    }
    console.error(data.message);
    return response.data;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default myAxios;
