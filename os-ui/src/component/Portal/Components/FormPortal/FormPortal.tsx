import { createPortal } from 'react-dom';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import { closeModal } from '../../../../redux/Slices/PortalOpenStatus';
import styles from './FormPortal.module.scss';

const portalElement = document.createElement('div');
portalElement.setAttribute('id', 'formPortal');
document.body.appendChild(portalElement);

export const FormPortal = ({ isOpen, children }:{isOpen:boolean, children:any}) => {
  if (!isOpen) return null;
  const { overlayStyles, modalStyles, enrolledPopup } = styles;
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const isCourseDescriptionPage = location.pathname.match(/^\/userCourse\/\d+$/);
  const handleOverlayClick = () => {
    if (location.pathname === '/homepage/account') {
      dispatch(closeModal());
      navigate('/homepage');
    } else {
      dispatch(closeModal());
    }
  };

  return createPortal(
    <>
      <div className={overlayStyles} onClick={handleOverlayClick} />
      <div className={isCourseDescriptionPage ? enrolledPopup : modalStyles}>
        {children}
      </div>
    </>,
    portalElement,
  );
};
