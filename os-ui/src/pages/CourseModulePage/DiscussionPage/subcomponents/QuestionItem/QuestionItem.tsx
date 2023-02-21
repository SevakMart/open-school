/* eslint-disable react/prop-types */
import './questionItem.scss';
import ArrowRightIcon from '../../../../../assets/svg/ArrowRight.svg';

interface QuestionItemProps {
  text: string
}

const QuestionItem: React.FC<QuestionItemProps> = ({ text }) => (
  <div className="Question_item">
    <p className="Question_text">
      <span className="Question_inner">{text}</span>
    </p>
    <img src={ArrowRightIcon} alt="chevron" />
  </div>
);

export default QuestionItem;
