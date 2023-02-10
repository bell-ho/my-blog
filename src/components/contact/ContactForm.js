import { useCallback, useEffect, useState } from 'react';
import classes from './contact-form.module.css';
import { axios } from '@/util/axios';
import Notification from '@/components/ui/notification';
const ContactForm = () => {
  const [enteredEmail, setEnteredEmail] = useState('');
  const [enteredName, setEnteredName] = useState('');
  const [enteredMessage, setEnteredMessage] = useState('');
  const [requestStatus, setRequestStatus] = useState(''); // 'pending', 'success', 'error'
  const [requestError, setRequestError] = useState('');

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

    try {
      await axios.post(`/api/contact`, {
        email: enteredEmail,
        name: enteredName,
        message: enteredMessage,
      });
      setRequestStatus('success');
      setEnteredMessage('');
      setEnteredName('');
      setEnteredEmail('');
    } catch (e) {
      setRequestError(e.message);
      setRequestStatus('error');
    }
  };

  return (
    <section className={classes.contact}>
      <h1>Help?</h1>
      <form className={classes.form} onSubmit={sendMessageHandler}>
        <div className={classes.controls}>
          <div className={classes.control}>
            <label htmlFor="email">email</label>
            <input
              type="email"
              id={'email'}
              required
              value={enteredEmail}
              onChange={(e) => setEnteredEmail(e.target.value)}
            />
          </div>
          <div className={classes.control}>
            <label htmlFor="name">name</label>
            <input
              type="text"
              id={'name'}
              required
              value={enteredName}
              onChange={(e) => setEnteredName(e.target.value)}
            />
          </div>
        </div>

        <div className={classes.control}>
          <label htmlFor="message">message</label>
          <textarea
            id={'message'}
            rows={5}
            required
            value={enteredMessage}
            onChange={(e) => setEnteredMessage(e.target.value)}
          />
        </div>

        <div className={classes.actions}>
          <button>Send Message</button>
        </div>
      </form>

      {requestStatus && <Notification result={notification(requestStatus)}></Notification>}
    </section>
  );
};
export default ContactForm;
