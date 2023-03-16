import React from 'react';
import classes from './all-posts.module.css';
import PostsGrid from '@/components/posts/PostsGrid';
const AllPosts = ({ posts }) => {
  return (
    <section className={classes.posts}>
      <PostsGrid posts={posts} />
    </section>
  );
};
export default AllPosts;
