// Navbar strings
export const APP_LOGO = 'Open-School';
export const EXPLORE_CATEGORIES = 'Explore Categories';
export const MENTORS = 'Mentors';
export const BECOME_A_MENTOR = 'Become a mentor';
export const SIGN_IN = 'Sign In';
export const ALL_LEARNING_PATHS = 'All Learning Paths';
export const MY_LEARNING_PATHS = 'My Learning Paths';

// Header strings
export const FREE_EDUCATIONAL_PLATFORM = 'Educational Platform';
export const SIGN_UP = 'SIGN UP';
export const HEADER_INTRODUCTION = 'Choose Categories You Are Interested In Choose Categories You Are Interested InChoose Categories You Are Interested';
export const EDUCATION_PLATFORM_IMAGE = 'https://image.shutterstock.com/image-vector/ebook-logo-design-vector-electronic-260nw-1767373559.jpg';

// URLS
export const GET_MENTORS_URL = '../../mentorData.json';
export const GET_CATEGORIES_URL = '../../categorieData.json';
export const GET_REAL_MENTORS_URL = 'http://localhost:5000/api/v1/mentors?';
export const REGISTRATION_URL = 'http://localhost:5000/api/v1/register';
export const GET_MAIN_CATEGORIES_URL = 'http://localhost:5000/api/v1/categories?';
// Error Message
export const ERROR_MESSAGE = 'Something went wrong please refresh the page :(';

// Regex for form validation
export const fullNameRegex = /^[A-Z\s]*$/i;
export const emailRegex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/g;
export const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
/* User’s Password should have at least 8 characters,
including upper and lower case letters, numbers and signs (required) */

// Form Validation error message
export const INVALID_FULL_NAME_ERROR_MESSAGE = 'Invalid name or surname';
export const INVALID_EMAIL_ERROR_MESSAGE = 'Invalid email address';
export const INVALID_PASSWORD_ERROR_MESSAGE = 'Password should be at least 8 characters and contain at least one uppercase,lowercase,number and special character';
export const FULL_NAME_TOO_SHORT = 'full name is too short';
export const EMAIL_TOO_SHORT = 'email is too short';
export const PASSWORD_TOO_SHORT = 'password is too short';
