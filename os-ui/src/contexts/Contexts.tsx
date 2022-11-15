import { createContext } from 'react';
import { ALL_LEARNING_PATHS } from '../constants/Strings';

const userInitialValue = { token: '', id: 0 };

// eslint-disable-next-line @typescript-eslint/no-empty-function
const signInInfo = { signIn: false, setSignIn: (a:boolean) => {} };

export const userContext = createContext(userInitialValue);
export const courseContentContext = createContext('');
export const courseBookmarkContext = createContext(0);
export const headerTitleContext = createContext(ALL_LEARNING_PATHS);
export const signInContext = createContext(signInInfo);
