import styles from '../VerifyMessage.module.scss';

const Message = ({ isVerify }:
  { isVerify: boolean }) => {
  const messageBeforeVerify = 'Go to in your registered email and verify';

  const { mainContainer, buttonContainer } = styles;
  return (
    <div className={mainContainer}>
      <div>
        <h4>
          {isVerify && messageBeforeVerify }
        </h4>
      </div>
      <div className={buttonContainer} />
    </div>
  );
};

export default Message;
