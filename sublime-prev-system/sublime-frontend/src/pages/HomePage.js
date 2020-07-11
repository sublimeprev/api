import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import { 
  Title
} from 'react-admin';
import DashboardIcon from '@material-ui/icons/Dashboard';
import TrainingTodays from './TrainingTodays.js';

export const Dashboard = () => (
  <Card>
    <Title title="Bem Vindo" />
    <CardContent style={{ textAlign: 'center' }}>
      Cesar GYM Backoffice
    </CardContent>
    <TrainingTodays />
  </Card>
);

export default {
  list: Dashboard,
  icon: DashboardIcon,
  options: { label: 'Início' },
};
