/* eslint-disable react/prop-types */
import './questionItem.scss';

interface QuestionItemProps {
  text: string,
  num: number,
}

const QuestionItem: React.FC<QuestionItemProps> = ({ text, num }) => (
  <div className="Question_item">
    <p className="Question_text">
      Question
      {num}
      :
      <span className="Question_inner">{text}</span>
    </p>
    <p>&#62;</p>
  </div>
);

export default QuestionItem;
