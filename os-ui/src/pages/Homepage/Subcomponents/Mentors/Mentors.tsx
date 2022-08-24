import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../../../redux/Store';
import RightArrowIcon from '../../../../icons/RightArrow';
import LeftArrowIcon from '../../../../icons/LeftArrow';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import { MentorType } from '../../../../types/MentorType';
import publicService from '../../../../services/publicService';
import userService from '../../../../services/userService';
import Button from '../../../../component/Button/Button';
import styles from './Mentors.module.scss';

const HomepageMentors = ({ handleButtonClick }:{handleButtonClick:(buttonType:string)=>void}) => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const { t } = useTranslation();
  const [mentors, setMentors] = useState<MentorType[]>([]);
  const [page, setPage] = useState(0);
  const [maxPage, setMaxPage] = useState(10);
  const [errorMessage, setErrorMessage] = useState('');
  const {
    mentorMainContainer, mentorHeader, gridContent,
    icon, icon__right, icon__left, mentorListContainer, registrationButton,
  } = styles;

  useEffect(() => {
    const cancel = false;
    let mentorPromise;

    if ((userInfo as any).token) {
      mentorPromise = userService.getMentors(
        { page, size: 4 },
        (userInfo as any).token,
      );
    } else mentorPromise = publicService.getPublicMentors({ page, size: 4 });

    mentorPromise.then((res) => {
      if (cancel) return;
      const { data } = res;
      if (!data.errorMessage && data.content.length > 0) {
        setMentors(data.content);
        setMaxPage(data.totalPages - 1);
      } else if (data.errorMessage) setErrorMessage(data.errorMessage);
    });
  }, [page]);

  return (
    <div className={mentorMainContainer}>
      <div className={mentorHeader}>
        <h3>{t('string.homePage.mentors.ourMentors')}</h3>
        <h2>{t('string.homePage.mentors.learnAboutContributors')}</h2>
      </div>
      <div className={mentorListContainer}>
        { page && (
          <div className={`${icon} ${icon__left}`}>
            <LeftArrowIcon
              testId="mentorLeftArrow"
              handleArrowClick={() => {
                setPage((prevPage) => prevPage - 1);
              }}
            />
          </div>
        )}
        <div className={gridContent}>
          {errorMessage && <h2 data-testid="mentorsErrorMessage">{errorMessage}</h2>}
          {!mentors.length && <h2 data-testid="emptyMentorMessage">{t('messages.noData.mentors')}</h2>}
          {mentors.length && !errorMessage && mentors.map((mentor, index) => (
            <MentorCard key={index} mentor={{ ...mentor }} />))}
        </div>
        {page < maxPage
        && (
        <div className={`${icon} ${icon__right}`}>
          <RightArrowIcon
            testId="mentorRightArrow"
            handleArrowClick={() => {
              setPage((prevPage) => prevPage + 1);
            }}
          />
        </div>
        )}
      </div>
      <div className={registrationButton}>
        <Button.SignUpButton className={['mainMentorRegistrationButton']}>
          {t('button.homePage.registerMentor')}
        </Button.SignUpButton>
      </div>
    </div>
  );
};
export default HomepageMentors;
