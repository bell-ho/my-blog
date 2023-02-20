import { axios } from '@/util/axios';

export const createPost = async (params) => {
  const { data } = await axios.post(`/api/v1/post`, params);

  return data;
};
