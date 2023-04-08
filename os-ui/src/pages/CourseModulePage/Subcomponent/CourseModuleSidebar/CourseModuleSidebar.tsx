import { useState } from 'react';
import Dropdown from '../../../../component/Dropdown/Dropdown';
import styles from './Sidebar.module.scss';
import { COURSE_NAME } from '../../../../constants/CourseModuleCategories';
import ArrowRightIcon from '../../../../assets/svg/ArrowRight.svg';
import SidebarDropdown from './SidebarDropdown/SidebarDropdown';

interface SidebarDropdownProps {
  value: string,
  handleChangeValue: (a:string) => void,
  title:string,
  modules: {
    link?: any,
    title: string,
    description: string,
    moduleItemSet: { [index: string]: string }[]
  }[];
}

const CourseModuleSidebar = ({
  value, title, handleChangeValue, modules,
}: SidebarDropdownProps) => {
  const [moduleListIsOpen, setModuleListIsOpen] = useState(true);
  const openModuleList = () => {
    setModuleListIsOpen((prevState) => !prevState);
  };
  const {
    chevronIsOpen, chevronIsClosed, moduleDescriptionIsOpen, moduleDescriptionIsClosed,
  } = styles;

  return (
    <div className={styles.Sidebar_container}>
      <h4 className={styles.Sidebar_courseName}>{title}</h4>
      <Dropdown
        open={value}
        trigger={(
          <button type="button" className={styles.ArrowRightIcon} onClick={() => handleChangeValue(value)}>
            Overview
            <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
            {' '}
          </button>
)}
        menu={[
          <div
            key={title}
            className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed}
            data-testid={moduleListIsOpen ? 'moduleListOpen' : 'moduleListClosed'}
          >
            <SidebarDropdown handleChangeValue={handleChangeValue} modules={modules} value={value} />
          </div>,
        ]}
      />
      {
        COURSE_NAME.map((button) => (
          <div
            className={styles.SidebarOverview_button}
            key={button.id}
          >
            {button.desc}
          </div>
        ))
      }
    </div>
  );
};

export default CourseModuleSidebar;
