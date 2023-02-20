import { useCallback, useEffect, useRef, useState } from 'react';
import classes from './contact-form.module.css';
import { axios } from '@/util/axios';
import Notification from '@/components/ui/notification';
import styled from '@emotion/styled';

const ContactForm = () => {
  const inputRef = useRef(null);

  const handleButtonClick = useCallback((e) => {
    inputRef.current.click();
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

  const sendMessageHandler = async (e) => {
    e.preventDefault();

    // try {
    //   await axios.post(`/api/contact`, {});
    //   setRequestStatus('success');
    // } catch (e) {
    //   setRequestError(e.message);
    //   setRequestStatus('error');
    // }
  };

  return (
    <section className={classes.contact}>
      <form className={classes.form} onSubmit={sendMessageHandler}>
        <div className={classes.controls}>
          <div className={classes.control}>
            <label htmlFor="tag">#tag</label>
            <input type="text" id={'tag'} />
          </div>
        </div>

        <ButtonWrapper>
          <div className={classes.control}>
            <button onClick={handleButtonClick}>이미지 업로드</button>
            <input
              id={'images'}
              type="file"
              accept="image/*"
              ref={inputRef}
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
