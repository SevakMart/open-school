import { useTranslation } from 'react-i18next';
import styles from './DiscussionForumShowMoreButton.module.scss';

type DiscussionForumShowMoreButtonProps = {
  isLoadingAllTheQuestions: boolean;
  togglesetPageNum: (type: string) => void;
  pageNum: number;
};

const DiscussionForumShowMoreButton = ({ isLoadingAllTheQuestions, togglesetPageNum, pageNum }: DiscussionForumShowMoreButtonProps) => {
  const { t } = useTranslation();
  const {
    showMoreContainer, showMoreButton, showMoreButtonMore, showMoreButtonLess,
  } = styles;

  return (
    <div className={showMoreContainer}>
      <button
        type="button"
        disabled={isLoadingAllTheQuestions}
        className={`${showMoreButton} ${showMoreButtonMore}`}
        onClick={() => togglesetPageNum('plus')}
      >
        {t('Show more')}
      </button>
      <button
        type="button"
        disabled={isLoadingAllTheQuestions}
        className={`${showMoreButton} ${showMoreButtonLess}`}
        onClick={() => togglesetPageNum('minus')}
      >
        {pageNum > 1 ? t('Show less') : ''}
      </button>
    </div>
  );
};

export default DiscussionForumShowMoreButton;
