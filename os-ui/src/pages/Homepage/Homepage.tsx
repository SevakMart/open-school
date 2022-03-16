import { useEffect, useState } from 'react';
import HomepageHeader from '../../component/HomepageHeader/HomepageHeader';
import Footer from '../../component/Footer/Footer';
import Button from '../../component/Button/Button';
import RightArrowIcon from '../../icons/RightArrow';
import LeftArrowIcon from '../../icons/LeftArrow';
import MentorCard from '../../component/MentorProfile/MentorProfile';
import styles from './Homepage.module.scss';
import { MentorType } from '../../types/MentorType';
import { getMentors } from '../../services/getMentors';
import { GET_MENTORS_URL, ERROR_MESSAGE } from '../../constants/Strings';

const Homepage = () => {
  const [mentors, setMentors] = useState<MentorType[]>([]);
  // This page state is temporary until the final api is ready
  const [page, setPage] = useState(1);
  const [maxPage, setMaxPage] = useState(10);
  const [errorMessage, setErrorMessage] = useState('');
  const {
    mainContainer, buttonContainer, mentorMainContainer, mentorListContainer,
    leftArrow, rightArrow,
  } = styles;

  const getNextMentorList = () => {
    setPage((prevPage) => prevPage + 1);
  };
  const getPreviousMentorList = () => {
    setPage((prevPage) => prevPage - 1);
  };

  useEffect(() => {
    getMentors(GET_MENTORS_URL, page)
      .then((data) => {
        setMentors(data.mentors.slice((page - 1) * 4, page * 4));
        setMaxPage(data.totalPages);
      })
      .catch(() => setErrorMessage(ERROR_MESSAGE));
  }, [page]);

  return (
    <>
      <HomepageHeader />
      <div>
        <p>{'      '}</p>
      </div>
      <div className={mentorMainContainer}>
        <h2>Our Mentors</h2>
        <div className={mentorListContainer}>
          { page > 1 ? (
            <p className={leftArrow} onClick={getPreviousMentorList}>
              <LeftArrowIcon />
            </p>
          ) : null}
          {
            mentors.length > 0 && !errorMessage ? mentors.map((mentor, index) => (
              <MentorCard
                key={index}
                name={mentor.name}
                surname={mentor.surname}
                professionName={mentor.professionName}
                companyName={mentor.companyName}
                courseCount={mentor.courseCount}
                userImgPath={mentor.userImgPath}
                emailPath={mentor.emailPath}
                linkedinPath={mentor.linkedinPath}
              />
            )) : errorMessage ? <h2>{errorMessage}</h2>
              : <h2>We do not have mentors yet</h2>
          }
          {page < maxPage ? (
            <p className={rightArrow} onClick={getNextMentorList}>
              <RightArrowIcon />
            </p>
          ) : null}
        </div>
        <Button>Register as a mentor</Button>
      </div>
      <div className={mainContainer}>
        <h2>Start Your Journey Now!</h2>
        <div className={buttonContainer}>
          <Button>Sign up as a Student</Button>
          <Button>sign up as a mentor</Button>
        </div>
      </div>
      <Footer />
    </>
  );
};
export default Homepage;
