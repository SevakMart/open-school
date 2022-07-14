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
  const { categoriesMainContainer, categoriesListContainer } = styles;

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
      <h2>{t('welcome.title')}</h2>
      <div className={categoriesListContainer}>
        {categoryPage > 0 ? (
          <LeftArrowIcon
            testId="categoryLeftArrow"
            handleArrowClick={() => {
              setCategoryPage((prevPage) => prevPage - 1);
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
              : <h2 data-testid="emptyCategoryMessage">{t('No Categories')}</h2>
          }
        {categoryPage < maxCategoryPage ? (
          <RightArrowIcon
            testId="categoryRightArrow"
            handleArrowClick={() => {
              setCategoryPage((prevPage) => prevPage + 1);
            }}
          />
        ) : null}
      </div>
      <button type="button">{t('SEE ALL')}</button>
    </div>
  );
};
export default HomepageCategories;
