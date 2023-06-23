import React, { useState, useEffect, useRef } from 'react';
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

  const handleCloseIconClick = (event: React.MouseEvent) => {
    event.stopPropagation();
    handleShareIconClick();
  };

  const copyLinkToClipboard = () => {
    const link = `${window.location.origin}/userCourse/modulOverview/${courseId}`;
    const textField = document.createElement('textarea');
    textField.innerText = link;
    document.body.appendChild(textField);
    textField.select();
    document.execCommand('copy');
    textField.remove();
    setIsLinkCopied(true);
    setCopyTimeoutId(window.setTimeout(() => setIsLinkCopied(false), 5000));
  };

  const popupRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutsidePopup = (event: MouseEvent) => {
      const popup = popupRef.current;
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

  const linkMaxLength = 50;
  const linkText = `${window.location.origin}/userCourse/modulOverview/${courseId}`;
  const truncatedLinkText = linkText.length > linkMaxLength ? `${linkText.substring(0, linkMaxLength)}...` : linkText;

  return (
    <div className={shareButton}>
      <img
        className={shareIcon}
        src={ShareIcon}
        onClick={handleShareIconClick}
        alt={t('shareButton.alt')}
      />
      {showShareLink && (
        <div ref={popupRef} className={sharePopup}>
          <div className={linkContainer}>
            <p className={link} onClick={copyLinkToClipboard} title={linkText}>
              {truncatedLinkText}
            </p>
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
            <div className={closeIcon} onClick={handleCloseIconClick}>
              <CloseIcon />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ShareButton;
