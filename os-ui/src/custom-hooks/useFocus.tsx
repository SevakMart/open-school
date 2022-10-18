import { useEffect, useState } from 'react';

export const useFocus = (coloronBlur:string, element:HTMLElement|null) => {
  const [isFocused, setIsFocused] = useState(false);
  const handleOnFocus = () => {
    setIsFocused(true);
  };
  const handleOnBlur = () => {
    setIsFocused(false);
  };
  useEffect(() => {
    if (isFocused) {
      element ? element.style.border = '2px solid black' : null;
    } else {
      element ? element.style.border = `2px solid ${coloronBlur}` : null;
    }
  }, [isFocused]);
  return [handleOnFocus, handleOnBlur] as const;
};
