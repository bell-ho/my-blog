import React from 'react';
import PostContent from '@/components/posts/post-detail/PostContent';
import { dehydrate, QueryClient } from '@tanstack/react-query';
import { queryKey } from '@/react-query/constants';
import { getPostData, getPostsFiles } from '@/util/posts-util';
import { useOnePostQuery } from '@/query-hooks/usePosts';
import { useRouter } from 'next/router';

const Post = () => {
  const router = useRouter();
  const slug = router.query.slug;
  const data = useOnePostQuery(slug);

  return <PostContent post={data} />;
};

export async function getStaticPaths() {
  const postFilenames = getPostsFiles();

  const slugs = postFilenames.map((fileName) => fileName.replace(/\.md$/, ''));

  return {
    paths: slugs.map((slug) => ({ params: { slug } })),
    fallback: false,
  };
}

export async function getStaticProps(context) {
  const queryClient = new QueryClient();

  const {
    params: { slug },
  } = context;

  try {
    await queryClient.prefetchQuery([queryKey.post, slug], () => getPostData(slug));

    return {
      props: {
        dehydratedState: dehydrate(queryClient),
      },
      revalidate: 600,
    };
  } catch (e) {
    return { hasError: true };
  } finally {
    queryClient.clear();
  }
}

export default Post;
