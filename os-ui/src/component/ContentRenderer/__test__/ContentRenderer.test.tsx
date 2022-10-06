import { render } from '@testing-library/react';
import ContentRenderer from '../ContentRenderer';

const courseEntity = [
  {
    id: 1,
    title: 'React',
  },
  {
    id: 2,
    title: 'JS',
  },
];

describe('Create test case for ContentRenderer component', () => {
  test('It should match the snapshot', () => {
    const { asFragment } = render(
      <ContentRenderer
        isLoading={false}
        errorMessage=""
        entity={courseEntity}
        isMyLearningPathPage={false}
        errorFieldClassName="courseErrorStyles"
        render={(courseEntity) => (
          courseEntity.map((course:{id:number, title:string}, index:number) => (
            <div key={index}>
              <h1>{course.id}</h1>
              <p>{course.title}</p>
            </div>
          ))
        )}
      />,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
