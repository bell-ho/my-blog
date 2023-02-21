import { axios } from '@/util/axios';

export const createPost = async (params) => {
  const { data } = await axios.post(`/api/v1/post`, params);

  return data;
};

export const getPosts = async () => {
  const { data } = await axios.get(`/api/v1/post`);
  return data.data.posts;
};

export const likePost = async ({ postId, memberUniqueKey }) => {
  const { data } = await axios.patch(`/api/v1/post/${postId}/like/${memberUniqueKey}`);
};
