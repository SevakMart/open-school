import { createPortal } from 'react-dom';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { closeModal } from '../../../../redux/Slices/PortalOpenStatus';
import styles from './FormPortal.module.scss';

const portalElement = document.createElement('div');
portalElement.setAttribute('id', 'formPortal');
document.body.appendChild(portalElement);

export const FormPortal = ({ isOpen, children }:{isOpen:boolean, children:any}) => {
  if (!isOpen) return null;
  const { overlayStyles, modalStyles } = styles;
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const handleOverlayClick = () => {
    dispatch(closeModal());
    navigate('/homepage');
  };

  return createPortal(
    <>
      <div className={overlayStyles} onClick={handleOverlayClick} />
      <div className={modalStyles}>
        {children}
      </div>
    </>,
    portalElement,
  );
};
