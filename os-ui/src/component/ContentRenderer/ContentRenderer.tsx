import { useTranslation } from 'react-i18next';
import Loader from '../Loader/Loader';
import NoCourses from '../../pages/MyLearningPathPage/Subcomponents/NoCourses/NoCourses';
import { ErrorField } from '../ErrorField/ErrorField';

/* eslint-disable max-len */

const ContentRenderer = ({
  isLoading, errorMessage, entity, errorFieldClassName, render, isMyLearningPathPage,
}:
    {isLoading:boolean, errorMessage:string, entity:any[]|any, errorFieldClassName:string, render:(entity:any[]|any)=>React.ReactElement[]|React.ReactElement, isMyLearningPathPage?:boolean}) => {
  const { t } = useTranslation();
  const NoDisplayedDataMessage = <h2 data-testid="emptyMessageHeader">{t('messages.noData.default')}</h2>;
  return (
    <>
      {isLoading && <Loader />}
      {errorMessage !== '' && (
      <ErrorField.MainErrorField className={[errorFieldClassName]}>
        {errorMessage}
      </ErrorField.MainErrorField>
      )}
      {(entity.length === 0 || Object.keys(entity).length === 0) && !isLoading && errorMessage.length === 0 && (isMyLearningPathPage ? <NoCourses /> : NoDisplayedDataMessage) }
      {!isLoading && (entity.length > 0 || Object.keys(entity).length > 0) && render(entity)}
    </>
  );
};
export default ContentRenderer;
