import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { openModal } from '../../../redux/Slices/PortalOpenStatus';
import { Types } from '../../../types/types';
import styles from './Success-Message.module.scss';

/* eslint-disable max-len */

const SuccessMessage = ({ message, isSignUpSuccessfulRegistration }:{message:string, isSignUpSuccessfulRegistration:boolean}) => {
  const { mainContainer } = styles;
  const dispatch = useDispatch();

  useEffect(() => {
    let timer:any;
    if (isSignUpSuccessfulRegistration) {
      timer = setTimeout(() => dispatch(openModal(Types.Button.VERIFY)), 3000);
    } else {
      timer = setTimeout(() => dispatch(openModal(Types.Button.RESET_PASSWORD)), 3000);
    }
    return () => clearTimeout(timer);
  }, []);

  return (
    <div className={mainContainer}>
      <h2>{message}</h2>
    </div>
  );
};
export default SuccessMessage;
