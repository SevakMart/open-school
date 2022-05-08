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
export const CHOOSE_CATEGORIES_HEADER = 'Choose Categories You Are Interested In';

// URLS
export const GET_MENTORS_URL = '../../mentorData.json';
export const GET_CATEGORIES_URL = '../../categorieData.json';
export const GET_REAL_MENTORS_URL = 'http://localhost:5000/api/v1/users/mentors?';
export const REGISTRATION_URL = 'http://localhost:5000/api/v1/auth/register';
export const GET_MAIN_CATEGORIES_URL = 'http://localhost:5000/api/v1/categories?';
export const GET_CATEGORY_SUBCATEGORY_SEARCH_URL = 'http://localhost:5000/api/v1/categories/subcategories?title=';
export const SIGNIN_URL = 'http://localhost:5000/api/v1/auth/login';
export const SAVE_PREFERRED_CATEGORIES = 'http://localhost:5000/api/v1/users';

// Error Message
export const ERROR_MESSAGE = 'Something went wrong please refresh the page :(';
export const EMPTY_DATA_ERROR_MESSAGE = 'No data to display :(';

// Successful Sign in message
export const SUCCESSFUL_SIGNIN_MESSAGE = 'You have Successfully signed in!';

// Regex for form validation
export const fullNameRegex = /^[A-Z\s]*$/i;
export const emailRegex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/g;
export const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
export const tokenRegex = /^[0-9]{4}$/;
/* User’s Password should have at least 8 characters,
including upper and lower case letters, numbers and signs (required) */

// Form Validation error message
export const INVALID_FULL_NAME_ERROR_MESSAGE = 'Invalid name or surname';
export const INVALID_EMAIL_ERROR_MESSAGE = 'Invalid email address';
export const INVALID_PASSWORD_ERROR_MESSAGE = 'Password should be at least 8 characters and contain at least one uppercase, lowercase, number and special character';
export const INVALID_TOKEN = 'Invalid verification code';
export const FULL_NAME_TOO_SHORT = 'Full name is too short';
export const EMAIL_TOO_SHORT = 'Email is too short';
export const PASSWORD_TOO_SHORT = 'Password is too short';
export const PASSWORD_REQUIRED = 'Password is required';
export const EMAIL_REQUIRED = 'Email is required';
export const TOKEN_REQUIRED = 'Verification code is required';
export const PASSWORDS_MISMATCH = 'Passwords do not match';
