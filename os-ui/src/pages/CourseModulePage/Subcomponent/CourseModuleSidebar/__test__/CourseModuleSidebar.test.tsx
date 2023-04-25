import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import CourseModuleSidebar from '../CourseModuleSidebar';

describe('CourseModuleSidebar', () => {
  const handleChangeValueMock = jest.fn();
  const modulesMock = [{ title: 'Module 1', description: 'Module 1 description', moduleItemSet: [{}, {}] },
    {
      title: 'Module 2',
      description: 'Module 2 description',
      moduleItemSet: [{}, {}],
    },
  ];

  test('renders the component', () => {
    const { asFragment } = render(
      <CourseModuleSidebar
        value="Overview"
        title="Course Name"
        handleChangeValue={handleChangeValueMock}
        modules={modulesMock}
        setDisBtnPosition={jest.fn()}
      />,
    );
    expect(screen.getByText('Course Name')).toBeInTheDocument();
    expect(screen.getByText('Overview')).toBeInTheDocument();
    expect(screen.getByAltText('chevron')).toBeInTheDocument();
    expect(screen.getByTestId('moduleListOpen')).toBeInTheDocument();
    expect(asFragment()).toMatchSnapshot();
  });

  test('toggles the module list on click', () => {
    const { asFragment } = render(
      <CourseModuleSidebar
        value="Overview"
        title="Course Name"
        handleChangeValue={handleChangeValueMock}
        modules={modulesMock}
        setDisBtnPosition={jest.fn()}
      />,
    );
    fireEvent.click(screen.getByAltText('chevron'));
    expect(screen.getByTestId('moduleListClosed')).toBeInTheDocument();
    expect(asFragment()).toMatchSnapshot();

    fireEvent.click(screen.getByAltText('chevron'));
    expect(screen.getByTestId('moduleListOpen')).toBeInTheDocument();
    expect(asFragment()).toMatchSnapshot();
  });

  test('calls handleChangeValue when clicking the Overview button', () => {
    const { asFragment } = render(
      <CourseModuleSidebar
        value="Overview"
        title="Course Name"
        handleChangeValue={handleChangeValueMock}
        modules={modulesMock}
        setDisBtnPosition={jest.fn()}
      />,
    );
    fireEvent.click(screen.getByText('Overview'));
    expect(handleChangeValueMock).toHaveBeenCalledWith('Overview');
    expect(asFragment()).toMatchSnapshot();
  });
});
