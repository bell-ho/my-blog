import Axios from 'axios';
import { backUrl } from '@/configs/backUrl';
import { getSession } from 'next-auth/react';
import { Cookies } from 'react-cookie';

const cookies = new Cookies();
export const axios = Axios.create({
  baseURL: backUrl,
  headers: {
    'Content-Type': 'application/json',
  },
});

axios.defaults.withCredentials = true;

axios.interceptors.request.use(
  async function (request) {
    const session = await getSession();

    if (session) {
      const token = cookies.get('Authorization');
      request.headers.Authorization = `Bearer ${token}`;
    }

    return request;
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
