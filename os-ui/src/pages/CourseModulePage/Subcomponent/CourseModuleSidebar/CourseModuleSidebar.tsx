/* eslint-disable react/jsx-key */
/* eslint-disable react/button-has-type */
import { t } from 'i18next';
import Dropdown from '../../../../component/Dropdown/Dropdown';
import SidebarDropdown from './SidebarDropdown';
import styles from './Sidebar.module.scss';
import { COURSE_NAME } from '../../../../constants/CourseModuleCategories';

interface SidebarDropdownProps {
  value: string,
  handleChangeValue: (a:string) => void
}

const CourseModuleSidebar = ({ value, handleChangeValue }: SidebarDropdownProps) => (
  <div className={styles.Sidebar_container}>
    <h4 className={styles.Sidebar_courseName}>{t('Course Name')}</h4>
    <Dropdown
      open={value}
      trigger={<button className={styles.SidebarOverview_button}>{t('Overview')}</button>}
      menu={[
        <SidebarDropdown handleChangeValue={handleChangeValue} />,
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
export default CourseModuleSidebar;
