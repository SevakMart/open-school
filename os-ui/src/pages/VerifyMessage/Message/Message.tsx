import Button from '../../../component/Button/Button';
import styles from '../VerifyMessage.module.scss';

const Message = ({ goToLogin, reSend, isVerify }:
  {goToLogin(arg:string):void, reSend(arg:string):void, isVerify: boolean }) => {
  const messageBeforeVerify = 'Go to in your registered email and verify';

  const { mainContainer, buttonContainer } = styles;
  return (
    <div className={mainContainer}>
      <div>
        <h4>
          {isVerify && messageBeforeVerify }
        </h4>
      </div>
      <div className={buttonContainer}>
        {/* <Button buttonType="button" buttonClick={reSend}>Re Send</Button>
        <Button buttonType="button" buttonClick={goToLogin}>Login</Button> */}
      </div>
    </div>
  );
};

export default Message;
