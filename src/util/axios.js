import Axios from 'axios';
import { backUrl } from '@/configs/backUrl';

export const axios = Axios.create({
  baseURL: backUrl,
  headers: {
    'Content-Type': 'application/json',
  },
});

axios.defaults.withCredentials = true;

axios.interceptors.request.use(
  function (config) {
    return config;
  },
  function (error) {
    return Promise.reject(error);
  },
);

axios.interceptors.response.use(
  function (res) {
    return res;
  },
  function (error) {
    return Promise.reject(error);
  },
);
