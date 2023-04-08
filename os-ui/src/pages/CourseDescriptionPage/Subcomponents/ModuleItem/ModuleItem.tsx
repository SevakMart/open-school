import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import ArrowRightIcon from '../../../../assets/svg/ArrowRight.svg';
import EllipseIcon from '../../../../assets/svg/Ellipse.svg';
import ClockIcon from '../../../../assets/svg/ClockIcon.svg';
import { CourseDescriptionType } from '../../../../types/CourseTypes';
import styles from './ModuleItem.module.scss';

type Module=CourseDescriptionType['modules'][number]
const ModuleItem = ({ moduleInfo }:{moduleInfo:Module}) => {
  const { t } = useTranslation();
  const [moduleListIsOpen, setModuleListIsOpen] = useState(false);
  const {
    mainContainer, mainContent, ellipseIcon, remainingTime, remainingTimeContainer, remainingTimeContent, titleAndEllipseIcon, titleAndIconWrapper, moduleTitle,
    chevronIsOpen, chevronIsClosed, moduleDescriptionIsOpen, moduleDescriptionIsClosed,
    moduleItemLink,
  } = styles;
  const openModuleList = () => {
    setModuleListIsOpen((prevState) => !prevState);
  };

  const handleClick = () => {
    window.open(moduleInfo.moduleItemSet[0].link, '_blank');
  };

  return (
    <div className={mainContainer}>
      <div className={mainContent}>
        <div className={titleAndEllipseIcon}>
          <div className={titleAndIconWrapper}>
            <img className={ellipseIcon} src={EllipseIcon} alt="ellipse icon" />
            <p className={moduleTitle}>{t(moduleInfo.description)}</p>
          </div>
          <div className={remainingTimeContainer}>
            <img className={remainingTime} src={ClockIcon} alt="clock icon" />
            <p data-testid={remainingTime} className={remainingTimeContent}>
              {t(`${moduleInfo.moduleItemSet[0].estimatedTime} hour`)}
            </p>
          </div>
        </div>
        <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
      </div>
      <div className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed} onClick={openModuleList}>
        <p>
          {t(`${moduleInfo.moduleItemSet[0].moduleItemType} : `)}
          <button
            type="button"
            className={moduleItemLink}
            onClick={handleClick}
          >
            {moduleInfo.moduleItemSet[0].link}
          </button>
        </p>
      </div>
    </div>
  );
};

export default ModuleItem;
