import { useCallback, useEffect, useRef, useState } from 'react';
import classes from './contact-form.module.css';
import Notification from '@/components/ui/notification';
import styled from '@emotion/styled';
import { useSession } from 'next-auth/react';
import { createPost } from '@/pages/api/post/post';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKey } from '@/react-query/constants';

const ContactForm = () => {
  const queryClient = useQueryClient();

  const { data: session, status } = useSession();

  const imageInputRef = useRef(null);
  const contentInputRef = useRef(null);

  const imageButtonClick = useCallback((e) => {
    imageInputRef.current.click();
  }, []);

  const onUploadImage = useCallback((e) => {
    const imageFormData = new FormData();
    const reg = /(.*?)\.(jpg|jpeg|png|gif|bmp)$/;
    [].forEach.call(e.target.files, (f) => {
      if (f.name.match(reg)) {
        imageFormData.append('image', f);
      }
    });
  }, []);

  const [requestStatus, setRequestStatus] = useState(''); // 'pending', 'success', 'error'
  const [requestError, setRequestError] = useState('');
  const [imagePaths, setImagePaths] = useState([]);

  useEffect(() => {
    if (requestStatus === 'success' || requestStatus === 'error') {
      const timer = setTimeout(() => {
        setRequestStatus(null);
        setRequestError(null);
      }, 3000);

      return () => clearTimeout(timer);
    }
  }, [requestStatus]);

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
          title: 'sending success',
          message: 'message success',
        },
        error: {
          status: 'error',
          title: 'sending error',
          message: requestError,
        },
      };
      return result[requestStatus] ?? result.pending;
    },
    [requestError],
  );

  const createPostMutation = useMutation((params) => createPost(params), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.posts]);
      contentInputRef.current.value = '';
      setRequestStatus('success');
    },
    onError: (e) => {
      setRequestError(e.message);
      contentInputRef.current.value = '';
      setRequestStatus('error');
    },
  });

  const sendHandler = useCallback(
    async (e) => {
      e.preventDefault();

      const content = contentInputRef.current.value;

      const hashtags = Array.from(
        new Set(content.match(/#[^\s#]+/g)?.map((v) => v.slice(1).toLowerCase())),
      );

      const params = {
        member: {
          uniqueKey: session?.user?.id,
        },
        content,
        hashtags,
      };

      await createPostMutation.mutate(params);
    },
    [createPostMutation, session?.user?.id],
  );

  return (
    <section className={classes.contact}>
      <form className={classes.form} onSubmit={sendHandler}>
        <div className={classes.controls}>
          <div className={classes.control}>
            <label htmlFor="content">Content</label>
            <input ref={contentInputRef} type="text" id={'content'} />
          </div>
        </div>

        <ButtonWrapper>
          <div className={classes.control}>
            <button onClick={imageButtonClick}>이미지 업로드</button>
            <input
              id={'images'}
              type="file"
              accept="image/*"
              ref={imageInputRef}
              onChange={onUploadImage}
              style={{ display: 'none' }}
            />
          </div>
          <div className={classes.actions}>
            <button>등록</button>
          </div>
        </ButtonWrapper>
      </form>

      {requestStatus && <Notification result={notification(requestStatus)}></Notification>}
    </section>
  );
};
const ButtonWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`;
export default ContactForm;
