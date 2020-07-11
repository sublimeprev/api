import React, { useEffect, useState, useRef } from 'react';
import { Button, Grid, Link, Paper, Toolbar } from '@material-ui/core';
import MaterialTable, { MTableToolbar } from 'material-table';
import {
  CREATE,
  GET_LIST,
  GET_ONE,
  UPDATE,
  Loading,
  useDataProvider,
  useNotify,
} from 'react-admin';
import LocalDateTimeField from '../components/LocalDateTimeField';
import FileUploadField from '../components/SimpleFileUploadField';
import tableIcons from '../utils/TableIcons';
import URLs from '../providers/URLs';
import i18nProvider, { messages } from '../i18n';

const defaultFormData = {
  encodedContent: '',
  newFileBase64: '',
  newFileName: '',
};

const controllerEndpoint = 'evaluations-by-user';

function EvaluationsByUserForm({ userId, onSubmit, formDataId }) {
  const dataProvider = useDataProvider();
  const notify = useNotify();
  const [formData, setFormData] = useState(defaultFormData);
  const [formDataStored, setFormDataStored] = useState(defaultFormData);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    if (!formDataId) {
      setLoading(false);
      setFormData(defaultFormData);
    } else {
      dataProvider(GET_ONE, `${controllerEndpoint}/${userId}`, {
        id: formDataId,
      })
        .then(({ data }) => {
          setLoading(false);
          setFormData(data);
          setFormDataStored(data);
        })
        .catch(error => {
          setLoading(false);
          notify(error.message, 'warning');
        });
    }
  }, [formDataId, userId, dataProvider, notify]);

  const saveTraining = () => {
    setLoading(true);
    const endpoint = `${controllerEndpoint}/${userId}`;
    const data = {};
    if (formDataStored.encodedContent !== formData.encodedContent) {
      data.newFileBase64 = formData.encodedContent;
      data.newFileName = formData.fileKey;
    }
    if (!formData.id) {
      dataProvider(CREATE, endpoint, {
        data: data,
      })
        .then(({ data }) => {
          setLoading(false);
          onSubmit();
        })
        .catch(error => {
          setLoading(false);
          notify(error.message, 'warning');
        });
    } else {
      dataProvider(UPDATE, endpoint, {
        id: formData.id,
        data: data,
      })
        .then(({ data }) => {
          setLoading(false);
          onSubmit();
        })
        .catch(error => {
          setLoading(false);
          notify(error.message, 'warning');
        });
    }
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
      }}
    >
      <form onSubmit={saveTraining}>
        <Grid container style={{ paddingLeft: 24, paddingRight: 24 }}>
          <Grid item xs={12} style={{ marginBottom: 10 }}>
            {formDataId && (
              <>
                <label>ID:&nbsp;</label>
                <span>{formDataId}</span>
              </>
            )}
          </Grid>
          <Grid item xs={12}>
            <FileUploadField
              label={formDataId ? 'Substituir anexo: ' : 'Adicinar anexo: '}
              value={{
                name: formData.fileKey,
                base64: formData.encodedContent,
              }}
              onChange={newFile => {
                setFormData(old => ({
                  ...old,
                  fileKey: newFile.name,
                  encodedContent: newFile.base64,
                }));
              }}
            />
          </Grid>
        </Grid>
        <Toolbar>
          <Button
            size="small"
            type="submit"
            variant="contained"
            color="secondary"
          >
            Salvar
          </Button>
        </Toolbar>
      </form>
    </Paper>
  );
}

function EvaluationsByUser({ resource, record }) {
  const dataProvider = useDataProvider();
  const [addMode, setAddMode] = useState(false);
  const [formDataId, setFormDataId] = useState(null);
  const tableRef = useRef(null);

  const columns = [
    {
      title: 'Data',
      field: 'createdAt',
      defaultSort: 'desc',
      render: rowData => (
        <LocalDateTimeField record={rowData} source="createdAt" />
      ),
    },
    {
      title: 'Arquivo',
      field: 'fileKey',
      render: rowData => (
        <Button component={Link} onClick={() => openFile(rowData.id)}>
          {rowData.fileKey}
        </Button>
      ),
    },
  ];

  const fetchData = query => {
    return dataProvider(GET_LIST, `${controllerEndpoint}/${record.id}`, {
      pagination: { page: query.page + 1, perPage: query.pageSize },
      sort: {
        order: !query.orderDirection ? 'asc' : query.orderDirection,
        field: !query.orderBy ? 'id' : query.orderBy.field,
      },
      filter: { q: query.search },
    });
  };

  const openFile = id => {
    window.open(
      `${URLs.baseURL}/api/${controllerEndpoint}/${
        record.id
      }/${id}/file?access_token=${localStorage.getItem('token')}`
    );
  };

  return (
    <>
      {addMode && (
        <EvaluationsByUserForm
          onSubmit={() => {
            setFormDataId(null);
            setAddMode(false);
            tableRef.current && tableRef.current.onQueryChange();
          }}
          userId={record.id}
          formDataId={formDataId}
        />
      )}
      <MaterialTable
        title=""
        tableRef={tableRef}
        icons={tableIcons}
        columns={columns}
        localization={messages[i18nProvider.getLocale()].mbrnTable}
        data={query =>
          new Promise(async (resolve, reject) => {
            fetchData(query).then(res => {
              resolve({
                data: res.data,
                totalCount: res.total,
                page: query.page,
              });
            });
          })
        }
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
            tooltip: 'Adicionar Treino',
            isFreeAction: true,
            onClick: event => {
              setFormDataId(null);
              setAddMode(!addMode);
            },
          },
          {
            icon: tableIcons.Edit,
            tooltip: 'Editar Treino',
            onClick: (event, rowData) => {
              setAddMode(true);
              setFormDataId(rowData.id);
            },
          },
        ]}
      />
    </>
  );
}

export default EvaluationsByUser;
