import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../../../redux/Store';
import RightArrowIcon from '../../../../icons/RightArrow';
import LeftArrowIcon from '../../../../icons/LeftArrow';
import CategoryCard from '../../../../component/CategoryProfile/CategoryProfile';
import { CategoryType } from '../../../../types/CategoryType';
import publicService from '../../../../services/publicService';
import categoriesService from '../../../../services/categoriesService';
import styles from './Categories.module.scss';

const HomepageCategories = ({ isLoggedIn }:{isLoggedIn:boolean}) => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const { t } = useTranslation();
  const [categories, setCategories] = useState<CategoryType[]>([]);
  const [errorMessage, setErrorMessage] = useState('');
  const [categoryPage, setCategoryPage] = useState(0);
  const [maxCategoryPage, setMaxCategoryPage] = useState(10);
  const {
    categoriesMainContainer, categoryHeader, gridContent,
    icon, icon__right, icon__left, categoriesListContainer, registrationButton,
  } = styles;

  useEffect(() => {
    let cancel = false;
    let categoryPromise;
    if (isLoggedIn) {
      categoryPromise = categoriesService.getCategories(
        { page: categoryPage, size: 6 },
        (userInfo as any).token,
      );
    } else {
      categoryPromise = publicService.getPublicCategories({ page: categoryPage, size: 6 });
    }

    categoryPromise.then((res) => {
      if (cancel) return;
      const { data } = res;
      if (!data.errorMessage && data.content.length > 0) {
        setCategories(data.content);
        setMaxCategoryPage(data.totalPages - 1);
      } else if (data.errorMessage) setErrorMessage(data.errorMessage);
    });
    return () => { cancel = true; };
  }, [isLoggedIn, categoryPage]);

  return (
    <div className={categoriesMainContainer}>
      <div className={categoryHeader}>
        <h3>{t('string.homePage.categories.title')}</h3>
        <h2>{t('string.homePage.categories.exploreCategories')}</h2>
      </div>
      <div className={categoriesListContainer}>
        {categoryPage > 0
        && (
        <div className={`${icon} ${icon__left}`}>
          <LeftArrowIcon testId="categoryLeftArrow" handleArrowClick={() => { setCategoryPage((prevPage) => prevPage - 1); }} />
        </div>
        )}
        <div className={gridContent}>
          {
            categories.length > 0 && !errorMessage ? categories.map((category, index) => (
              <CategoryCard
                key={index}
                title={category.title}
                logoPath={category.logoPath}
              />
            )) : errorMessage ? <h2 data-testid="categoriesErrorMessage">{errorMessage}</h2>
              : <h2 data-testid="emptyCategoryMessage">{t('messages.noData.categories')}</h2>
          }
        </div>
        {categoryPage < maxCategoryPage
        && (
        <div className={`${icon} ${icon__right}`}>
          <RightArrowIcon testId="categoryRightArrow" handleArrowClick={() => { setCategoryPage((prevPage) => prevPage + 1); }} />
        </div>
        )}
      </div>
      <div className={registrationButton}>
        <button type="button">{t('button.homePage.registerMentor')}</button>
      </div>
    </div>
  );
};
export default HomepageCategories;
