import { Fragment } from 'react';
import { useAllPostsQuery } from '@/query-hooks/usePosts';
import AllPosts from '@/components/posts/AllPosts';

export default function Home() {
  const data = useAllPostsQuery();

  return (
    <Fragment>
      <AllPosts posts={data} />
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
