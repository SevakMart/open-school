import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation } from 'react-router-dom';
import ArrowRightIcon from '../../../../assets/svg/ArrowRight.svg';
import EllipseIcon from '../../../../assets/svg/Ellipse.svg';
import ClockIcon from '../../../../assets/svg/ClockIcon.svg';
import Vector from '../../../../assets/svg/Vector.svg';
import { CourseDescriptionType } from '../../../../types/CourseTypes';
import styles from './ModuleItem.module.scss';

type Module=CourseDescriptionType['modules'][number]
const ModuleItem = ({ moduleInfo }:{moduleInfo:Module}) => {
  const { t } = useTranslation();
  const [moduleListIsOpen, setModuleListIsOpen] = useState(false);
  const {
    mainContainer, mainContent, ellipseIcon, remainingTime, remainingTimeContainer, remainingTimeContent, titleAndEllipseIcon, titleAndIconWrapper, moduleTitle,
    chevronIsOpen, chevronIsClosed, moduleDescriptionIsOpen, moduleDescriptionIsClosed, moduleDecription,
    moduleItemLink, vector, enrolledMainContainer,
  } = styles;

  const openModuleList = () => {
    setModuleListIsOpen((prevState) => !prevState);
  };

  const handleClick = (link: string) => {
    window.open(link, '_blank');
  };

  const totalEstimatedTime = moduleInfo.moduleItemSet.reduce((total, item) => total + Number(item.estimatedTime), 0);
  const hourText = totalEstimatedTime < 1 ? 'hour' : 'hours';
  const formattedEstimatedTime = totalEstimatedTime.toFixed(1);
  const location = useLocation();

  if (location && location.pathname && location.pathname.match(/^\/userCourse\/modulOverview\/\d+$/)) {
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
            {t(`${formattedEstimatedTime} ${hourText}`)}
          </p>
        </div>
      </div>
      <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
    </div>
    <div className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed} onClick={openModuleList}>
      {moduleInfo.moduleItemSet.map((item, index) => (
        <p key={index}>
          {t(`${item.moduleItemType} : `)}
          <button type="button" className={moduleItemLink} onClick={() => handleClick(item.link)}>
            {item.link}
          </button>
        </p>
      ))}
    </div>
  </div>
	  );
  }
	  return (
  <div className={enrolledMainContainer}>
    <div className={mainContent}>
      <div className={titleAndEllipseIcon}>
        <div className={titleAndIconWrapper}>
          <img className={ellipseIcon} src={EllipseIcon} alt="ellipse icon" />
          <img className={vector} src={Vector} alt="vector" />
          <p className={moduleTitle}>{t(moduleInfo.title)}</p>
        </div>
      </div>
      <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
    </div>
    <div className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed} onClick={openModuleList}>
      <p className={moduleDecription}>{t(moduleInfo.description)}</p>
    </div>
  </div>
	  );
};

export default ModuleItem;
