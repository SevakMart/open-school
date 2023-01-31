import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import ArrowRightIcon from '../../../../assets/svg/ArrowRight.svg';
import EllipseIcon from '../../../../assets/svg/Ellipse.svg';
import { CourseDescriptionType } from '../../../../types/CourseTypes';
import styles from './ModuleItem.module.scss';

type Module=CourseDescriptionType['modules'][number]
const ModuleItem = ({ moduleInfo }:{moduleInfo:Module}) => {
  const { t } = useTranslation();
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
          <p className={moduleTitle}>{t(moduleInfo.title)}</p>
        </div>
        <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
      </div>
      <div className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed}>
        <p>{t(moduleInfo.description)}</p>
        <button
          type="button"
          style={{
            backgroundColor: 'transparent',
            border: 'none',
            color: 'blue',
            textDecoration: 'underline',
            cursor: 'pointer',
          }}
        >
          {moduleInfo.link}
        </button>
      </div>
    </div>
  );
};
export default ModuleItem;
