import React, { Children, useCallback, useEffect, useState } from 'react';
import Link from 'next/link';
import classes from './post-item.module.css';
import styled from '@emotion/styled';
import { useSession } from 'next-auth/react';
import Notification from '@/components/ui/notification';
import ImageSwiper from '@/components/ui/ImageSwiper';

const PostItem = ({
  post: { id, content, nickName, postLikeDislikes, images, createDate },
  onThumbClick,
}) => {
  const { data: session, status } = useSession();

  const likeCnt = postLikeDislikes.filter((v) => v.type === 'like').length;
  const dislikeCnt = postLikeDislikes.filter((v) => v.type === 'dislike').length;

  const likeMember = postLikeDislikes.find(
    (v) => v.type === 'like' && v.memberUniqueKey === session.user.id,
  );
  const dislikeMember = postLikeDislikes.find(
    (v) => v.type === 'dislike' && v.memberUniqueKey === session.user.id,
  );

  const existThumb = useCallback(
    (fn) => {
      if (likeMember || dislikeMember) {
        setRequestError('이미 눌렀어요.');
        setRequestStatus('error');
        return false;
      }
      fn();
    },
    [dislikeMember, likeMember],
  );

  const [requestStatus, setRequestStatus] = useState('');
  const [requestError, setRequestError] = useState('');

  const notification = useCallback(
    (requestStatus) => {
      const result = {
        pending: {
          status: 'pending',
          title: 'sending message',
          message: 'message is on its way',
        },
        success: {
          status: 'success',
          title: 'success',
          message: 'message success',
        },
        error: {
          status: 'error',
          title: 'Error',
          message: requestError,
        },
      };
      return result[requestStatus] ?? result.pending;
    },
    [requestError],
  );

  useEffect(() => {
    if (requestStatus === 'success' || requestStatus === 'error') {
      const timer = setTimeout(() => {
        setRequestStatus(null);
        setRequestError(null);
      }, 3000);

      return () => clearTimeout(timer);
    }
  }, [requestStatus]);

  return (
    <Wrapper>
      <li className={classes.post}>
        <div className={classes.image}>
          <ImageSwiper imagePaths={images.map((v) => v.src)} />
        </div>
        <div className={classes.content}>
          <ContentWrapper>
            <div className="top">
              <h4>{nickName}</h4>
              <time>{createDate}</time>
            </div>
            <p className={'bottom'}>
              {Children.toArray(
                content.split(/(#[^\s#]+)/g).map((v, i) => {
                  if (v.match(/(#[^\s#]+)/g)) {
                    return (
                      // <Link href={`/hashtag/${v.slice(1)}`} prefetch={false} key={i}>
                      <Link href={`/`} style={{ color: 'blue', fontWeight: 'bold' }}>
                        {v}
                      </Link>
                    );
                  }
                  return v;
                }),
              )}
            </p>
            <div className="icon">
              <div className={'left'}>
                <span className="material-icons-with-text">
                  <i
                    className="material-icons"
                    onClick={() => existThumb(() => onThumbClick(id, 'like', session.user?.id))}
                  >
                    {likeMember ? 'thumb_up_alt' : 'thumb_up_off_alt'}
                  </i>
                  <div>{likeCnt}</div>
                </span>
                <span className="material-icons-with-text">
                  <i
                    className="material-icons"
                    onClick={() => existThumb(() => onThumbClick(id, 'dislike', session.user?.id))}
                  >
                    {dislikeMember ? 'thumb_down_alt' : 'thumb_down_off_alt'}
                  </i>
                  <div>{dislikeCnt}</div>
                </span>
              </div>
              {/*<span className="material-icons-with-text">*/}
              {/*  <i className="material-icons">delete</i>*/}
              {/*</span>*/}
            </div>
          </ContentWrapper>
        </div>
      </li>
      {requestStatus && <Notification result={notification(requestStatus)} />}
    </Wrapper>
  );
};

const Wrapper = styled.div``;

const ContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;

  h4 {
    font-weight: bold;
  }

  .top {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .bottom {
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
      gap: 15px;
    }
  }

  .material-icons-with-text {
    display: inline-flex;
    align-items: center;
  }
`;

export default PostItem;
