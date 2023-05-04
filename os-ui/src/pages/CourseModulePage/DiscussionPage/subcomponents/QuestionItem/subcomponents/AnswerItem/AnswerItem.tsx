import React, { useState } from 'react';
import { formatDate } from '../../../../helpers/formatDate';
import { AnswerItemProps } from '../../../../interfaces/interfaces';
import next from '../../../../../../../assets/svg/next.svg';
import './answerItem.scss';

const Answeritem:React.FC<AnswerItemProps> = ({
  name, surname, answerText, answerCreatedDate, avatar,
}) => {
  const answerCreatedDateFormated = formatDate(answerCreatedDate);

  // show more ...
  const [showMore, setShowMore] = useState(false);

  const toggleShowMore = () => {
    setShowMore(!showMore);
  };

  let truncatedText = answerText;
  if (answerText.length > 220) truncatedText = `${answerText.slice(0, 220)}...`;

  return (
    <div className="answersPage">
      <div className="answers-item">
        <div className="answers-item_info">
          <img src={avatar} alt="Avatar" className="answers-item_info_img" />
          <div className="answers-item_info_box">
            <div className="answers-item_info_box_person">
              {name}
              {' '}
              {surname}
            </div>
            <div className="answers-item_info_box_createdDate">
              {answerCreatedDateFormated}
            </div>
          </div>
        </div>
        <div className="answers-item_body">
          <div className="answers-item_text">
            {showMore ? answerText : truncatedText}
          </div>
          {answerText.length > 220 && (
            <div className="answers-item_showMore" onClick={toggleShowMore}>
              {showMore ? 'Show less' : 'Show more'}
              <img className="answers-item_showMore_img" src={next} alt=">" />
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Answeritem;
