import React, { Children, Fragment } from 'react';
import PostItem from '@/components/posts/PostItem';
import classes from './posts-grid.module.css';
import ContactForm from '@/components/contact/ContactForm';
const PostsGrid = ({ posts }) => {
  return (
    // <ul className={classes.grid}>
    <Fragment>
      <ContactForm />
      <ul>{Children.toArray(posts?.map((post) => <PostItem post={post} />))}</ul>
    </Fragment>
  );
};
export default PostsGrid;
