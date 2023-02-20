import { Fragment } from 'react';
import FeaturedPosts from '@/components/home-page/FeaturedPosts';
import Hero from '@/components/home-page/Hero';
import { getFeaturedPosts } from '@/util/posts-util';
import { dehydrate, QueryClient } from '@tanstack/react-query';
import { queryKey } from '@/react-query/constants';
import { useFeaturedPostsQuery } from '@/query-hooks/usePosts';
export default function Home() {
  const data = useFeaturedPostsQuery();

  return (
    <Fragment>
      <FeaturedPosts posts={data} />
    </Fragment>
  );
}

export async function getStaticProps() {
  const queryClient = new QueryClient();

  try {
    await queryClient.prefetchQuery([queryKey.posts.featured], getFeaturedPosts);

    return {
      props: {
        dehydratedState: dehydrate(queryClient),
      },
      revalidate: 60,
    };
  } catch (e) {
    return { hasError: true };
  } finally {
    queryClient.clear();
  }
}
