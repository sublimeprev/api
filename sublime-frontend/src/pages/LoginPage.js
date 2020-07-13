import React from 'react';
import { Login, LoginForm } from 'react-admin';

const MyLoginPage = () => (
  <Login style={{backgroundImage:"radial-gradient(circle at 50% 14em, #929280 10%, #929292 60%, #929292 10%)"}} loginForm={<LoginForm />} />
);

export default MyLoginPage;
