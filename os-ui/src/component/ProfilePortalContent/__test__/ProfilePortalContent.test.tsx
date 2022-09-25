import { render } from '@testing-library/react';
import ProfilePortalContent from '../ProfilePortalContent';

describe('Create unit tests for ProfilePortalContent component', () => {
  test('Create a snapshot test', () => {
    const { asFragment } = render(
      <ProfilePortalContent
        isSignOut={false}
        children={<h2>Hello world</h2>}
        icon={<p>{'>'}</p>}
      />,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
