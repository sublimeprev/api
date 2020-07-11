import { Box, Grid, Tab, Tabs, Toolbar } from '@material-ui/core';
import TimerOffIcon from '@material-ui/icons/TimerOff';
import React from 'react';
import {
  Create,
  Datagrid,
  DateInput,
  DeleteButton,
  Edit,
  Filter,
  FormWithRedirect,
  List,
  ListButton,
  required,
  SaveButton,
  SimpleForm,
  TextField,
  TextInput,
  TopToolbar,
} from 'react-admin';
import LocalDateField from '../components/LocalDateField';
import { TabPanel } from '../components/TabPanels';

const TimeOffsFilter = props => (
  <Filter {...props}>
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

export const TimeOffsList = props => (
  <List
    filters={<TimeOffsFilter />}
    sort={{ field: 'date', order: 'DESC' }}
    {...props}
  >
    <Datagrid rowClick="edit">
      <LocalDateField source="date" />
      <TextField source="time" />
    </Datagrid>
  </List>
);

export const TimeOffsEdit = props => {
  return (
    <Edit undoable={false} actions={<FormActions />} {...props}>
      <TimeOffsTabs {...props} />
    </Edit>
  );
};

const TimeOffsTabs = props => {
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
        </Tabs>
      </div>
      <TabPanel value={value} index={0}>
        <TimeOffsForm {...props} />
      </TabPanel>
    </div>
  );
};

const TimeOffsForm = props => {
  return (
    <FormWithRedirect
      {...props}
      redirect={false}
      render={formProps => (
        <form onSubmit={formProps.submit}>
          <Grid container spacing={2}>
            <Grid item xs={4}>
              <DateInput
                resource="time-offs"
                source="date"
                validate={required()}
                InputLabelProps={{
                  shrink: true,
                }}
              />
            </Grid>
            <Grid item xs={4}>
              <TextInput
                resource="time-offs"
                source="time"
                type="time"
                InputLabelProps={{
                  shrink: true,
                }}
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

export const TimeOffsCreate = props => (
  <Create undoable="false" actions={<FormActions />} {...props}>
    <SimpleForm initialValues={{ status: 'MARCADO' }}>
      <TextInput
        source="date"
        validate={required()}
        type="date"
        InputLabelProps={{
          shrink: true,
        }}
      />
      <TextInput
        source="time"
        type="time"
        InputLabelProps={{
          shrink: true,
        }}
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
  create: TimeOffsCreate,
  edit: TimeOffsEdit,
  list: TimeOffsList,
  icon: TimerOffIcon,
};
