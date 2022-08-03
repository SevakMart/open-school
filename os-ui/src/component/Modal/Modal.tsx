import { createPortal } from 'react-dom';
import styles from './Modal.module.scss';

const portalElement = document.createElement('div');
portalElement.setAttribute('id', 'portalRoot');
document.body.appendChild(portalElement);

const Modal = ({ children }:{children:any}) => {
  const { mainPortalContainer } = styles;
  /* eslint-disable-next-line max-len */
  const modalElement = <div className={mainPortalContainer}>{children}</div>;
  return (
    createPortal(modalElement, portalElement)
  );
};
export default Modal;
