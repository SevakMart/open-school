export interface Question {
  id: string;
  text: string;
  createdDate: string;
}

export interface QuestionItemProps {
  removeQ(questionId: string): void;
  questionChanged(id: string, val: string): void;
  text: string;
  id: string;
  createdDate: string;
}

export interface PopupProps {
  value: string;
  isOpen: boolean;
  onClose: () => void;
  handleChange: (event: React.ChangeEvent<HTMLTextAreaElement>) => void;
  addQuestion: (val: string) => void;
}

interface AnimatedFunction {
  (any_function: (...args: string[]) => void, ...args: string[]): void;
}

interface TextAreaRef {
  current: HTMLTextAreaElement | null;
}

export interface QuestionItemPopupProps {
  isOpen: boolean;
  isAnimating: boolean;
  animatedFunction: AnimatedFunction;
  onClose: () => void;
  editQuestion: () => void;
  btnType: string
  btnTextType:string;
  removeQ: (id: string) => void;
  id: string;
  textAreaRef:TextAreaRef;
}
