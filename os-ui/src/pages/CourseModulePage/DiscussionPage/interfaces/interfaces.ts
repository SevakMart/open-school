export interface Question {
  id: string;
  text: string;
}

export interface QuestionItemProps {
  removeQ(questionId: string): void;
  questionChanged(id: string, val: string): void;
  text: string;
  id: string;
}

export interface PopupProps {
  value: string;
  isOpen: boolean;
  onClose: () => void;
  handleChange: (event: React.ChangeEvent<HTMLTextAreaElement>) => void;
  addQuestion: (val: string) => void;
}
