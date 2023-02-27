import React, { Children, Fragment, useCallback, useEffect } from 'react';
import PostItem from '@/components/posts/PostItem';
import ContactForm from '@/components/contact/ContactForm';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKey } from '@/react-query/constants';
import { likePost } from '@/pages/api/post/post';
import { useDelayed } from '@/util/usePageSearchUtil';

const PostsGrid = ({ posts }) => {
  const queryClient = useQueryClient();

  const likeDislikePostMutation = useMutation((params) => likePost(params), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.posts]);
    },
  });

  const delayedFn = useDelayed();

  const onThumbClick = useCallback(
    async (postId, type, memberUniqueKey) => {
      delayedFn(async () => {
        await likeDislikePostMutation.mutate({ postId, type, memberUniqueKey });
      }, 300);
    },
    [delayedFn, likeDislikePostMutation],
  );

  return (
    <Fragment>
      <ContactForm />
      <ul>
        {Children.toArray(
          posts?.map((post) => <PostItem post={post} onThumbClick={onThumbClick} />),
        )}
      </ul>
    </Fragment>
  );
};
export default PostsGrid;
