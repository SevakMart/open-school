import { render, screen } from '@testing-library/react';
import CategoryCard from '../CategoryProfile';

const categoryProfile = {
  title: 'JavaScript',
  logoPath: 'https://reactjs.org/logo-og.png',
  subCategoryCount: 3,

};
describe('Create unit tests of category profile', () => {
  test('Make a snapshot tests', () => {
    const { asFragment } = render(<CategoryCard category={categoryProfile} />);
    expect(asFragment()).toMatchSnapshot();
  });
  test('Verify if category info is contained in the component', () => {
    render(<CategoryCard category={categoryProfile} />);
    const titleElem = screen.queryByTestId('JavaScript');
    expect(titleElem).toBeInTheDocument();
    expect(titleElem).toHaveTextContent('JavaScript');
  });
});
