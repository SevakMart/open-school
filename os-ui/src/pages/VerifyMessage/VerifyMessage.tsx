import { useSelector } from 'react-redux';

import { RootState } from '../../redux/Store';
import authService from '../../services/authService';
import Message from './Message/Message';

const VerifyMessage = ({ handleSignInClicks }:{handleSignInClicks(arg:string):void}) => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);

  const reSend = () => {
    authService.resendVerificationEmail((userInfo as any).userId);
  };

  return (
    <div>
      <div>
        <Message isVerify reSend={reSend} goToLogin={() => handleSignInClicks('signIn')} />
      </div>
    </div>
  );
};
export default VerifyMessage;
