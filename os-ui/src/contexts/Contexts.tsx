import { createContext } from 'react';

const userInitialValue = '';

export const userContext = createContext(userInitialValue);
export const courseContentContext = createContext('');
export const courseBookmarkContext = createContext(0);
