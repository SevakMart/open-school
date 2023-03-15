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

interface ModuleMainPageProps {
  modules: {
    link?: any,
    title: string,
    description: string,
    moduleItemSet: { [index: string]: string }[]
  }[];
  duration: number;
}

const ModuleMainPage = ({
  modules, duration, value, handleChangeValue,
}: ModuleMainPageProps & ModuleM1MainPage) => {
  const [moduleListIsOpen, setModuleListIsOpen] = useState(true);
  const { t } = useTranslation();
  const openModuleList = () => {
    setModuleListIsOpen((prevState) => !prevState);
  };
  const {
    chevronIsOpen, chevronIsClosed, moduleDescriptionIsOpen, moduleDescriptionIsClosed,
  } = styles;
  const selectedModule = modules.find((module) => module.title === value) || modules[0];

  return (
    <>
      <div className={styles.Main_container}>
        <h1 className={styles.Main_M1_header}>{t(`${selectedModule?.title} Overview`)}</h1>
        <p className={styles.Main_SumTime}>{t(`Estimated Time: ${duration} hour`)}</p>
        <div>
          <Dropdown
            open={value}
            trigger={(
              <button type="button" className={styles.Course_Material} onChange={() => handleChangeValue(value)}>
                {t('Course Materials')}
                <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
                {' '}
              </button>
)}
            menu={[
              <div className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed} key={modules[0].title}>
                {modules && modules.map((module) => (module.title === value ? <ModuleItem key={module.title} moduleInfo={module} /> : null))}
              </div>,
            ]}
          />
          <Dropdown
            open={value}
            trigger={(
              <button type="button" className={styles.Course_Material}>
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
              <button type="button" className={styles.Course_Material}>
                {t('Quizes and Assignments')}
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
