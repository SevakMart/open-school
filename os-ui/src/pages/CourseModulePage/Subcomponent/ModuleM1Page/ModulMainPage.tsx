import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import Dropdown from '../../../../component/Dropdown/Dropdown';
import styles from './ModuleM1Page.module.scss';
import ModuleItem from '../../../CourseDescriptionPage/Subcomponents/ModuleItem/ModuleItem';
import ArrowRightIcon from '../../../../assets/svg/ArrowRight.svg';
import { RootState } from '../../../../redux/Store';
import { setValue } from '../../../../redux/Slices/CourseModuleSlice';
import { CourseDescriptionType } from '../../../../types/CourseTypes';

const ModuleMainPage = () => {
  const [moduleListIsOpen, setModuleListIsOpen] = useState(true);
  const { t } = useTranslation();
  const openModuleList = () => {
    setModuleListIsOpen((prevState) => !prevState);
  };

  const dispatch = useDispatch();
  const { value } = useSelector<RootState>((state) => state.courseModule) as { value: string };
  const { entity } = useSelector<RootState>((state) => state.courseDescriptionRequest) as { entity: CourseDescriptionType };
  const {
    chevronIsOpen, chevronIsClosed, moduleDescriptionIsOpen, moduleDescriptionIsClosed,
  } = styles;
  const selectedModule = entity.modules.find((module) => module.title === value) || entity.modules[0];

  const handleChangeValue = (newValue: string) => {
    dispatch(setValue(newValue));
  };

  return (
    <>
      <div className={styles.Main_container}>
        <h1 className={styles.Main_M1_header}>{t(`${selectedModule?.title} Overview`)}</h1>
        <p className={styles.Main_SumTime}>{t(`Estimated Time: ${entity.duration} hour`)}</p>
        <div>
          <Dropdown
            open={value}
            trigger={(
              <button type="button" className={styles.Course_Material} onClick={() => handleChangeValue(value)}>
                {t('Course Materials')}
                <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
                {' '}
              </button>
            )}
            menu={[
              <div className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed} key={entity.modules[0].title}>
                {entity.modules && entity.modules.map((module) => (module.title === value ? <ModuleItem key={module.title} moduleInfo={module} /> : null))}
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
                {t('Quizzes and Assignments')}
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
