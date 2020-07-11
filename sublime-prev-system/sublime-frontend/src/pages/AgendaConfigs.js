import React, { useState, cloneElement } from 'react';
import axios from 'axios';
import URL from './../providers/URLs';
import {
  CircularProgress,
  Grid,
  Box,
  Toolbar,
  Tab,
  Tabs,
  makeStyles,
} from '@material-ui/core';
import ScheduleIcon from '@material-ui/icons/Schedule';
import SettingsIcon from '@material-ui/icons/Settings';
import {
  CheckboxGroupInput,
  Button,
  TopToolbar,
  Create,
  CreateButton,
  Datagrid,
  DateTimeInput,
  Edit,
  ExportButton,
  List,
  ListButton,
  NumberInput,
  required,
  SimpleForm,
  TextField,
  TextInput,
  SaveButton,
  FormWithRedirect,
  DeleteButton,
  minValue,
  maxValue,
  number,
  sanitizeListRestProps,
  useDataProvider,
  useNotify,
  ChipField, 
  ArrayField,
  SingleFieldList,
  RadioButtonGroupInput,
} from 'react-admin';
import ListFilter from '../components/ListFilter';
import { TabPanel } from '../components/TabPanels';
import { EnumRadioInput, EnumRadioField, } from '../components/Enums';
import UsersInAgendaConfig from './UsersInAgendaConfig';

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

 return typeTraining
}

const useStyles = makeStyles({
  button: {
    '& span': {
      '& span': {
        marginRight: 0,
      },
    },
  },
});
const ListActions = ({
  currentSort,
  className,
  resource,
  filters,
  displayedFilters,
  exporter, // you can hide ExportButton if exporter = (null || false)
  filterValues,
  permanentFilter,
  hasCreate, // you can hide CreateButton if hasCreate = false
  basePath,
  selectedIds,
  onUnselectItems,
  showFilter,
  maxResults,
  total,
  ...rest
}) => {
  const dataProvider = useDataProvider();
  const notify = useNotify();
  const [loading, setLoading] = useState(false);
  const classes = useStyles();

  const createSchedulings = async () => {
    setLoading(true);
    try {
      await dataProvider('GET', `${resource}/create-schedulings`);
      notify('Agendamentos cadastrados com sucesso.');
    } catch (error) {
      notify(error.message, 'warning');
    }
    setLoading(false);
  };
  return (
    <TopToolbar className={className} {...sanitizeListRestProps(rest)}>
      {filters &&
        cloneElement(filters, {
          resource,
          showFilter,
          displayedFilters,
          filterValues,
          context: 'button',
        })}
      <CreateButton basePath={basePath} />
      <ExportButton
        disabled={total === 0}
        resource={resource}
        sort={currentSort}
        filter={{ ...filterValues, ...permanentFilter }}
        exporter={exporter}
        maxResults={maxResults}
      />
      {/* Add your custom actions */}
      <Button
        disabled={loading}
        onClick={() => {
          createSchedulings();
        }}
        label="Rodar Agendamentos"
        startIcon={<ScheduleIcon />}
        style={{ marginLeft: 4 }}
        className={classes.button}
        endIcon={loading && <CircularProgress size={18} />}
      ></Button>
    </TopToolbar>
  );
};

ListActions.defaultProps = {
  selectedIds: [],
  onUnselectItems: () => null,
};

export const AgendaConfigsList = props => (
  getList(),
  <List
    {...props}
    filters={<ListFilter />}
    bulkActionButtons={false}
    sort={{ field: 'dayOfWeek', order: 'ASC' }}
    actions={<ListActions />}
  >
    <Datagrid rowClick="edit">
      <EnumRadioField source="dayOfWeek" />
      <TextField source="time" />
      <TextField source="maxLimit" />
      <ArrayField source="listTypeExercise">
          <SingleFieldList>
              <ChipField source="type" label="Tipos"/>
          </SingleFieldList>
      </ArrayField>
    </Datagrid>
  </List>
);

const AgendaConfigsForm = props => {
  return (
    <FormWithRedirect
      {...props}
      redirect={false}
      render={formProps => (
        <form onSubmit={formProps.submit}>
          <Grid container spacing={2}>
            <Grid item xs={3}>
              <TextInput resource="agenda-configs" source="id" disabled />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="agenda-configs"
                source="updatedBy"
                disabled
              />
            </Grid>
            <Grid item xs={3}>
              <DateTimeInput
                resource="agenda-configs"
                source="updatedAt"
                disabled
              />
            </Grid>
            <Grid item xs={12}>
              <EnumRadioInput
                resource="agenda-configs"
                source="dayOfWeek"
                validate={required()}
              />
            </Grid>
            <Grid item xs={3}>
              <TextInput
                resource="agenda-configs"
                source="time"
                validate={required()}
                type="time"
                InputLabelProps={{
                  shrink: true,
                }}
              />
            </Grid>
            <Grid item xs={3}>
              <NumberInput
                resource="agenda-configs"
                source="maxLimit"
                validate={[
                  required(),
                  number(),
                  minValue(0),
                  maxValue(2147483647),
                ]}
                step={1}
              />
            </Grid>
            <Grid item xs={12}>
              <RadioButtonGroupInput 
                resource="agenda-configs"
                source="typeExercise"  
                validate={required()} 
                choices={typeTraining} 
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

const AgendaConfigsTabs = props => {
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
          <Tab label="Usuários" {...a11yProps(1)} />
        </Tabs>
      </div>
      <TabPanel value={value} index={0}>
        <AgendaConfigsForm {...props} />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <UsersInAgendaConfig {...props} />
      </TabPanel>
    </div>
  );
};

export const AgendaConfigsEdit = props => {
  return (
    <Edit undoable={false} actions={<FormActions />} {...props}>
      <AgendaConfigsTabs {...props} />
    </Edit>
  );
};

export const AgendaConfigsCreate = props => (
  <Create
    {...props}
    undoable="false"
    actions={<FormActions />}
    redirect={false}
  >
    <SimpleForm>
      <EnumRadioInput source="dayOfWeek" validate={required()} />
      <TextInput
        source="time"
        validate={required()}
        type="time"
        InputLabelProps={{
          shrink: true,
        }}
      />
      <NumberInput
        resource="agendaConfigs"
        source="maxLimit"
        validate={[required(), number(), minValue(0), maxValue(2147483647)]}
        step={1}
      />  
      <RadioButtonGroupInput 
        style={{width: 500}}
        resource="agenda-configs"
        source="typeExercise"  
        validate={required()} 
        choices={typeTraining}   
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
  create: AgendaConfigsCreate,
  edit: AgendaConfigsEdit,
  list: AgendaConfigsList,
  icon: SettingsIcon,
};
