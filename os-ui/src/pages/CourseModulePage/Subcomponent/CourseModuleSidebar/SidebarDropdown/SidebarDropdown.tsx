import { t } from 'i18next';
import styles from '../Sidebar.module.scss';

interface Props {
	value:string,
  handleChangeValue: (a: string) => void,
  modules: {
    link?: any,
    title: string,
    description: string,
    moduleItemSet: { [index: string]: string }[]
  }[];
}

const SidebarDropdown = ({ handleChangeValue, value, modules }: Props) => (
  <>
    {
        modules.map((module) => (
          <label key={module.title} className={styles.Checkbox_container}>
            {t(`${module.title}`)}
            <input
              type="radio"
              name="category"
              value={module.title}
              checked={value === module.title}
              onChange={() => handleChangeValue(module.title)}
            />
            <span className={styles.Checkbox_checkmark} />
          </label>
        ))
      }
  </>
);

export default SidebarDropdown;
