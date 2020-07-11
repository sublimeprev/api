import { Box, Grid, Tab, Tabs, Toolbar } from '@material-ui/core';
import PeopleIcon from '@material-ui/icons/People';
import React from 'react';
import axios from 'axios';
import URL from './../providers/URLs';
import {
  Create,
  Datagrid,
  DateInput,
  DateTimeInput,
  DeleteButton,
  Edit,
  FormWithRedirect,
  List,
  ListButton,
  required,
  SaveButton,
  SimpleForm,
  TextField,
  TextInput,
  TopToolbar,
  ChipField,
  CheckboxGroupInput,
  ArrayField,
  SingleFieldList,
} from 'react-admin';
import { EnumCheckboxInput, EnumField } from '../components/Enums';
import { ListFilterWithDeleteds } from '../components/ListFilter';
import LocalDateTimeField from '../components/LocalDateTimeField';
import { TabPanel } from '../components/TabPanels';
import EvaluationsByUser from './EvaluationsByUser';
import TrainingsByUser from './TrainingsByUser';
import UserAgendaConfig from './UsersAgendaConfig';
import WeightControlByUser from './WeightControlByUser';
import RestoreButton from '../components/RestoreButton';

const typeTraining = [];
function getList(){

  axios.get(`${URL.baseURL}/api/type-training/all`,{
    headers: {
      'Accept': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  }).then(res => {
    typeTraining.length = 0;
    res.data.forEach(type => {
      type = {id: type.id, name:type.type}
      typeTraining.push(type)
      
    })
  })
}

export const UserList = props => (
  getList(),
  <List
    filters={<ListFilterWithDeleteds />}
    bulkActionButtons={false}
    {...props}
  >
    <Datagrid rowClick="edit">
      <TextField source="id" />
      <LocalDateTimeField source="updatedAt" />
      <TextField source="username" />
      <TextField source="email" />
      <TextField source="phone" />
      <EnumField source="roles" />
      <ArrayField source="listTypeExercise">
          <SingleFieldList>
              <ChipField source="type" label="Tipos"/>
          </SingleFieldList>
      </ArrayField>
    </Datagrid>
  </List>
);

const UserForm = props => {
  return (
    <FormWithRedirect
      {...props}
      redirect={false}
      render={formProps => (
        <form onSubmit={formProps.submit}>
          <Grid container spacing={2}>
            <Grid item xs={3}>
              <TextInput resource="users" source="id" disabled />
            </Grid>
            <Grid item xs={3}>
              <TextInput resource="users" source="updatedBy" disabled />
            </Grid>
            <Grid item xs={3}>
              <DateTimeInput resource="users" source="updatedAt" disabled />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="users"
                source="username"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <TextInput resource="users" source="newPassword" type="password" />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="users"
                source="email"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <TextInput resource="users" source="name" validate={required()} />
            </Grid>
            <Grid item xs={3}>
              <TextInput resource="users" source="city" validate={required()} />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="users"
                source="phone"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <DateInput
                resource="users"
                source="birthdate"
                validate={required()}
              />
            </Grid>
            <Grid item xs={5}>
              <EnumCheckboxInput resource="users" source="roles" />
            </Grid>
            <Grid item xs={3}>
              <DateInput resource="users" source="schedulingsEndDate" />
            </Grid>
            <Grid item xs={12}>
              <CheckboxGroupInput 
                resource = "users"
                source="typeExercise"
                choices={typeTraining} 
                />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                multiline
                resource="users"
                source="comments"
              />
            </Grid>
          </Grid>
          <Toolbar disableGutters>
            <Box display="flex" justifyContent="space-between" width="100%">
              <SaveButton
                saving={formProps.saving}
                handleSubmitWithRedirect={formProps.handleSubmitWithRedirect}
              />
              {!formProps.record.deleted ? (
                <DeleteButton
                  record={formProps.record}
                  resource={formProps.resource}
                  basePath={formProps.basePath}
                  undoable={formProps.undoable}
                />
              ) : (
                <RestoreButton
                  record={formProps.record}
                  resource={formProps.resource}
                  basePath={formProps.basePath}
                />
              )}
            </Box>
          </Toolbar>
        </form>
      )}
    />
  );
};

const UserTabs = props => {
  const [value, setValue] = React.useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  function a11yProps(index) {
    return {
      id: `tab-${index}`,
      'aria-controls': `tabpanel-${index}`,
    };
  }
  return (
    <div>
      <div>
        <Tabs value={value} onChange={handleChange} variant="standard">
          <Tab label="Geral" {...a11yProps(0)} />
          <Tab label="Treinos" {...a11yProps(1)} />
          <Tab label="Avaliações" {...a11yProps(2)} />
          <Tab label="Agendamentos Config" {...a11yProps(3)} />
          <Tab label="Controle de Peso" {...a11yProps(4)} />
        </Tabs>
      </div>
      <TabPanel value={value} index={0}>
        <UserForm {...props} />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <TrainingsByUser {...props} />
      </TabPanel>
      <TabPanel value={value} index={2}>
        <EvaluationsByUser {...props} />
      </TabPanel>
      <TabPanel value={value} index={3}>
        <UserAgendaConfig {...props} />
      </TabPanel>
      <TabPanel value={value} index={4}>
        <WeightControlByUser {...props} />
      </TabPanel>
    </div>
  );
};

export const UserEdit = props => {
  return (
    <Edit undoable={false} actions={<FormActions />} {...props}>
      <UserTabs {...props} />
    </Edit>
  );
};

export const UserCreate = props => (
  <Create undoable="false" actions={<FormActions />} {...props}>
    <SimpleForm>
      <TextInput source="username" validate={required()} />
      <TextInput source="encryptedPassword" type="password" validate={required()}/>
      <TextInput source="email" validate={required()} />
      <TextInput source="name" validate={required()} />
      <TextInput source="city" validate={required()} />
      <TextInput source="phone" validate={required()} />
      <DateInput source="birthdate" validate={required()} />
      <EnumCheckboxInput source="roles" />
      <CheckboxGroupInput 
        resource="users"
        source="typeExercise"  
        validate={required()} 
        choices={typeTraining}   
        
      />
      <TextInput
        multiline
        resource="users"
        source="comments"
      />
    </SimpleForm>
  </Create>
);

const FormActions = ({ basePath }) => (
  <TopToolbar>
    <ListButton basePath={basePath} />
  </TopToolbar>
);

export default {
  create: UserCreate,
  edit: UserEdit,
  list: UserList,
  icon: PeopleIcon,
};
