import React, { Children, Fragment, useCallback, useEffect } from 'react';
import PostItem from '@/components/posts/PostItem';
import ContactForm from '@/components/contact/ContactForm';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKey } from '@/react-query/constants';
import { deletePost, likePost } from '@/pages/api/post/post';
import { useDelayed } from '@/util/usePageSearchUtil';
import { useSession } from 'next-auth/react';

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

  const deletePostMutation = useMutation((params) => deletePost(params), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.posts]);
    },
  });

  const onDeletePost = useCallback(
    async (postId) => {
      await deletePostMutation.mutate(postId);
    },
    [deletePostMutation],
  );

  return (
    <Fragment>
      <ContactForm />
      <ul>
        {Children.toArray(
          posts?.map((post) => (
            <PostItem post={post} onThumbClick={onThumbClick} onDeletePost={onDeletePost} />
          )),
        )}
      </ul>
    </Fragment>
  );
};
export default PostsGrid;
