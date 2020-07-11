import React, { Fragment} from 'react';
import { 
  Box, 
  Grid, 
  Tab, 
  Tabs, 
  Toolbar,
 } from '@material-ui/core';
import ScheduleIcon from '@material-ui/icons/Schedule';
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
  TextField,
  TextInput,
  TopToolbar,
  Filter,
  ChipField
} from 'react-admin';
import { EnumRadioField, EnumRadioInput } from '../components/Enums';
import LocalDateField from '../components/LocalDateField';
import { TabPanel } from '../components/TabPanels';
import CancelBulkButton from '../actions/CancelBulkButton';
import CreateShenduling from '../pages/CreateSchenduling';

const SchedulingsFilter = props => (
  <Filter {...props}>
    <TextInput
      label="Usuário"
      source="user"
      InputLabelProps={{
        shrink: true,
      }}
      alwaysOn
    />
    <DateInput
      label="Data"
      source="date"
      InputLabelProps={{
        shrink: true,
      }}
      alwaysOn
    />
    <TextInput
      source="time"
      type="time"
      InputLabelProps={{
        shrink: true,
      }}
      alwaysOn
    />
  </Filter>
);

const SchedulingBulkActionButtons = props => (
  <Fragment>
    <CancelBulkButton label="Cancel" {...props} />
  </Fragment>
);

export const SchedulingsList = props => (
  <List
    filters={<SchedulingsFilter />}
    sort={{ field: 'date', order: 'DESC' }}
    bulkActionButtons={<SchedulingBulkActionButtons />}
    {...props}
    
  >
    <Datagrid rowClick="edit">
      <TextField source="id" />
      <TextField source="userDesc" sortBy="user.name" />
      <LocalDateField source="date" />
      <TextField source="time" />
      <EnumRadioField source="status" type="schedulingStatus" />
      <ChipField source="typeExercise" />
    </Datagrid>
  </List>
);

export const SchedulingsEdit = props => {
  return (
    <Edit undoable={false} actions={<FormActions />} {...props}>
      <SchedulingsTabs {...props} />
    </Edit>
  );
};

const SchedulingsTabs = props => {
  const [value, setValue] = React.useState();
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
        </Tabs>
      </div>
      <TabPanel value={value} index={0}>
        <SchedulingsForm {...props} />
      </TabPanel>
    </div>
  );
};

const SchedulingsForm = props => {
  return (
    <FormWithRedirect
      {...props}
      redirect={false}
      render={formProps => (
        <form onSubmit={formProps.submit}>
          <Grid container spacing={2}>
            <Grid item xs={4}>
              <TextInput resource="schedulings" source="id" disabled />
            </Grid>
            <Grid item xs={4}>
              <TextInput resource="schedulings" source="updatedBy" disabled />
            </Grid>
            <Grid item xs={4}>
              <DateTimeInput
                resource="schedulings"
                source="updatedAt"
                disabled
              />
            </Grid>
            <Grid item xs={4}>
              <TextInput resource="schedulings" source="userDesc" disabled />
            </Grid>
            <Grid item xs={4}>
              <DateInput
                resource="schedulings"
                source="date"
                validate={required()}
                InputLabelProps={{
                  shrink: true,
                }}
              />
            </Grid>
            <Grid item xs={4}>
              <TextInput
                resource="schedulings"
                source="time"
                validate={required()}
                type="time"
                InputLabelProps={{
                  shrink: true,
                }}
              />
            </Grid>
            <Grid item xs={4}>
              <EnumRadioInput
                resource="schedulings"
                source="status"
                type="schedulingStatus"
              />
            </Grid>
          </Grid>
          <Toolbar disableGutters>
            <Box display="flex" justifyContent="space-between" width="100%">
              <SaveButton
                saving={formProps.saving}
                handleSubmitWithRedirect={formProps.handleSubmitWithRedirect}
              />
              <DeleteButton
                record={formProps.record}
                resource={formProps.resource}
                basePath={formProps.basePath}
                undoable={formProps.undoable}
              />
            </Box>
          </Toolbar>
        </form>
      )}
    />
  );
};

export const SchedulingsCreate = props => (
  <Create undoable="false" actions={<FormActions />} {...props}>
    <CreateShenduling/>
  </Create>
);

const FormActions = ({ basePath }) => (
  <TopToolbar>
    <ListButton basePath={basePath} />
  </TopToolbar>
);

export default {
  create: SchedulingsCreate,
  edit: SchedulingsEdit,
  list: SchedulingsList,
  icon: ScheduleIcon,
};
