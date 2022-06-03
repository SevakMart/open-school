import { createContext } from 'react';

const userInitialValue = { token: '', userId: 0 };

export const userContext = createContext(userInitialValue);
export const courseContentContext = createContext('');
export const courseBookmarkContext = createContext(0);
