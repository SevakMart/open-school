/* eslint-disable jsx-a11y/anchor-is-valid */
import { useState } from 'react';
import ArrowRightIcon from '../../../../assets/svg/ArrowRight.svg';
import EllipseIcon from '../../../../assets/svg/Ellipse.svg';
import { CourseDescriptionType } from '../../../../types/CourseTypes';
import styles from './ModuleItem.module.scss';

type Module=CourseDescriptionType['modules'][number]
const ModuleItem = ({ moduleInfo }:{moduleInfo:Module}) => {
  const [moduleListIsOpen, setModuleListIsOpen] = useState(false);
  const {
    mainContainer, mainContent, ellipseIcon, titleAndEllipseIcon, moduleTitle,
    chevronIsOpen, chevronIsClosed, moduleDescriptionIsOpen, moduleDescriptionIsClosed,
  } = styles;
  const openModuleList = () => {
    setModuleListIsOpen((prevState) => !prevState);
  };

  return (
    <div className={mainContainer}>
      <div className={mainContent}>
        <div className={titleAndEllipseIcon}>
          <img className={ellipseIcon} src={EllipseIcon} alt="ellipse icon" />
          <p className={moduleTitle}>{moduleInfo.title}</p>
        </div>
        <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
      </div>
      <div className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed}>
        <p>{moduleInfo.description}</p>
        <a>{moduleInfo.link}</a>
      </div>
    </div>
  );
};
export default ModuleItem;
