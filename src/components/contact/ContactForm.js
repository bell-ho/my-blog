import { useCallback, useEffect, useRef, useState } from 'react';
import classes from './contact-form.module.css';
import Notification from '@/components/ui/notification';
import styled from '@emotion/styled';
import { useSession } from 'next-auth/react';
import { createPost } from '@/pages/api/post/post';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKey } from '@/react-query/constants';
import { uploadImages } from '@/util/uploadFileToS3';
import ImageBox from '@/components/contact/ImageBox';
import Checkbox from '@/components/ui/Checkbox';

const ContactForm = () => {
  const queryClient = useQueryClient();
  const [hide, setHide] = useState(false);
  const { data: session, status } = useSession();

  const imageInputRef = useRef(null);
  const contentInputRef = useRef(null);

  const imageButtonClick = useCallback((e) => {
    imageInputRef.current?.click();
  }, []);

  const onUploadImage = useCallback(async (e) => {
    const reg = /(.*?)\.(jpg|jpeg|png|gif|bmp)$/;
    const files = Array.from(e.target.files);

    if (files.some((file) => !file.name.match(reg))) {
      setRequestError('이미지만 올려요.');
      setRequestStatus('error');
      return false;
    }

    const imageUrls = await uploadImages(files);
    setImagePaths((prev) => prev.concat(imageUrls));
  }, []);

  const [requestStatus, setRequestStatus] = useState(''); // 'pending', 'success', 'error'
  const [requestError, setRequestError] = useState('');
  const [requestSuccess, setRequestSuccess] = useState('');
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
          title: 'Success',
          message: requestSuccess,
        },
        error: {
          status: 'error',
          title: 'Error',
          message: requestError,
        },
      };
      return result[requestStatus] ?? result.pending;
    },
    [requestError, requestSuccess],
  );

  const createPostMutation = useMutation((params) => createPost(params), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.posts]);
      setRequestStatus('success');
      setRequestSuccess('등록 완료');
    },
    onError: (e) => {
      setRequestError(e.message);
      setRequestStatus('error');
    },
    onSettled: () => {
      contentInputRef.current.value = '';
      setImagePaths([]);
      setHide(false);
    },
  });

  const sendHandler = useCallback(
    async (e) => {
      e.preventDefault();

      const content = contentInputRef.current.value;

      if (!content || !content.trim()) {
        setRequestError('게시물을 등록해주세요.');
        setRequestStatus('error');
        return;
      }

      if (imagePaths.length > 5) {
        setRequestError('사진은 5장 까지 가능합니다.');
        setRequestStatus('error');
        return;
      }

      const hashtags = Array.from(
        new Set(content.match(/#[^\s#]+/g)?.map((v) => v.slice(1).toLowerCase())),
      );

      const params = {
        member: {
          uniqueKey: session?.user?.id,
        },
        content,
        hashtags,
        images: imagePaths,
        hide,
      };

      await createPostMutation.mutate(params);
    },
    [createPostMutation, hide, imagePaths, session?.user?.id],
  );

  const onRemoveImage = useCallback(
    (index) => () => {
      setImagePaths((prev) => {
        return prev.filter((v, i) => i !== index);
      });
    },
    [],
  );

  return (
    <section className={classes.contact}>
      <form className={classes.form} onSubmit={sendHandler}>
        <button type={'button'} onClick={imageButtonClick}>
          ADD A FILE
        </button>

        <ImageBox onRemoveImage={onRemoveImage} imagePaths={imagePaths} />

        <input
          type="file"
          name="image"
          multiple
          hidden
          accept="image/*"
          ref={imageInputRef}
          onChange={onUploadImage}
        />

        <input
          placeholder={'ex) #해시태그'}
          ref={contentInputRef}
          maxlength="15"
          type="text"
          id={'content'}
        />
        <Checkbox checked={hide} onChange={setHide}>
          숨기기
        </Checkbox>
        <button>등록</button>
      </form>

      {requestStatus && <Notification result={notification(requestStatus)} />}
    </section>
  );
};
const BottomWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 20px;

  button {
    flex-grow: 1;
  }
`;
export default ContactForm;
