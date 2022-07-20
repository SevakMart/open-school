import { createPortal } from 'react-dom';
import styles from './Modal.module.scss';

const portalElement = document.createElement('div');
portalElement.setAttribute('id', 'portalRoot');

const Modal = ({ children }:{children:any}) => {
  const { mainPortalContainer } = styles;
  const modalElement = <div className={mainPortalContainer}>{children}</div>;
  return (
    createPortal(modalElement, portalElement)
  );
};
export default Modal;
