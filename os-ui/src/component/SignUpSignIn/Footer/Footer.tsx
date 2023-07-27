import { useDispatch } from 'react-redux';
import Button from '../../Button/Button';
import { Types } from '../../../types/types';
import { openModal } from '../../../redux/Slices/PortalOpenStatus';
import styles from './Footer.module.scss';

/* eslint-disable max-len */
const Footer = ({ mainText, buttonType, buttonText }:{mainText:string, buttonType:string, buttonText:string}) => {
  const { alreadyHaveAccount } = styles;
  const dispatch = useDispatch();

  const handleSignIn = () => {
    dispatch(openModal({ buttonType }));
  };

  const handleSignUp = () => {
    dispatch(openModal({ buttonType }));
  };

  return (
    <>
      <div className={alreadyHaveAccount}>
        {mainText}
        <span>
          {buttonType === Types.Button.SIGN_IN && <Button.SignInButton className={['account']} onClick={handleSignIn}>{buttonText}</Button.SignInButton>}
          {buttonType === Types.Button.SIGN_UP && <Button.SignUpButton className={['account']} onClick={handleSignUp}>{buttonText}</Button.SignUpButton>}
        </span>
      </div>
    </>
  );
};
export default Footer;
