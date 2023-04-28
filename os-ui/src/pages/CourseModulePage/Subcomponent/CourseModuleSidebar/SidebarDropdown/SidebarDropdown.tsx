import { t } from 'i18next';
import { useNavigate } from 'react-router-dom';
import styles from '../Sidebar.module.scss';

interface Props {
	courseId: string | undefined
  checked:boolean,
  handleChangeValue: (a: string) => void,
  title: string;
}

const SidebarDropdown = ({
  courseId, checked, handleChangeValue, title,
}: Props) => {
  const navigate = useNavigate();

  return (
    <>
      <label
        className={styles.Checkbox_container}
        onClick={() => {
          handleChangeValue(title);
          navigate(`/userCourse/modulOverview/${courseId}`);
        }}
      >
        {t(`${title}`)}
        <input
          type="radio"
          name="category"
          value={title}
          checked={checked}
          onChange={() => handleChangeValue(title)}
        />
        <span className={styles.Checkbox_checkmark} />
      </label>
    </>
  );
};

export default SidebarDropdown;
