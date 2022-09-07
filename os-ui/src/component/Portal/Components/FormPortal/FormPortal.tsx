import { createPortal } from 'react-dom';
import styles from './FormPortal.module.scss';

const portalElement = document.createElement('div');
portalElement.setAttribute('id', 'formPortal');
document.body.appendChild(portalElement);

export const FormPortal = ({ isOpen, children }:{isOpen:boolean, children:any}) => {
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
