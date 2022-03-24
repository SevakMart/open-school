interface ButtonProps{
  buttonType:string;
  children:string;
  buttonClick(arg:string):void;
}

const Button = ({ children, buttonType, buttonClick }:ButtonProps) => (
  <button type="button" name={buttonType} onClick={(e) => buttonClick((e.target as HTMLButtonElement).name)}>
    {children}
  </button>
);
export default Button;
