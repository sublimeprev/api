import React from 'react';
import { Box, Grid, Tab, Tabs, Toolbar } from '@material-ui/core';
import PregnantWomanIcon from '@material-ui/icons/PregnantWoman';
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
  Loading,
  useNotify,
} from 'react-admin';
import { EnumRadioInput, EnumField } from '../components/Enums';
import { ListFilterWithDeleteds } from '../components/ListFilter';
import { TabPanel } from '../components/TabPanels';
import RestoreButton from '../components/RestoreButton';
import AddressMother from './AddressMother';
import ChildrenMother from './ChildrenMother';
import ProcessMother from './ProcessMother';
import LastCompanyMother from './LastCompany';


export const MotherList = props => (
  <List
    filters={<ListFilterWithDeleteds />}
    bulkActionButtons={false}
    {...props}
  >
    <Datagrid rowClick="edit">
      <TextField source="name" />
      <TextField source="email" />
      <TextField source="phone" />
      <TextField source="birthdate" />
    </Datagrid>
  </List>
);

const MotherForm = props => {
  return (
    <FormWithRedirect
      {...props}
      redirect={false}
      render={formProps => (
        <form onSubmit={formProps.submit}>
          <Grid container spacing={2}>
            <Grid item xs={3}>
              <TextInput resource="mothers" source="id" disabled />
            </Grid>
            <Grid item xs={3}>
              <TextInput resource="mothers" source="createdBy" disabled />
            </Grid>
            <Grid item xs={3}>
              <TextInput resource="mothers" source="updatedBy" disabled />
            </Grid>
            <Grid item xs={3}>
              <DateTimeInput resource="mothers" source="createdAt" disabled/>
            </Grid>
            <Grid item xs={3}>
              <DateTimeInput resource="mothers" source="updatedAt" disabled/>
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="mothers"
                source="name"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="mothers"
                source="email"
               
              />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="mothers"
                source="cpf"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="mothers"
                source="rg"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="mothers"
                source="pis"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="mothers"
                source="phone"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="mothers"
                source="motherName"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="mothers"
                source="fatherName"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <DateInput
                resource="mothers"
                source="birthdate"
                validate={required()}
              />
            </Grid>
            <Grid item xs={12}>
              <EnumRadioInput
                resource="mothers"
                source="maritalStatus"
                validate={required()}
              />
            </Grid>
            <Grid item xs={12}>
              <EnumRadioInput
                resource="mothers"
                source="schooling"
                validate={required()}
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

const MotherTabs = props => {
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
          <Tab label="Endereço" {...a11yProps(1)} />
          <Tab label="Filho" {...a11yProps(2)} />
          <Tab label="Última Empresa" {...a11yProps(3)} />
          <Tab label="Processo" {...a11yProps(4)} />
        </Tabs>
      </div>
      <TabPanel value={value} index={0}>
        <MotherForm {...props} />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <AddressMother {...props} />
      </TabPanel>
      <TabPanel value={value} index={2}>
        <ChildrenMother {...props} />
      </TabPanel>
      <TabPanel value={value} index={3}>
        <LastCompanyMother {...props} />
      </TabPanel>
      <TabPanel value={value} index={4}>
        <ProcessMother {...props} />
      </TabPanel>
    </div>
  );
};

export const MotherEdit = props => {
  return (
    <Edit undoable={false} actions={<FormActions />} {...props}>
      <MotherTabs {...props} />
    </Edit>
  );
};

export const MotherCreate = props => (
  <Create undoable="false" actions={<FormActions />} {...props}>
    <SimpleForm>
      <TextInput  source="email" />
      <TextInput source="name" validate={required()} />
      <TextInput source="phone" validate={required()} />
      <DateInput source="birthdate" validate={required()} />
      <TextInput source="cpf" validate={required()} />
      <TextInput source="rg" validate={required()} />
      <TextInput source="pis" validate={required()} />
      <TextInput source="motherName"/>
      <TextInput source="fatherName" />
      <EnumRadioInput
        resource="mothers"
        source="schooling"
        validate={required()}
      />
      <EnumRadioInput
        resource="mothers"
        source="maritalStatus"
        validate={required()}
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
  create: MotherCreate,
  edit: MotherEdit,
  list: MotherList,
  icon: PregnantWomanIcon,
};