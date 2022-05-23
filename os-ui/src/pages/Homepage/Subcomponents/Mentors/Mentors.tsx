import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../../../redux/Store';
import RightArrowIcon from '../../../../icons/RightArrow';
import Button from '../../../../component/Button/Button';
import LeftArrowIcon from '../../../../icons/LeftArrow';
import MentorCard from '../../../../component/MentorProfile/MentorProfile';
import { MentorType } from '../../../../types/MentorType';
import publicService from '../../../../services/publicService';
import userService from '../../../../services/userService';
import styles from './Mentors.module.scss';

const HomepageMentors = ({ isLoggedIn, handleButtonClick }:{isLoggedIn:boolean,
  handleButtonClick:(buttonType:string)=>void}) => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const [mentors, setMentors] = useState<MentorType[]>([]);
  const [page, setPage] = useState(0);
  const [maxPage, setMaxPage] = useState(10);
  const [errorMessage, setErrorMessage] = useState('');
  const { mentorMainContainer, mentorListContainer } = styles;

  useEffect(() => {
    const cancel = false;
    let mentorPromise;

    if (isLoggedIn) {
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
  }, [isLoggedIn, page]);

  return (
    <div className={mentorMainContainer}>
      <h2>Our Mentors</h2>
      <div className={mentorListContainer}>
        { page > 0 ? (
          <LeftArrowIcon
            testId="mentorLeftArrow"
            handleArrowClick={() => {
              setPage((prevPage) => prevPage - 1);
            }}
          />
        ) : null}
        {
        mentors.length > 0 && !errorMessage ? mentors.map((mentor, index) => (
          <MentorCard key={index} mentor={{ ...mentor }} />
        )) : errorMessage ? <h2 data-testid="mentorsErrorMessage">{errorMessage}</h2>
          : <h2 data-testid="emptyMentorMessage">We do not have mentors yet</h2>
      }
        {page < maxPage ? (
          <RightArrowIcon
            testId="mentorRightArrow"
            handleArrowClick={() => {
              setPage((prevPage) => prevPage + 1);
            }}
          />
        ) : null}
      </div>
      <Button buttonType="signUp" buttonClick={() => handleButtonClick('signUp')}>Register as a mentor</Button>
    </div>
  );
};
export default HomepageMentors;