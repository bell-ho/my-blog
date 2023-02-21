import { useQuery } from '@tanstack/react-query';
import { getAllPosts, getFeaturedPosts, getPostData } from '@/util/posts-util';
import { queryKey } from '@/react-query/constants';
import { commentOptions } from '@/react-query/queryOptions';
import { getPosts } from '@/pages/api/post/post';

export const useFeaturedPostsQuery = () => {
  const { data } = useQuery([queryKey.posts.featured], getFeaturedPosts, {
    ...commentOptions(5000, 10000),
  });
  return data;
};

export const useAllPostsQuery = () => {
  const { data } = useQuery([queryKey.posts.all], getPosts, {
    ...commentOptions(5000, 10000),
  });
  return data;
};

export const useOnePostQuery = (slug) => {
  const { data } = useQuery([queryKey.post, slug], () => getPostData(slug), {
    ...commentOptions(5000, 10000),
  });
  return data;
};
