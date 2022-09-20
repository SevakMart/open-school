import { useEffect, useState, useContext } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../../../redux/Store';
import { getHomepageCategoriesList } from '../../../../redux/Slices/HomepageCategoriesSlice';
import CategoryCard from '../../../../component/CategoryProfile/CategoryProfile';
import { userContext } from '../../../../contexts/Contexts';
import { CategoryType } from '../../../../types/CategoryType';
import Title from '../Title/Title';
import MainBody from '../MainBody/MainBody';
import ContentRenderer from '../../../../component/ContentRenderer/ContentRenderer';
import styles from './Categories.module.scss';

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
  const { categoriesMainContainer, gridContent } = styles;

  useEffect(() => {
    dispatch(getHomepageCategoriesList({ page, token: token || '' }));
  }, [page]);

  return (
    <div className={categoriesMainContainer}>
      <Title
        mainTitle={t('string.homePage.categories.title')}
        subTitle={t('string.homePage.categories.exploreCategories')}
        isMentor={false}
      />
      <MainBody
        page={page}
        maxPage={totalPages}
        isMentor={false}
        clickPrevious={() => setPage((prevPage) => prevPage - 1)}
        clickNext={() => setPage((prevPage) => prevPage + 1)}
      >
        <div className={gridContent}>
          <ContentRenderer
            isLoading={isLoading}
            errorMessage={errorMessage}
            entity={entity}
            errorFieldClassName="allLearningPathErrorStyle"
            render={(entity) => (
              entity.map((category, index) => (
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
