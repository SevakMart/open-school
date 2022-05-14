import { useSelector, useDispatch } from 'react-redux';
import { removeLoggedInUser } from '../../redux/Slices/loginUserSlice';
import { RootState } from '../../redux/Store';

const UserPage = () => {
  const dispatch = useDispatch();
  const userInfo = useSelector<RootState>((state) => state.userInfo);
  return (
    <>
      <h1>
        This is the homepage of user
        {' '}
        {(userInfo as any).id}
      </h1>
      <button type="button" onClick={() => dispatch(removeLoggedInUser())}>Logout</button>
    </>
  );
};
export default UserPage;
