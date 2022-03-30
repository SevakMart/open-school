import { render, screen } from '@testing-library/react';
import CategoryCard from '../CategoryProfile';

const categoryProfile = {
  title: 'JavaScript',
  logoPath: 'https://reactjs.org/logo-og.png',

};
describe('Create unit tests of category profile', () => {
  test('Make a snapshot tests', () => {
    const { asFragment } = render(
      <CategoryCard
        title={categoryProfile.title}
        logoPath={categoryProfile.logoPath}
      />,
    );
    expect(asFragment()).toMatchSnapshot();
  });
  test('Verify if category info is contained in the component', () => {
    render(
      <CategoryCard
        title={categoryProfile.title}
        logoPath={categoryProfile.logoPath}
      />,
    );
    const titleElem = screen.queryByTestId('JavaScript');
    expect(titleElem).toBeInTheDocument();
    expect(titleElem).toHaveTextContent('JavaScript');
  });
});
