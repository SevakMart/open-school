import { useEffect, useState } from 'react';
import HomepageHeader from '../../component/HomepageHeader/HomepageHeader';
import Footer from '../../component/Footer/Footer';
import Button from '../../component/Button/Button';
import RightArrowIcon from '../../icons/RightArrow';
import LeftArrowIcon from '../../icons/LeftArrow';
import MentorCard from '../../component/MentorProfile/MentorProfile';
import CategoryCard from '../../component/CategoryProfile/CategoryProfile';
import styles from './Homepage.module.scss';
import { MentorType } from '../../types/MentorType';
import { CategoryType } from '../../types/CategoryType';
import { getMentors } from '../../services/getMentors';
import { getCategories } from '../../services/getCategories';
import { GET_CATEGORIES_URL, GET_REAL_MENTORS_URL } from '../../constants/Strings';
import SignUp from '../../component/SignUp/SignUp';

const Homepage = () => {
  const [mentors, setMentors] = useState<MentorType[]>([]);
  const [categories, setCategories] = useState<CategoryType[]>([]);
  const [page, setPage] = useState(0);
  const [maxPage, setMaxPage] = useState(10);
  const [errorMessage, setErrorMessage] = useState('');
  const [categoryPage, setCategoryPage] = useState(1);
  const [maxCategoryPage, setMaxCategoryPage] = useState(10);
  const [listType, setListType] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const [clickedButtonType, setClickedButtonType] = useState('');
  const {
    mainContainer, buttonContainer, mentorMainContainer, mentorListContainer,
    categoriesMainContainer, categoriesListContainer,
  } = styles;
  const handleButtonClick = (buttonType:string) => {
    switch (buttonType) {
      case 'signUp':
        setIsOpen(true);
        setClickedButtonType(buttonType);
        break;
      case 'signIn':
        setIsOpen(true);
        setClickedButtonType(buttonType);
        break;
      case 'closeButton':
        setIsOpen(false);
        break;
    }
  };

  useEffect(() => {
    if (listType === 'Mentor' || listType === '') {
      getMentors(`${GET_REAL_MENTORS_URL}page=${page}&size=4`)
        .then((data) => {
          if (!data.errorMessage) {
            /* setMentors(data.mentors.slice((page - 1) * 4, page * 4));
            setMaxPage(data.totalPages); */
            setMentors(data.content);
            setMaxPage(data.totalPages - 1);
          } else setErrorMessage(data.errorMessage);
        });
    } if (listType === 'Category' || listType === '') {
      getCategories(GET_CATEGORIES_URL)
        .then((data) => {
          if (!data.errorMessage) {
            setCategories(data.categories.slice((categoryPage - 1) * 6, categoryPage * 6));
            setMaxCategoryPage(data.totalPages);
          } else setErrorMessage(data.errorMessage);
        });
    }
  }, [page, categoryPage]);

  return (
    <>
      <HomepageHeader
        handleFormVisibility={handleButtonClick}
      />
      {/* This section below is dedicated to implement the category list */}
      <div className={categoriesMainContainer}>
        <h2>Explore Categories You Are Interested In</h2>
        <div className={categoriesListContainer}>
          {categoryPage > 1 ? (
            <LeftArrowIcon
              testId="categoryLeftArrow"
              handleArrowClick={() => {
                setCategoryPage((prevPage) => prevPage - 1);
                setListType('Category');
              }}
            />
          ) : null}
          {
            categories.length > 0 && !errorMessage ? categories.map((category, index) => (
              <CategoryCard
                key={index}
                title={category.title}
                logoPath={category.logoPath}
              />
            )) : errorMessage ? <h2 data-testid="categoriesErrorMessage">{errorMessage}</h2>
              : <h2>We do not have courses yet</h2>
          }
          {categoryPage < maxCategoryPage ? (
            <RightArrowIcon
              testId="categoryRightArrow"
              handleArrowClick={() => {
                setCategoryPage((prevPage) => prevPage + 1);
                setListType('Category');
              }}
            />
          ) : null}
        </div>
        <button type="button">SEE ALL</button>
      </div>
      {/* This section below is dedicated to implement the mentor list */}
      <div className={mentorMainContainer}>
        <h2>Our Mentors</h2>
        <div className={mentorListContainer}>
          { page > 0 ? (
            <LeftArrowIcon
              testId="mentorLeftArrow"
              handleArrowClick={() => {
                setPage((prevPage) => prevPage - 1);
                setListType('Mentor');
              }}
            />
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
            )) : errorMessage ? <h2 data-testid="mentorsErrorMessage">{errorMessage}</h2>
              : <h2>We do not have mentors yet</h2>
          }
          {page < maxPage ? (
            <RightArrowIcon
              testId="mentorRightArrow"
              handleArrowClick={() => {
                setPage((prevPage) => prevPage + 1);
                setListType('Mentor');
              }}
            />
          ) : null}
        </div>
        <Button buttonType="signUp" buttonClick={handleButtonClick}>Register as a mentor</Button>
      </div>
      <div className={mainContainer}>
        <h2>Start Your Journey Now!</h2>
        <div className={buttonContainer}>
          <Button buttonType="signUp" buttonClick={handleButtonClick}>Sign up as a Student</Button>
          <Button buttonType="signUp" buttonClick={handleButtonClick}>sign up as a mentor</Button>
        </div>
      </div>
      <Footer />
      {isOpen && clickedButtonType === 'signUp' ? <SignUp handleSignUpClicks={handleButtonClick} /> : null}
    </>
  );
};
export default Homepage;
