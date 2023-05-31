export interface FormValues {
	[index:string]:string;
  }

  interface ErrorFormValues {
	[index:string]:string;
  }

export interface FormProps {
	isSignUpForm: boolean;
	isResetPasswordForm: boolean;
	formButtonText: string;
	errorFormValue: ErrorFormValues;
	unAuthorizedSignInError?: string;
	handleForm: (formValue: FormValues) => void;
	resendEmail?: () => void;
  }
