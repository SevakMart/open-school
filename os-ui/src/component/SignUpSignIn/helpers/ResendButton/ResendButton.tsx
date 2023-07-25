import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import Notification from '../Notification/Notification';
import './resendButton.scss';

const ResendButton = ({ resendEmail }: { resendEmail: (() => void) | undefined }) => {
  const [isDisable, setIsDisable] = useState(false);
  const [showNotification, setShowNotification] = useState(false);
  const message = 'Resend successful!';
  const { t } = useTranslation();
  const handleClick = () => {
    resendEmail && resendEmail();
    setShowNotification(true);
    setTimeout(() => {
      setShowNotification(false);
      setIsDisable(true);
    }, 3000);
  };

  return (
    <div className="notification_box">
      <div className="resendLinkContainer">
        <button
          type="button"
          disabled={isDisable}
          className="resendLink"
          onClick={handleClick}
        >
          {t(`${isDisable ? 'Code has been sent to your mail' : 'button.resetPsd.resendEmail'}`)}
        </button>
        {showNotification && (
          <Notification
            message={message}
            onClose={() => setShowNotification(false)}
          />
        )}
      </div>
    </div>
  );
};

export default ResendButton;
