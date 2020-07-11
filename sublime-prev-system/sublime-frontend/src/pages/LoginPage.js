import React from 'react';
import { Login, LoginForm } from 'react-admin';

const MyLoginPage = () => (
  <Login backgroundImage="/background.jpg" loginForm={<LoginForm />} />
);

export default MyLoginPage;
