import { useEffect, useState, useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../../../redux/Store';
import { getHomepageCategoriesList } from '../../../../redux/Slices/HomepageCategoriesSlice';
import CategoryCard from '../../../../component/CategoryProfile/CategoryProfile';
import { userContext } from '../../../../contexts/Contexts';
import { CategoryType } from '../../../../types/CategoryType';
import MainBody from '../MainBody/MainBody';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import styles from './Categories.module.scss';
import RightArrowIcon from '../../../../icons/RightArrow';
import LeftArrowIcon from '../../../../icons/LeftArrow';

/* eslint-disable max-len */

const HomepageCategories = () => {
  const { token } = useContext(userContext);
  const dispatch = useDispatch();
  const homepageCategoriesListState = useSelector<RootState>((state) => state.homepageCategoriesList) as {entity:CategoryType[], isLoading:boolean, errorMessage:string, totalPages:number};
  const {
    entity, isLoading, errorMessage, totalPages,
  } = homepageCategoriesListState;
  const { t } = useTranslation();
  const [page, setPage] = useState(0);
  const {
    categoriesMainContainer, gridContent, categoriesTitle, categoriesSumtitle, icon,
  } = styles;

  useEffect(() => {
    dispatch(getHomepageCategoriesList({ page, token: token || '' }));
  }, [page]);

  return (
    <div className={categoriesMainContainer}>
      <p className={categoriesTitle}>
        {t('string.homePage.categories.title')}
      </p>
      <p className={categoriesSumtitle}>
        {t('string.homePage.categories.exploreCategories')}
      </p>
      {page < totalPages
        && (
        <div className={icon}>
          <RightArrowIcon testId="categoryRightArrow" handleArrowClick={() => setPage((prevPage) => prevPage + 1)} />
        </div>
        )}
      {page > 0 && (
      <div className={icon}>
        <LeftArrowIcon testId="categoryLeftArrow" handleArrowClick={() => setPage((prevPage) => prevPage - 1)} />
      </div>
      )}
      <MainBody
        isMentor={false}
      >
        <div className={gridContent}>
          <ContentRenderer
            isLoading={isLoading}
            errorMessage={errorMessage}
            entity={entity}
            errorFieldClassName="allLearningPathErrorStyle"
            render={() => (
              entity.map((category: CategoryType, index: number) => (
                <CategoryCard key={index} category={category} />
              ))
            )}
          />
        </div>
      </MainBody>
    </div>
  );
};
export default HomepageCategories;
