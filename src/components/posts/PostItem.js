import React from 'react';
import Link from 'next/link';
import Image from 'next/image';
import classes from './post-item.module.css';
import styled from '@emotion/styled';
const PostItem = ({ post: { title, image, date, excerpt, slug } }) => {
  const formattedDate = new Date(date).toLocaleDateString('en-US', {
    day: 'numeric',
    month: 'long',
    year: 'numeric',
  });

  const imagePath = `/images/posts/${slug}/${image}`;
  const linkPath = `/posts/${slug}`;

  return (
    <Wrapper>
      <li className={classes.post}>
        <div className={classes.image}>
          <Image
            layout={'response'}
            width={800}
            height={300}
            src={`/images/posts/getting-started-with-nextjs/getting-started-nextjs.png`}
            alt={title}
          />
        </div>
        <div className={classes.content}>
          <ContentWrapper>
            <div className="top">
              <h3>{'jh'}</h3>
              <time>{formattedDate}</time>
            </div>
            <p className={'bottom'}>{'#tag #test'}</p>
            <div className="icon">
              <div className={'left'}>
                <span class="material-icons-with-text">
                  <i class="material-icons">thumb_up_off_alt</i>
                  123
                </span>
                <span class="material-icons-with-text">
                  <i class="material-icons">thumb_down_off_alt</i>
                  12
                </span>
              </div>
              <span class="material-icons">delete</span>
            </div>
          </ContentWrapper>
        </div>
      </li>
    </Wrapper>
  );
};

const Wrapper = styled.div``;

const ContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;

  .top {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .bottom {
    display: flex;
    align-self: flex-start;
  }

  .icon {
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    .left {
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      gap: 10px;
    }
  }

  .material-icons-with-text {
    display: inline-flex;
    align-items: center;
  }
`;

export default PostItem;
