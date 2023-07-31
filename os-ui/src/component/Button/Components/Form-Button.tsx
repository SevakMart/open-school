import React, { useEffect, useRef } from 'react';
import styles from '../Button-Styles.module.scss';

interface FormButtonProps {
  children: string;
  className: string[];
  onClick?: (event: React.SyntheticEvent) => void;
  disabled?: boolean;
}

export const FormButton = ({
  children, className, onClick, disabled,
}: FormButtonProps) => {
  const styleNames = className.map((className: string) => styles[`${className}`]);
  const buttonRef = useRef<HTMLButtonElement>(null);

  useEffect(() => {
    const handleKeyPress = (event: KeyboardEvent) => {
      if (event.key === 'Enter') {
        buttonRef.current?.click();
      }
    };

    buttonRef.current?.addEventListener('keypress', handleKeyPress);

    return () => {
      buttonRef.current?.removeEventListener('keypress', handleKeyPress);
    };
  }, []);

  return (
    <button
      ref={buttonRef}
      type="submit"
      className={styleNames.join(' ')}
      onClick={onClick}
      disabled={disabled}
    >
      {children}
    </button>
  );
};
