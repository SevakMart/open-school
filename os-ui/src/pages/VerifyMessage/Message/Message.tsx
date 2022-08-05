import { useTranslation } from 'react-i18next';
import Button from '../../../component/Button/Button';
import styles from '../VerifyMessage.module.scss';

const Message = ({ reSend, isVerify }:
  {reSend(arg:string):void, isVerify: boolean }) => {
  const { t } = useTranslation();
  const messageBeforeVerify = t('messages.verificationPageHint');

  const { mainContainer, buttonContainer } = styles;
  return (
    <div className={mainContainer}>
      <div>
        <h4>
          {isVerify && messageBeforeVerify }
        </h4>
      </div>
      <div className={buttonContainer}>
        <Button buttonType="button" buttonClick={reSend}>Re Send</Button>
      </div>
    </div>
  );
};

export default Message;
