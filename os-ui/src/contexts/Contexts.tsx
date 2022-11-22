/* eslint-disable @typescript-eslint/no-empty-function */
import { createContext } from 'react';
import { ALL_LEARNING_PATHS } from '../constants/Strings';

type signInInfoType = {
    signIn: boolean,
    setSignIn: React.Dispatch<React.SetStateAction<boolean>>
}
const userInitialValue = { token: '', id: 0 };
const signInInfo = { signIn: false, setSignIn: () => {} };

export const userContext = createContext(userInitialValue);
export const courseContentContext = createContext('');
export const courseBookmarkContext = createContext(0);
export const headerTitleContext = createContext(ALL_LEARNING_PATHS);
export const signInContext = createContext<signInInfoType>(signInInfo);
