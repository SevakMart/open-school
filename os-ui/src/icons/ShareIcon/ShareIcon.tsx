import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import ShareIcon from '../../assets/svg/ShareIcon.svg';
import CheckIcon from '../../assets/svg/CheckIcon.svg';
import CopyIcon from '../../assets/svg/CopyIcon.svg';
import styles from './ShareIcon.module.scss';
import CloseIcon from '../Close';

const ShareButton = ({ courseId }: { courseId: number }) => {
  const [showShareLink, setShowShareLink] = useState(false);
  const [isLinkCopied, setIsLinkCopied] = useState(false);
  const [copyTimeoutId, setCopyTimeoutId] = useState<number | undefined>(undefined);
  const { t } = useTranslation();
  const {
	  shareButton, shareIcon, sharePopup, linkContainer, link, closeIcon, copyIcon, copiedIcon, tooltip,
  } = styles;

  const handleShareIconClick = () => {
	  setShowShareLink(!showShareLink);
	  setIsLinkCopied(false);
  };

  const copyLinkToClipboard = () => {
	  const link = `${window.location.origin}/userCourse/modulOverview/${courseId}`;
	  navigator.clipboard.writeText(link);
	  setIsLinkCopied(true);
	  setCopyTimeoutId(window.setTimeout(() => setIsLinkCopied(false), 5000));
  };

  useEffect(() => {
	  const handleClickOutsidePopup = (event: MouseEvent) => {
      const popup = document.querySelector(`.${sharePopup}`) as HTMLElement;
      const target = event.target as HTMLElement;
      if (popup && !popup.contains(target)) {
		  handleShareIconClick();
      }
	  };
	  if (showShareLink) {
      document.addEventListener('click', handleClickOutsidePopup);
	  }
	  return () => {
      document.removeEventListener('click', handleClickOutsidePopup);
      if (copyTimeoutId) clearTimeout(copyTimeoutId);
	  };
  }, [showShareLink, copyTimeoutId]);

  return (
    <div className={shareButton}>
      <img
        className={shareIcon}
        src={ShareIcon}
        onClick={handleShareIconClick}
        alt={t('shareButton.alt')}
      />
      {showShareLink && (
      <div className={sharePopup}>
        <div className={closeIcon} onClick={handleShareIconClick}><CloseIcon /></div>
        <div className={linkContainer}>
          <p className={link} onClick={copyLinkToClipboard}>{`${window.location.origin}/userCourse/modulOverview/${courseId}`}</p>
          <button type="button" className={copyIcon} onClick={copyLinkToClipboard}>
            {isLinkCopied ? (
              <>
                <img className={copiedIcon} src={CheckIcon} alt={t('copyIcon.checkAlt')} />
                <span className={tooltip}>{t('Link copied to clipboard!')}</span>
              </>
            ) : (
              <>
                <img src={CopyIcon} alt={t('copyIcon.alt')} />
                <span className={tooltip}>{t('Copy link to clipboard')}</span>
              </>
            )}
          </button>
        </div>
      </div>
      )}
    </div>
  );
};

export default ShareButton;
