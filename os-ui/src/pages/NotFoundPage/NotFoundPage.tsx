import styles from './NotFoundPage.module.scss';

const NotFoundPage = () => {
  const { mainContainer } = styles;
  return (
    <>
      <div className={mainContainer}>
        <h1>404 Not Found</h1>
      </div>
    </>
  );
};
export default NotFoundPage;
