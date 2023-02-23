import ReactDOM from 'react-dom';

import classes from './notification.module.css';
import { useEffect, useState } from 'react';

function Notification({ result: { title, message, status } }) {
  const [showNotification, setShowNotification] = useState(false);
  let statusClasses = '';

  if (status === 'success') {
    statusClasses = classes.success;
  }

  if (status === 'error') {
    statusClasses = classes.error;
  }

  const cssClasses = `${classes.notification} ${statusClasses} ${
    showNotification ? classes.show : ''
  }`;

  useEffect(() => {
    if (showNotification) {
      const timer = setTimeout(() => {
        setShowNotification(false);
      }, 3000);
      return () => clearTimeout(timer);
    }
  }, [showNotification]);

  return ReactDOM.createPortal(
    <div className={cssClasses}>
      <h2>{title}</h2>
      <p>{message}</p>
    </div>,
    document.getElementById('notifications'),
  );
}

export default Notification;
