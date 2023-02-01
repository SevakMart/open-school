/* eslint-disable react/jsx-key */
/* eslint-disable react/button-has-type */
import { t } from 'i18next';
import { useState } from 'react';
import Dropdown from '../../../../component/Dropdown/Dropdown';
import SidebarDropdown from './SidebarDropdown';
import styles from './Sidebar.module.scss';
import { COURSE_NAME } from '../../../../constants/CourseModuleCategories';
import ArrowRightIcon from '../../../../assets/svg/ArrowRight.svg';

interface SidebarDropdownProps {
  value: string,
  handleChangeValue: (a:string) => void
  setDisBtnPosition: (value:string) => void
}

const CourseModuleSidebar = (props: SidebarDropdownProps) => {
  const { value, handleChangeValue, setDisBtnPosition } = props;
  const [moduleListIsOpen, setModuleListIsOpen] = useState(true);
  const openModuleList = () => {
    setModuleListIsOpen((prevState) => !prevState);
  };
  const {
    chevronIsOpen, chevronIsClosed, moduleDescriptionIsOpen, moduleDescriptionIsClosed,
  } = styles;
  return (
    <div className={styles.Sidebar_container}>
      <h4 className={styles.Sidebar_courseName}>{t('Course Name')}</h4>
      <Dropdown
        open={value}
        trigger={(
          <button className={styles.ArrowRightIcon} onChange={() => handleChangeValue(value)}>
            Overview
            <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
            {' '}
          </button>
)}
        menu={[
          <div
            className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed}
          >
            <SidebarDropdown handleChangeValue={handleChangeValue} />
          </div>,
        ]}
      />
      {
        COURSE_NAME.map((button) => (
          <div
            className={styles.SidebarOverview_button}
            key={button.id}
            onClick={() => setDisBtnPosition(button.desc)}
          >
            {button.desc}
          </div>
        ))
      }
    </div>
  );
};
export default CourseModuleSidebar;
// function handleChangeValue(a: string): void {
//   throw new Error('Function not implemented.');
// }
