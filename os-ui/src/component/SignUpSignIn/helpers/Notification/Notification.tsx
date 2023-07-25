import React, { useState, useEffect } from 'react';
import './notification.scss';

interface NotificationProps {
  message: string;
  onClose: () => void;
}

const Notification: React.FC<NotificationProps> = ({ message, onClose }) => {
  const [isVisible, setIsVisible] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setIsVisible(false);
      onClose();
    }, 3000);

    return () => {
      clearTimeout(timer);
    };
  }, [onClose]);

  const handleClose = () => {
    setIsVisible(false);
    onClose();
  };

  return isVisible ? (
    <div className="notification">
      <div className="message">{message}</div>
      <div className="close-notification" onClick={handleClose}>
        ✖
      </div>
    </div>
  ) : null;
};

export default Notification;
