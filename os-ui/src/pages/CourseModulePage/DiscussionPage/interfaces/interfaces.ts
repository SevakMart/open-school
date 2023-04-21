export interface Question {
  id: string;
  text: string;
  createdDate: string;
}

export interface QuestionItemProps {
  text: string;
  id: string;
  createdDate: string;
  token: string;
  enrolledCourseId: number;
  sectionName: string;
}

export interface PopupProps {
  value: string;
  isOpen: boolean;
  handleClose: () => void;
  handleChange: (event: React.ChangeEvent<HTMLTextAreaElement>) => void;
  enrolledCourseId: number;
  token: string;
  cleanTextField: () => void;
  sectionName: string;
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
