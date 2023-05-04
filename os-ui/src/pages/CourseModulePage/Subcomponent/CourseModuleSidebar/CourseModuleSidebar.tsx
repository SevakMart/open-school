import { useState } from 'react';
import { Link, useLocation, useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import Dropdown from '../../../../component/Dropdown/Dropdown';
import styles from './Sidebar.module.scss';
import { COURSE_NAME } from '../../../../constants/CourseModuleCategories';
import ArrowRightIcon from '../../../../assets/svg/ArrowRight.svg';
import { RootState } from '../../../../redux/Store';
import { setValue } from '../../../../redux/Slices/CourseModuleSlice';
import { CourseDescriptionType } from '../../../../types/CourseTypes';
import SidebarDropdown from './SidebarDropdown/SidebarDropdown';

const CourseModuleSidebar = () => {
  const [moduleListIsOpen, setModuleListIsOpen] = useState(true);
  const openModuleList = () => {
    setModuleListIsOpen((prevState) => !prevState);
  };
  const {
    chevronIsOpen, chevronIsClosed, moduleDescriptionIsOpen, moduleDescriptionIsClosed,
  } = styles;

  const { entity } = useSelector<RootState>((state) => state.courseDescriptionRequest) as { entity: CourseDescriptionType };
  const { courseId } = useParams();
  const { value } = useSelector<RootState>((state) => state.courseModule) as { value: string };
  const dispatch = useDispatch();

  const handleChangeValue = (newValue: string) => {
    dispatch(setValue(newValue));
  };

  // sidebar item's click handler
  const location = useLocation();
  const currentPath = location.pathname;
  let isbtnClicked = false;
  const currentBaseUrl = `/userCourse/modulOverview/${courseId}`;
  if (currentPath === `${currentBaseUrl}/discussionForum`) isbtnClicked = true;
  if (currentPath === `${currentBaseUrl}/discussionForum/AskMentor`) isbtnClicked = true;
  if (currentPath === `${currentBaseUrl}/discussionForum/AskPeers`) isbtnClicked = true;
  if (currentPath === currentBaseUrl) isbtnClicked = false;
  const itemBtnclickedStyle = isbtnClicked ? styles.btnClickedCName : styles.btnNotClickedCName;
  const itemBtnclickedRevStyle = isbtnClicked ? styles.btnNotClickedCName : styles.btnClickedCName;

  return (
    <div className={styles.Sidebar_container}>
      <h4 className={styles.Sidebar_courseName}>{entity.title}</h4>
      <Dropdown
        open={value}
        trigger={(
          <button type="button" className={styles.ArrowRightIcon} onClick={() => handleChangeValue(value)}>
            <Link
              to={`/userCourse/modulOverview/${courseId}`}
              className={itemBtnclickedRevStyle}
            >
              Overview
            </Link>
            <img className={moduleListIsOpen ? chevronIsOpen : chevronIsClosed} src={ArrowRightIcon} alt="chevron" onClick={openModuleList} />
          </button>
        )}
        menu={[
          <div
            key={entity.title}
            className={moduleListIsOpen ? moduleDescriptionIsOpen : moduleDescriptionIsClosed}
            data-testid={moduleListIsOpen ? 'moduleListOpen' : 'moduleListClosed'}
          >
            { entity?.modules?.map((module) => (
              <SidebarDropdown key={module.title} checked={value === module.title} title={module.title} handleChangeValue={handleChangeValue} courseId={courseId} />
            ))}
          </div>,
        ]}
      />
      {
        COURSE_NAME.map((button) => (
          <div
            className={styles.SidebarOverview_button}
            key={button.id}
          >
            {button.desc === 'Discussion Forum' ? (
              <Link
                to={`/userCourse/modulOverview/${courseId}/discussionForum`}
                className={itemBtnclickedStyle}
              >
                {button.desc}
              </Link>
            ) : (
              button.desc
            )}
          </div>
        ))
      }
    </div>
  );
};

export default CourseModuleSidebar;
