import { createContext } from 'react';
import { ALL_LEARNING_PATHS } from '../constants/Strings';

const userInitialValue = { token: '', id: 0 };

export const userContext = createContext(userInitialValue);
export const courseContentContext = createContext('');
export const courseBookmarkContext = createContext(0);
export const headerTitleContext = createContext(ALL_LEARNING_PATHS);
