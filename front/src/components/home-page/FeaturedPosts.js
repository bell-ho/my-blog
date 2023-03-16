import React from 'react';
import classes from './featured-posts.module.css';
import PostsGrid from '@/components/posts/PostsGrid';
const FeaturedPosts = ({ posts }) => {
  return (
    <section className={classes.latest}>
      <h2>Featured Posts</h2>
      <PostsGrid posts={posts} />
    </section>
  );
};

export default FeaturedPosts;
