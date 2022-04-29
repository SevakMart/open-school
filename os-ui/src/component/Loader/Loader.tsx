import styles from './Loader.module.scss';

const Loader = () => {
  const { mainContent, loader } = styles;
  return (
    <div className={mainContent}>
      <div className={loader}>
        <span />
        <span />
        <span />
      </div>
    </div>
  );
};
export default Loader;
