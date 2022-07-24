import { useTranslation } from 'react-i18next';
import EnrollIcon from '../../../../../../assets/svg/Enroll.svg';
import styles from './ModalMessageComponent.module.scss';

const ModalMessageComponent = ({ enrollInCourse }:{enrollInCourse():void}) => {
  const { mainContainer, mainContent, textContent } = styles;
  const { t } = useTranslation();

  return (
    <div className={mainContainer}>
      <div className={mainContent}>
        <img src={EnrollIcon} alt="Enrolled" />
        <div className={textContent}>
          <h2>{t('string.courseDescription.modal.congratsHeader')}</h2>
          <p>{t('string.courseDescription.modal.mainMessage')}</p>
        </div>
        <button type="button" onClick={() => enrollInCourse()}>{t('button.startCourse')}</button>
      </div>
    </div>
  );
};
export default ModalMessageComponent;
