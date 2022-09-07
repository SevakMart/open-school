import { createPortal } from 'react-dom';
import styles from './ProfilePortal.module.scss';

const portalElement = document.createElement('div');
portalElement.setAttribute('id', 'profilePortal');
document.body.appendChild(portalElement);

export const ProfilePortal = ({ isOpen, children }:{isOpen:boolean, children:any}) => {
  if (!isOpen) return null;
  const { modalStyles } = styles;

  return createPortal(
    <div className={modalStyles}>
      {children}
    </div>,
    portalElement,
  );
};
