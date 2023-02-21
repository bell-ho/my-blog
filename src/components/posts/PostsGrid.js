import React, { Children, Fragment, useCallback } from 'react';
import PostItem from '@/components/posts/PostItem';
import ContactForm from '@/components/contact/ContactForm';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKey } from '@/react-query/constants';
import { likePost } from '@/pages/api/post/post';

const PostsGrid = ({ posts }) => {
  const queryClient = useQueryClient();

  const likePostMutation = useMutation((params) => likePost(params), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.posts]);
    },
  });
  const onLike = useCallback(
    async (postId, memberUniqueKey) => {
      await likePostMutation.mutate({ postId, memberUniqueKey });
    },
    [likePostMutation],
  );
  return (
    // <ul className={classes.grid}>
    <Fragment>
      <ContactForm />
      <ul>{Children.toArray(posts?.map((post) => <PostItem post={post} onLike={onLike} />))}</ul>
    </Fragment>
  );
};
export default PostsGrid;
