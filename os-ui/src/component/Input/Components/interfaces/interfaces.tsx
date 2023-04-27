export interface EmailInputProps {
	textName: string;
	labelText: string;
	placeholderText: string;
	errorMessage: string;
	value: string;
	handleInputChange: (event: React.SyntheticEvent) => void;
  }

export interface PasswordInputProps {
	textName: string;
	labelText: string;
	placeholderText: string;
	errorMessage: string;
	value: string;
	handleInputChange: (event: React.SyntheticEvent) => void;
	handleEnterPress: (event: React.KeyboardEvent<HTMLInputElement>) => void;
  }
