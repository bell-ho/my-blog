import React from 'react';
import AllPosts from '@/components/posts/AllPosts';
import { useAllPostsQuery } from '@/query-hooks/usePosts';
import { dehydrate, QueryClient } from '@tanstack/react-query';
import { queryKey } from '@/react-query/constants';
import { getAllPosts } from '@/util/posts-util';

const AllPostsPage = () => {
  const data = useAllPostsQuery();

  return <AllPosts posts={data} />;
};

// export async function getStaticProps() {
//   const queryClient = new QueryClient();
//
//   try {
//     await queryClient.prefetchQuery([queryKey.posts.all], getAllPosts);
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

export default AllPostsPage;
