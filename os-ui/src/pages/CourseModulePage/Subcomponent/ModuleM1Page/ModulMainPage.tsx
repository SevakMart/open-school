/* eslint-disable jsx-a11y/anchor-is-valid */
/* eslint-disable react/jsx-key */
/* eslint-disable react/button-has-type */
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import Dropdown from '../../../../component/Dropdown/Dropdown';
import styles from './ModuleM1Page.module.scss';
import ModuleItem from '../../../CourseDescriptionPage/Subcomponents/ModuleItem/ModuleItem';
import ArrowRightIcon from '../../../../assets/svg/ArrowRight.svg';

interface ModuleM1MainPage {
  value: string,
  handleChangeValue: (a:string) => void,
}

const ModuleMainPage = ({ value, handleChangeValue }:ModuleM1MainPage) => {
  const [moduleListIsOpen, setModuleListIsOpen] = useState(false);
  const { t } = useTranslation();
  const openModuleList = () => {
    setModuleListIsOpen((prevState) => !prevState);
  };
  const {
    chevronIsOpen, chevronIsClosed, moduleDescriptionIsOpen, moduleDescriptionIsClosed,
  } = styles;

  return (
    <>
      <div className={styles.Main_container}>
        <h1 className={styles.Main_M1_header}>{t('Module 1 Overview')}</h1>
        <p className={styles.Main_SumTime}>{t('Estimated Time: ')}</p>
        <div>
          <Dropdown
            open={value}
            trigger={(
              <button className={styles.Course_Material} onChange={() => handleChangeValue(value)}>
                {t('Course Materials')}
                <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
                {' '}
              </button>
)}
            menu={[
              <div
                className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed}
              >
                <ModuleItem moduleInfo={{
                  title: 'Video: Here goes the video name',
                  description: 'Here is the Video Link...',
                  moduleItemSet: [],
                  link: <a href="https://www.pluralsight.com/guides/understanding-links-in-reactjs">Link</a>,
                }}
                />
                <ModuleItem moduleInfo={{
                  title: 'Reading: Here goes book name',
                  description: 'Here is documentation...',
                  moduleItemSet: [],
                }}
                />
              </div>,
            ]}
          />
          <Dropdown
            open={value}
            trigger={(
              <button className={styles.Course_Material}>
                {t('Exercises and Practice Sessions')}
                <img className={chevronIsClosed} src={ArrowRightIcon} alt="chevron" />
                {' '}
              </button>
)}
            menu={[
            ]}
          />
          <Dropdown
            open={value}
            trigger={(
              <button className={styles.Course_Material}>
                {t('Course Description')}
                <img className={chevronIsClosed} src={ArrowRightIcon} alt="chevron" />
                {' '}
              </button>
)}
            menu={[
            ]}
          />
        </div>
      </div>
    </>
  );
};

export default ModuleMainPage;
