import React, { useEffect } from 'react';
import { useAllPostsQuery } from '@/query-hooks/usePosts';
import { useInView } from 'react-intersection-observer';
import AllPosts from '@/components/posts/AllPosts';
import styled from '@emotion/styled';
import MainNavigation from '@/components/layout/MainNavigation';
import ScrollToTopButton from '@/components/ui/ScrollToTopButton';

export default function Home() {
  const { ref, inView } = useInView();

  const {
    data,
    fetchNextPage,
    isLoading: loadPostsLoading,
    handleKeywordChange,
  } = useAllPostsQuery();

  const posts = data?.pages.flatMap((page) => page.content) || [];
  const isEmpty = data?.pages[0]?.length === 0;
  const isReachingEnd = isEmpty || (data && data.pages[data.pages.length - 1]?.length < 10);
  const hasMorePosts = !isEmpty && !isReachingEnd;
  const readToLoad = hasMorePosts && !loadPostsLoading;

  useEffect(() => {
    if (inView && readToLoad) {
      fetchNextPage();
    }
  }, [inView, readToLoad, fetchNextPage]);

  return (
    <Wrapper>
      <MainNavigation handleKeywordChange={handleKeywordChange} />
      <AllPosts posts={posts} />
      <ScrollToTopButton />
      <div ref={readToLoad ? ref : undefined} style={{ height: 50, backgroundColor: 'white' }} />
    </Wrapper>
  );
}

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
`;

// export async function getServerSideProps(context) {
//   const session = await getSession({ req: context.req });
//
//   if (!session) {
//     return {
//       redirect: {
//         destination: '/login',
//         permanent: false,
//       },
//     };
//   }
//
//   return {
//     props: {
//       session,
//     },
//   };
// }
