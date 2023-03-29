export const mockUseNavigate = jest.fn();
export const reactRouterDomMock = {
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockUseNavigate,
  useLocation: () => ({ search: '' }),
};
