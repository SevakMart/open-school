import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch } from 'react-redux';
import { addSignedUpUserId } from '../../../../redux/Slices/SignedUpUserIdSlice';
import { validateSignUpForm } from '../../../../helpers/SignUpFormValidate';
import { openModalWithSuccessMessage } from '../../../../redux/Slices/PortalOpenStatus';
import { Types } from '../../../../types/types';
import Form from '../../Form/Form';
import authService from '../../../../services/authService';
import { FormValues } from '../../../../types/FormTypes';

const initialErrorFormValues = {
  firstNameError: '', lastNameError: '', emailError: '', psdError: '',
};

const SignUpDefault = () => {
  const dispatch = useDispatch();
  const { t } = useTranslation();
  const [errorFormValue, setErrorFormValue] = useState(initialErrorFormValues);

  const handleSubmitForm = (formValues:FormValues) => {
    const {
      firstNameError, lastNameError, emailError, psdError,
    } = validateSignUpForm(formValues);
    if (!firstNameError && !lastNameError && !emailError && !psdError) {
      authService.register(formValues).then((response) => {
        if (response.status === 201) {
          setErrorFormValue(initialErrorFormValues);
          dispatch(addSignedUpUserId(response.userId));
          dispatch(openModalWithSuccessMessage({
            buttonType: Types.Button.SUCCESS_MESSAGE, withSuccessMessage: t('messages.successfullSignUp'), isSignUpSuccessfulRegistration: true, forgotVerficationEmail: formValues.email,
          }));
        } else {
          setErrorFormValue({ ...initialErrorFormValues, emailError: response[0] });
        }
      });
    } else setErrorFormValue(validateSignUpForm(formValues));
  };

  return (
    <Form
      isSignUpForm
      isResetPasswordForm={false}
      formButtonText={t('button.homePage.signUp')}
      errorFormValue={errorFormValue}
      handleForm={handleSubmitForm}
    />
  );
};
export default SignUpDefault;
