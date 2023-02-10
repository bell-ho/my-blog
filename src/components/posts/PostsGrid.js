import React, { Children } from 'react';
import PostItem from '@/components/posts/PostItem';
import classes from './posts-grid.module.css';
const PostsGrid = ({ posts }) => {
  return (
    <ul className={classes.grid}>
      {Children.toArray(posts?.map((post) => <PostItem post={post} />))}
    </ul>
  );
};
export default PostsGrid;
