import { BrowserRouter as Router } from 'react-router-dom';
import AppRoutes from './component/AppRoutes/Routes';
// import CourseModulePage from './pages/CourseModulePage/CourseModulePage';

const App = () => (
  <Router>
    <AppRoutes />

    {/* <CourseModulePage /> */}
  </Router>
);

export default App;
