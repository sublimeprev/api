import React, { useEffect, useState, useRef } from 'react';
import {
  AutocompleteArrayInput,
  Confirm,
  DELETE,
  Loading,
  ReferenceArrayInput,
  required,
  useDataProvider,
  useNotify,
} from 'react-admin';
import { Button, Box, Grid, Paper } from '@material-ui/core';
import arrayMutators from 'final-form-arrays';
import { Form } from 'react-final-form';
import MaterialTable, { MTableToolbar } from 'material-table';
import tableIcons from '../utils/TableIcons';
import i18nProvider, { messages } from '../i18n';
import SaveIcon from '@material-ui/icons/Save';

const controllerEndpoint = 'user-agenda-configs/users-by-agenda';

const defaultSubscription = {
  submitting: true,
  pristine: true,
  valid: true,
  invalid: true,
};
let setFormValue = null;
function UsersInAgendaConfigForm({ agendaConfigId, onSubmit }) {
  const dataProvider = useDataProvider();
  const notify = useNotify();
  const [loading, setLoading] = useState(false);

  const handleSubmit = values => {
    setLoading(true);
    const endpoint = `${controllerEndpoint}/${agendaConfigId}?usersIds=${values.usersIds}`;
    dataProvider('POST', endpoint, {})
      .then(() => {
        setLoading(false);
        onSubmit();
      })
      .catch(error => {
        setLoading(false);
        notify(error.message, 'warning');
      });
  };

  if (loading) return <Loading />;
  return (
    <Paper
      style={{
        boxShadow:
          '0px 3px 1px -2px rgba(0,0,0,0.2), 0px 2px 2px 0px rgba(0,0,0,0.14), 0px 1px 5px 0px rgba(0,0,0,0.12)',
        paddingTop: 20,
        paddingBottom: 20,
        marginBottom: 10,
        paddingLeft: 20,
        paddingRight: 20,
      }}
    >
      <Form
        onSubmit={handleSubmit}
        subscription={defaultSubscription} // don't redraw entire form each time one field changes
        key="usersInAgendaConfig" // support for refresh button
        keepDirtyOnReinitialize
        mutators={{
          ...arrayMutators,
          setValue: ([field, value], state, { changeValue }) => {
            changeValue(state, field, () => value);
          },
        }}
        render={({ form, ...formProps }) => {
          if (!setFormValue) setFormValue = form.mutators.setValue;
          return (
            <form onSubmit={formProps.handleSubmit}>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <ReferenceArrayInput
                    label="Usuário"
                    source="usersIds"
                    reference="users"
                    validate={required()}
                    sort={{ field: 'name', order: 'ASC' }}
                    resource={''}
                  >
                    <AutocompleteArrayInput optionText="name" />
                  </ReferenceArrayInput>
                </Grid>
              </Grid>
              <Grid container style={{ marginTop: 10 }}>
                <Grid item xs={12} style={{ backgroundColor: 'none' }}>
                  <Box display="flex" justifyContent="flex-end" width="100%">
                    <Button
                      type="submit"
                      variant="contained"
                      color="primary"
                      endIcon={<SaveIcon />}
                    >
                      Adicionar Usuários
                    </Button>
                  </Box>
                </Grid>
              </Grid>
            </form>
          );
        }}
      />
    </Paper>
  );
}

function UsersInAgendaConfig({ record }) {
  const dataProvider = useDataProvider();
  const [data, setData] = useState([]);
  const [addMode, setAddMode] = useState(false);
  const [loading, setLoading] = useState(false);
  const tableRef = useRef(null);
  const [openConfirmDelete, setOpenConfirmDelete] = useState(false);
  const [selectedUsers, setSelectedUsers] = useState([]);

  const columns = [
    {
      title: 'Nome',
      field: 'name',
      defaultSort: 'asc',
    },
    {
      title: 'E-mail',
      field: 'email',
    },
  ];

  useEffect(() => {
    fetchData();
    // eslint-disable-next-line
  }, []);

  const fetchData = async () => {
    setLoading(true);
    const { data } = await dataProvider(
      'GET',
      `${controllerEndpoint}/${record.id}`
    );
    setData(data);
    setLoading(false);
  };

  const deleteUsersFromAgendaConfig = async () => {
    const usersIds = selectedUsers.map(item => item.id);
    setLoading(true);
    setOpenConfirmDelete(false);
    await dataProvider(DELETE, `${controllerEndpoint}`, {
      id: `${record.id}?usersIds=${usersIds}`,
    });
    await fetchData();
  };

  return (
    <>
      {addMode && (
        <UsersInAgendaConfigForm
          onSubmit={() => {
            setAddMode(false);
            fetchData();
          }}
          agendaConfigId={record.id}
        />
      )}
      <Confirm
        isOpen={openConfirmDelete}
        loading={loading}
        title="Remover Usuários"
        content="Você tem certeza que deseja remover os usuários selecionados da configuração?"
        onConfirm={() => deleteUsersFromAgendaConfig()}
        onClose={() => setOpenConfirmDelete(false)}
      />
      <MaterialTable
        title=""
        tableRef={tableRef}
        icons={tableIcons}
        columns={columns}
        localization={messages[i18nProvider.getLocale()].mbrnTable}
        data={data}
        isLoading={loading}
        components={{
          Toolbar: props => (
            <div>
              <MTableToolbar {...props} />
            </div>
          ),
        }}
        actions={[
          {
            icon: () => (
              <div style={{ whiteSpace: 'nowrap' }}>
                <tableIcons.Add />
                <span style={{ fontSize: 20, verticalAlign: 'top' }}>
                  Adicionar
                </span>
              </div>
            ),
            tooltip: 'Adicionar Usuário',
            isFreeAction: true,
            onClick: event => {
              setAddMode(!addMode);
            },
          },
          {
            icon: tableIcons.Delete,
            tooltip: 'Remover Usuários Selecionados',
            onClick: (event, data) => {
              setSelectedUsers(data);
              setOpenConfirmDelete(true);
            },
          },
        ]}
        options={{
          selection: true,
        }}
      />
    </>
  );
}

export default UsersInAgendaConfig;
