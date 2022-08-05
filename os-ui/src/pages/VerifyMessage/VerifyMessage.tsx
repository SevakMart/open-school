import { useSelector } from 'react-redux';
import styles from '../../component/SignIn/SignIn.module.scss';
import { RootState } from '../../redux/Store';
import authService from '../../services/authService';
import Message from './Message/Message';

const VerifyMessage = () => {
  const {
    mainContainer, formContainer,
  } = styles;

  const userInfo = useSelector<RootState>((state) => state.userInfo);

  const reSend = () => {
    authService.resendVerificationEmail((userInfo as any).userId);
  };

  return (
    <div className={mainContainer}>
      <div className={formContainer}>
        <Message isVerify reSend={reSend} />
      </div>
    </div>
  );
};
export default VerifyMessage;
