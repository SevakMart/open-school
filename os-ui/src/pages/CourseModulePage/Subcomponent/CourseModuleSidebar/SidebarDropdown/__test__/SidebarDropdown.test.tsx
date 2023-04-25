import React from 'react';
import { fireEvent, render } from '@testing-library/react';
import SidebarDropdown from '../SidebarDropdown';

describe('SidebarDropdown component', () => {
  const handleChangeValue = jest.fn();
  const modules = [
    {
      title: 'Title 1',
      description: 'Description 1',
      moduleItemSet: [],
    },
    {
      title: 'Title 2',
      description: 'Description 2',
      moduleItemSet: [],
    },
  ];

  test('should call handleChangeValue with correct value when radio button is clicked', () => {
    const { container, asFragment } = render(
      <SidebarDropdown
        value={modules[0].title}
        handleChangeValue={handleChangeValue}
        modules={modules}
      />,
    );

    const radioBtn1 = container.querySelector('input[value="Title 1"]') as HTMLInputElement;
    const radioBtn2 = container.querySelector('input[value="Title 2"]') as HTMLInputElement;

    expect(radioBtn1.checked).toBe(true);

    fireEvent.click(radioBtn2);
    expect(handleChangeValue).toHaveBeenCalledWith('Title 2');

    expect(asFragment()).toMatchSnapshot();
  });
});
