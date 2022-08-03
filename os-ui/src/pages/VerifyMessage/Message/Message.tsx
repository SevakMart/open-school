import Button from '../../../component/Button/Button';
import styles from '../VerifyMessage.module.scss';

const Message = ({ reSend, isVerify }:
  {reSend(arg:string):void, isVerify: boolean }) => {
  const messageBeforeVerify = 'Go to in your registered email and verify, If you haven\'t received a verification email within a few minutes, click the "Resend" button to receive another one.';

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
