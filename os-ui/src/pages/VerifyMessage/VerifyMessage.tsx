import styles from '../../component/SignIn/SignIn.module.scss';
import Message from './Message/Message';

const VerifyMessage = ({ handleSignInClicks }:{handleSignInClicks(arg:string):void}) => {
  const {
    mainContainer, formContainer,
  } = styles;

  const reSend = () => {
    // To Do
  };

  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        <Message isVerify reSend={reSend} goToLogin={() => handleSignInClicks('signIn')} />
      </div>
    </div>
  );
};
export default VerifyMessage;
