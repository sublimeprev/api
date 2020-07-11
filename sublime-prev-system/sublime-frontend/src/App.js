import React from 'react';
import { Admin, Resource } from 'react-admin';
import Roles from './constants/Roles';
import i18nProvider from './i18n';
// import AgendaConfigs from './pages/AgendaConfigs';
// import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import NotFound from './pages/NotFound';
// import Notifications from './pages/Notifications';
// import Schedulings from './pages/Schedulings';
// import TimeOffs from './pages/TimeOffs';
import Users from './pages/Users';
// import TypeExercise from './pages/TypeExercise';
import authProvider from './providers/authProvider';
import dataProvider from './providers/dataProvider';
import './styles/App.css';
import theme from './themes/theme';

const fetchResources = permissions => {
  let arr = [];
  // arr.push(<Resource name="home" {...HomePage} />);
  if (permissions.includes(Roles.ADMIN)) {
    // arr.push(<Resource name="home" {...HomePage} />);
    arr.push(<Resource name="users" {...Users} />);
    // arr.push(<Resource name="notifications" {...Notifications} />);
    // arr.push(<Resource name="agenda-configs" {...AgendaConfigs} />);
    // arr.push(<Resource name="schedulings" {...Schedulings} />);
    // arr.push(<Resource name="time-offs" {...TimeOffs} />);
    // arr.push(<Resource name="type-training" {...TypeExercise} />);
  }
  return arr;
};

function App() {
  return (
    <div className="App">
      <Admin
        theme={theme}
        i18nProvider={i18nProvider}
        authProvider={authProvider}
        dataProvider={dataProvider}
        loginPage={LoginPage}
        catchAll={NotFound}
      >
        {fetchResources}
      </Admin>
    </div>
  );
}

export default App;
