export interface PortalStatus {
    isOpen:boolean;
    buttonType:string;
    withSuccessMessage:string;
    isSignUpSuccessfulRegistration:boolean;
    isResetPasswordSuccessfulMessage:boolean;
    isResendVerificationEmailMessage:boolean;
    isRequestForMentorsPage:boolean,
	forgotVerficationEmail?: string,
	signUpEmail?: string
}
