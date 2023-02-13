import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import styles from './UncompletedVerificationPage.module.scss';

interface Props {
  email: string;
}

const UncompletedVerificatonPage: React.FC<Props> = ({ email }) => {
  const { t } = useTranslation();
  const [isVerified, setIsVerified] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [showPopup, setShowPopup] = useState(false);
  const [error, setError] = useState('');

  const checkVerificationStatus = async () => {
    // Make a request to the backend to check if the account is verified or not
    const response = await fetch(`/api/check-verification-status?email=${email}`);
    const data = await response.json();

    if (!data.isVerified) {
      setShowPopup(true);
    } else {
      setIsVerified(true);
    }
  };

  useEffect(() => {
    checkVerificationStatus();
  }, []);

  const resendVerificationEmail = async () => {
    setIsLoading(true);
    setError('');

    // Make a request to the backend to resend the verification email
    const response = await fetch(`/api/resend-verification-email?email=${email}`);
    const data = await response.json();

    if (data.error) {
      setError(data.error);
    } else {
      setIsLoading(false);
    }
  };

  if (!showPopup) {
    return null;
  }

  const { mainContainer, buttonContainer } = styles;
  return (
    <div className={mainContainer}>
      {error && (
        <div className="error">{error}</div>
      )}
      {isLoading ? (
        <div className="loading">{t('Loading...')}</div>
      ) : (
        <>
          <div className="message">{t('Verify your account')}</div>
          <button type="button" className={buttonContainer} onClick={resendVerificationEmail}>{t('Resend verification email')}</button>
        </>
      )}
    </div>
  );
};

export default UncompletedVerificatonPage;
