import React from 'react';
import { fireEvent, render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import SidebarDropdown from '../SidebarDropdown';

describe('SidebarDropdown component', () => {
  const handleChangeValue = jest.fn();

  test('should call handleChangeValue with correct value when radio button is clicked', () => {
    const { container, asFragment } = render(
      <BrowserRouter>
        <SidebarDropdown
          courseId="123"
          checked
          handleChangeValue={handleChangeValue}
          title="Title 1"
        />
        <SidebarDropdown
          courseId="123"
          checked={false}
          handleChangeValue={handleChangeValue}
          title="Title 2"
        />
      </BrowserRouter>,
    );

    const radioBtn1 = container.querySelector('input[value="Title 1"]') as HTMLInputElement;
    const radioBtn2 = container.querySelector('input[value="Title 2"]') as HTMLInputElement;

    expect(radioBtn1.checked).toBe(true);

    fireEvent.click(radioBtn2);
    expect(handleChangeValue).toHaveBeenCalledWith('Title 2');

    expect(asFragment()).toMatchSnapshot();
  });
});
