import { Fragment, useEffect, useRef } from 'react';
import { useAllPostsQuery } from '@/query-hooks/usePosts';
import { useInView } from 'react-intersection-observer';
import AllPosts from '@/components/posts/AllPosts';
import { useInfiniteQuery } from '@tanstack/react-query';
import { queryKey } from '@/react-query/constants';
import { getPosts } from '@/pages/api/post/post';
import { useObserver } from '@/util/useObserver';

const PAGE_SIZE = 3;
export default function Home() {
  // const data = useAllPostsQuery();
  const { ref, inView } = useInView();
  const bottom = useRef(null);

  const { data, error, fetchNextPage, hasNextPage, isFetching, isFetchingNextPage, status } =
    useInfiniteQuery(
      [queryKey.posts],
      ({ pageParam = 0, size = PAGE_SIZE }) => getPosts(pageParam, size),
      {
        getNextPageParam: (lastPage) => (!lastPage.isLast ? lastPage.nextPage : undefined),
      },
    );

  const posts = data?.pages.flatMap((page) => page.content) || [];

  useEffect(() => {
    if (inView) fetchNextPage();
  }, [fetchNextPage, inView]);

  if (status === 'loading') return <div>loading</div>;
  if (status === 'error') return <div>error</div>;

  // console.log(posts);

  // const onIntersect = ([entry]) => entry.isIntersecting && fetchNextPage();
  // useObserver({
  //   target: bottom,
  //   onIntersect,
  // });

  return (
    <Fragment>
      <AllPosts posts={posts} />

      {isFetchingNextPage ? <div>로딩</div> : <div ref={ref}></div>}
    </Fragment>
  );
}

// export async function getStaticProps() {
//   const queryClient = new QueryClient();
//
//   try {
//     await queryClient.prefetchQuery([queryKey.posts.featured], getFeaturedPosts);
//
//     return {
//       props: {
//         dehydratedState: dehydrate(queryClient),
//       },
//       revalidate: 60,
//     };
//   } catch (e) {
//     return { hasError: true };
//   } finally {
//     queryClient.clear();
//   }
// }
