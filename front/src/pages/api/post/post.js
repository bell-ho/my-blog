import { axios } from '@/util/axios';

export const createPost = async (params) => {
  const { data } = await axios.post(`/api/v1/post`, params);

  return data;
};

export const getPosts = async (page, size, keyword = '') => {
  const { data } = await axios.get(`/api/v1/post`, { params: { page, size, keyword } });
  const { content, isLast } = data.data.posts;
  return { content, nextPage: page + 1, isLast };
};

export const likePost = async ({ postId, type, memberUniqueKey }) => {
  const { data } = await axios.post(
    `/api/v1/post/${postId}/type/${type}/member/${memberUniqueKey}`,
  );

  return data.data.postLikeDislike;
};

export const deletePost = async (postId) => {
  const { data } = await axios.delete(`/api/v1/post/${postId}`);

  return data.data;
};
