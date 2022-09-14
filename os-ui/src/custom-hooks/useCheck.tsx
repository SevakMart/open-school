import { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';

export const useCheck = (queryKey:string, queryValue:any) => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const params = new URLSearchParams(location.search);
  const [isChecked, setIsChecked] = useState(params.has(queryKey));

  const handleChecking = () => {
    setIsChecked((prevState) => !prevState);
  };

  useEffect(() => {
    if (isChecked) {
      params.set(queryKey, JSON.stringify(queryValue));
      navigate(`${location.pathname}?${params}`);
    } else {
      params.delete(queryKey);
      navigate(`${location.pathname}?${params}`);
    }
  }, [isChecked]);

  return [isChecked, handleChecking, dispatch] as const;
};
