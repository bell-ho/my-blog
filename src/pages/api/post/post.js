import { axios } from '@/util/axios';

export const createPost = async (params) => {
  const { data } = await axios.post(`/api/v1/post`, params);

  return data;
};

export const getPosts = async (page, size) => {
  const { data } = await axios.get(`/api/v1/post`, { params: { page, size } });
  const { content, isLast } = data.data.posts;
  console.log(isLast);
  return { content, nextPage: page + 1, isLast };
};

export const likePost = async ({ postId, type, memberUniqueKey }) => {
  const { data } = await axios.post(
    `/api/v1/post/${postId}/type/${type}/member/${memberUniqueKey}`,
  );

  return data.data.postLikeDislike;
};

export const uploadImagesAPI = async (params) => {
  // const { data } = await axios.post('/post/images', params);
  // return data;
  // return params;
};
