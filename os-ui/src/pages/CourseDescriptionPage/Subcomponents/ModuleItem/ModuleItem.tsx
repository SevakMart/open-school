import { useState } from 'react';
import ArrowRightIcon from '../../../../assets/svg/ArrowRight.svg';
import StraightLine from '../../../../assets/svg/Vector.svg';
import { CourseDescriptionType } from '../../../../types/CourseDescriptionType';
import styles from './ModuleItem.module.scss';

type Module=CourseDescriptionType['modules'][number]

const ModuleItem = ({ moduleInfo }:{moduleInfo:Module}) => {
  const [moduleListIsOpen, setModuleListIsOpen] = useState(false);
  const {
    mainContainer, vector, mainContent, ellipseIcon, titleAndEllipseIcon,
    chevronIsOpen, chevronIsClosed, moduleDescriptionIsOpen, moduleDescriptionIsClosed,
  } = styles;
  const openModuleList = () => {
    setModuleListIsOpen((prevState) => !prevState);
  };

  return (
    <div className={mainContainer}>
      <img className={vector} src={StraightLine} alt="vector" />
      <div className={mainContent}>
        <div className={titleAndEllipseIcon}>
          <div className={ellipseIcon} />
          <p>{moduleInfo.title}</p>
        </div>
        <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
      </div>
      <div className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed}>
        <p>This is where the module description will be implemented</p>
      </div>
    </div>
  );
};
export default ModuleItem;
