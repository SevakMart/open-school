import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { RootState } from '../../../../redux/Store';
import CategoryCard from '../../../../component/CategoryProfile/CategoryProfile';
import { CategoryType } from '../../../../types/CategoryType';
import publicService from '../../../../services/publicService';
import categoriesService from '../../../../services/categoriesService';
import Title from '../Title/Title';
import MainBody from '../MainBody/MainBody';
import styles from './Categories.module.scss';

const HomepageCategories = () => {
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  const { t } = useTranslation();
  const [categories, setCategories] = useState<CategoryType[]>([]);
  const [errorMessage, setErrorMessage] = useState('');
  const [categoryPage, setCategoryPage] = useState(0);
  const [maxCategoryPage, setMaxCategoryPage] = useState(10);
  const { categoriesMainContainer, gridContent } = styles;

  useEffect(() => {
    let categoryPromise:any;
    if ((userInfo as any).token) {
      /* eslint-disable-next-line max-len */
      categoryPromise = categoriesService.getCategories({ page: categoryPage, size: 6 }, (userInfo as any).token);
    } else {
      categoryPromise = publicService.getPublicCategories({ page: categoryPage, size: 6 });
    }
    categoryPromise.then((res:any) => {
      const { data } = res;
      if (!data.errorMessage && data.content.length > 0) {
        setCategories(data.content);
        setMaxCategoryPage(data.totalPages - 1);
      } else if (data.errorMessage) setErrorMessage(data.errorMessage);
    });
  }, [categoryPage]);

  return (
    <div className={categoriesMainContainer}>
      <Title
        mainTitle={t('string.homePage.categories.title')}
        subTitle={t('string.homePage.categories.exploreCategories')}
        isMentor={false}
      />
      <MainBody
        page={categoryPage}
        maxPage={maxCategoryPage}
        isMentor={false}
        clickPrevious={() => setCategoryPage((prevPage) => prevPage - 1)}
        clickNext={() => setCategoryPage((prevPage) => prevPage + 1)}
      >
        <div className={gridContent}>
          {errorMessage && <h2 data-testid="categoriesErrorMessage">{errorMessage}</h2>}
          {categories.length === 0 && <h2 data-testid="emptyCategoryMessage">{t('messages.noData.categories')}</h2> }
          {categories.length > 0 && !errorMessage && categories.map((category, index) => (
            <CategoryCard key={index} category={category} />))}
        </div>
      </MainBody>
    </div>
  );
};
export default HomepageCategories;
