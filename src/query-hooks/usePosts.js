import { useInfiniteQuery } from '@tanstack/react-query';
import { queryKey } from '@/react-query/constants';
import { getPosts } from '@/pages/api/post/post';
import { usePageSearchUtil } from '@/util/usePageSearchUtil';

export const useAllPostsQuery = () => {
  const { keyword, handleKeywordChange } = usePageSearchUtil();

  const {
    data,
    isLoading,
    error,
    fetchNextPage,
    hasNextPage,
    isFetching,
    isFetchingNextPage,
    status,
  } = useInfiniteQuery(
    [queryKey.posts, keyword],
    ({ pageParam = 0, size = 3 }) => getPosts(pageParam, size, keyword),
    {
      getNextPageParam: (lastPage) => (!lastPage.isLast ? lastPage.nextPage : undefined),
    },
  );

  return {
    data,
    error,
    fetchNextPage,
    hasNextPage,
    isFetching,
    isLoading,
    isFetchingNextPage,
    status,
    keyword,
    handleKeywordChange,
  };
};
