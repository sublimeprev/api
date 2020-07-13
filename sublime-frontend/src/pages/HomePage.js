import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import { 
  Title
} from 'react-admin';
import DashboardIcon from '@material-ui/icons/Dashboard';
import Logo from '../img/logo_transparente.png';
import Sublime from '../img/sublime.png';

export const Dashboard = () => (
  <Card>
    <Title title="Bem Vindo" />
    <CardContent style={{ textAlign: 'center'}}>
      <h1>Backoffice</h1>
    </CardContent>
    <CardContent style={{ textAlign: 'center'}}>
      <img style={{ width:'35%'}} src={Logo} alt="Logo sublimeprev"></img>
    </CardContent>
    <CardContent style={{ textAlign: 'center'}}>
      <img style={{ width:'35%'}} src={Sublime} alt="Logo sublimeprev"></img>
    </CardContent>
  </Card>
);

export default {
  list: Dashboard,
  icon: DashboardIcon,
  options: { label: 'Início' },
};
