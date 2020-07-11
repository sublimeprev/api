import React, { useRef } from 'react';
import MaterialTable, { MTableToolbar } from 'material-table';
import { GET_LIST, useDataProvider, useNotify } from 'react-admin';
import LocalDateTimeField from '../components/LocalDateTimeField';
import tableIcons from '../utils/TableIcons';
import i18nProvider, { messages } from '../i18n';

const controllerEndpoint = 'weight-controls-by-user';

function WeightControlByUser({ resource, record }) {
  const dataProvider = useDataProvider();
  const notify = useNotify();
  const tableRef = useRef(null);

  const columns = [
    {
      title: 'Data Criação',
      field: 'createdAt',
      defaultSort: 'desc',
      render: rowData => (
        <LocalDateTimeField record={rowData} source="createdAt" />
      ),
    },
    {
      title: 'Peso (Kg)',
      field: 'value',
      render: rowData => (
        <>{rowData.value ? rowData.value.toFixed(2).replace('.', ',') : ''}</>
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

  return (
    <>
      <MaterialTable
        title=""
        tableRef={tableRef}
        icons={tableIcons}
        columns={columns}
        localization={messages[i18nProvider.getLocale()].mbrnTable}
        data={query =>
          new Promise(async (resolve, reject) => {
            fetchData(query)
              .then(res => {
                resolve({
                  data: res.data,
                  totalCount: res.total,
                  page: query.page,
                });
              })
              .catch(error => {
                notify(error.message, 'warning');
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
      />
    </>
  );
}

export default WeightControlByUser;
