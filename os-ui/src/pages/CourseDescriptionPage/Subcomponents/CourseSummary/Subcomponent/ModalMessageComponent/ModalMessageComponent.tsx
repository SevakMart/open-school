import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { closeModal } from '../../../../../../redux/Slices/PortalOpenStatus';
import EnrollIcon from '../../../../../../assets/svg/Enroll.svg';
import Button from '../../../../../../component/Button/Button';
import styles from './ModalMessageComponent.module.scss';
import CloseIcon from '../../../../../../icons/Close';
import succesMessage from '../../../../../../assets/svg/SuccessMessage.svg';

type Props = {
	courseId: number;
  };

const EnrolledSuccessMessage = ({ courseId }: Props) => {
  const {
    mainContainer, mainContent, textContent, buttonContainer, closeIcon, successMessage, enrollIcon,
  } = styles;
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const handleClosePortal = () => {
    dispatch(closeModal());
  };

  const handleStartEnrolledCourse = () => {
    dispatch(closeModal());
    navigate(`/userCourse/modulOverview/${courseId}`);
  };

  return (
    <div className={mainContainer}>
      <div className={mainContent}>
        <img className={successMessage} src={succesMessage} alt="success" />
        <div className={closeIcon} onClick={handleClosePortal}><CloseIcon /></div>
        <img className={enrollIcon} src={EnrollIcon} alt="Enrolled" />
        <div className={textContent}>
          <h2>
            {t('string.courseDescriptionPage.title.modalSuccessTitle')}
            !
          </h2>
          <p>
            {t('Your Have Enrolled To The Course!')}
          </p>
        </div>
        <div className={buttonContainer}>
          <Button.MainButton className={['startEnrolledCourse']} onClick={handleStartEnrolledCourse}>
            {t('button.courseDescriptionPage.modalStartCourse')}
          </Button.MainButton>
        </div>
      </div>
    </div>
  );
};
export default EnrolledSuccessMessage;
