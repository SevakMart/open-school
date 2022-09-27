import { useTranslation } from 'react-i18next';
import { useNavigate, useLocation } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { closeModal } from '../../../../../../redux/Slices/PortalOpenStatus';
import EnrollIcon from '../../../../../../assets/svg/Enroll.svg';
import Button from '../../../../../../component/Button/Button';
import styles from './ModalMessageComponent.module.scss';

const EnrolledSuccessMessage = () => {
  const {
    mainContainer, mainContent, textContent, buttonContainer,
  } = styles;
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const params = new URLSearchParams(location.search);

  const handleStartEnrolledCourse = () => {
    dispatch(closeModal());
    params.set('enrolled', 'true');
    navigate(`${location.pathname}?${params}`);
  };

  return (
    <div className={mainContainer}>
      <div className={mainContent}>
        <img src={EnrollIcon} alt="Enrolled" />
        <div className={textContent}>
          <h2>{t('string.courseDescription.modal.congratsHeader')}</h2>
          <p>{t('string.courseDescription.modal.mainMessage')}</p>
        </div>
        <div className={buttonContainer}>
          <Button.MainButton className={['startEnrolledCourse']} onClick={handleStartEnrolledCourse}>
            {t('button.startCourse')}
          </Button.MainButton>
        </div>
      </div>
    </div>
  );
};
export default EnrolledSuccessMessage;
