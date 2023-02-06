import { t } from 'i18next';
import { COURSE_CATEGORIES } from '../../../../constants/CourseModuleCategories';
import styles from './Sidebar.module.scss';

interface Props {
handleChangeValue: (a:string) => void,
  }

const SidebarDropdown = ({ handleChangeValue }: Props) => (
  <>
    {
		COURSE_CATEGORIES.map((button: { value: string, id: string, title: string }) => (
  <label key={button.id} className={styles.Checkbox_container}>
    {t(`${button.title}`)}
    <input type="checkbox" onChange={() => handleChangeValue(button.value)} />
    <span className={styles.Checkbox_checkmark} />
  </label>
		))
	  }
  </>
);

export default SidebarDropdown;
