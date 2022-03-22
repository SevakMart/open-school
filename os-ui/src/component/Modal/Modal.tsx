import ReactDOM from 'react-dom';
import SignUp from '../SignUp/SignUp';
import styles from './Modal.module.scss';
/* const modalDiv = document.createElement('div');
  modalDiv.setAttribute('id', 'modal-root');
  document.body.appendChild(modalDiv); */

const Modal = ({ buttonType, children }:{buttonType:string, children:any}) => {
  const { mainContainer } = styles;
  return (
    <div className={mainContainer}>
      {/*
      buttonType === 'signUp'
        ? ReactDOM.createPortal(children, document.getElementById('modal-root')!) : null
  */}
      {children}
    </div>
  );
};
export default Modal;
