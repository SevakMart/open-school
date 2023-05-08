import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { addMentorNameToParams, removeMentorNameFromParams } from '../../../../redux/Slices/AllMentorsFilterParamsSlice';
import { Input } from '../../../../component/Input/Input';
import { useCheck } from '../../../../custom-hooks/useCheck';
import styles from './AllMentorsPageHeader.module.scss';

export enum Mentors {
  ALL_MENTORS='All Mentors',
  SAVED_MENTORS='Saved Mentors'
}

const AllMentorsPageHeader = ({ changeHeaderFocus }:{changeHeaderFocus:(headerNav:Mentors)=>void}) => {
  const [focusedHeader, setFocusedHeader] = useState(Mentors.ALL_MENTORS);
  const [key, setKey] = useState(0);
  const {
    mentorHeaderMainContainer, navContent, activeNav, nonActiveNav,
  } = styles;
  const [, , dispatch, handleSearchedResult] = useCheck('name', '');
  const { t } = useTranslation();

  const handleSearch = (name:string) => {
    if (name) {
      dispatch(addMentorNameToParams(name));
    } else dispatch(removeMentorNameFromParams());
    handleSearchedResult(name);
  };

  const handleSavedMentorsClick = () => {
    dispatch(removeMentorNameFromParams());
    setKey((prevKey) => prevKey + 1);
    changeHeaderFocus(Mentors.SAVED_MENTORS);
    setFocusedHeader(Mentors.SAVED_MENTORS);
  };

  const handleAllMentorsClick = () => {
    dispatch(removeMentorNameFromParams());
    setKey((prevKey) => prevKey + 1);
    changeHeaderFocus(Mentors.ALL_MENTORS);
    setFocusedHeader(Mentors.ALL_MENTORS);
  };

  return (
    <div key={key} className={mentorHeaderMainContainer}>
      <div className={navContent}>
        <nav>
          <p className={focusedHeader === Mentors.ALL_MENTORS ? activeNav : nonActiveNav} onClick={handleAllMentorsClick}>
            {t('string.mentors.all')}
          </p>
          <p className={focusedHeader === Mentors.SAVED_MENTORS ? activeNav : nonActiveNav} onClick={handleSavedMentorsClick}>
            {t('string.mentors.saved')}
          </p>
        </nav>
      </div>
      <Input.SearchInput
        className={['search', 'search__allMentorsSearch']}
        changeUrlQueries={handleSearch}
      />
    </div>
  );
};
export default AllMentorsPageHeader;
