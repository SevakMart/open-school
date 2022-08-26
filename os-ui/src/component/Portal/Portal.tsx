import { createPortal } from 'react-dom';
import styles from './Portal.module.scss';

const portalElement = document.createElement('div');
portalElement.setAttribute('id', 'portalRoot');
document.body.appendChild(portalElement);

const Portal = ({ isOpen, children }:{isOpen:boolean, children:any}) => {
  if (!isOpen) return null;
  const { overlayStyles, modalStyles } = styles;

  return createPortal(
    <>
      <div className={overlayStyles} />
      <div className={modalStyles}>
        {children}
      </div>
    </>,
    portalElement,
  );
};
export default Portal;
