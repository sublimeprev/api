import React, { useState, useEffect } from 'react';
import {
  ReferenceInput,
  AutocompleteInput,
  required,
  TextInput,
  useDataProvider,
  useNotify,
} from 'react-admin';
import arrayMutators from 'final-form-arrays';
import { Form } from 'react-final-form';
import { Button, Box, Card, CardContent, Grid } from '@material-ui/core';
import { Title } from 'react-admin';
import NotificationsIcon from '@material-ui/icons/Notifications';
import SendIcon from '@material-ui/icons/Send';

const defaultNotification = {
  title: 'Sublime Prev Salario Maternidade',
  content: '',
  userId: null,
};

let setFormValue = null;
const Notifications = () => {
  const [insertMode, setInsertMode] = useState('one');
  const [renderTypeExrcise, setRenderTypeExrcise] = useState(false);
  const dataProvider = useDataProvider();
  const notify = useNotify();

  useEffect(() => {
    setFormValue('userId', null);
  }, [insertMode]);

  const endpoint = (values) => {
    
      {if (renderTypeExrcise) {
        return `notifications/send/all-users-type-exercise/${values.typeExerciseId}`;
      }else{
        if(insertMode === "broadcast"){
          return 'notifications/send-broadcast'
        }else{
          return `notifications/send/${values.userId}`
        } 
      }
        
      
    }
    
  }

  const handleSubmit = values => {
    console.log(values);

    dataProvider('POST', endpoint(values), {
      title: values.title,
      content: values.content,
    })
      .then(() => {
        notify('Notificação enviada com sucesso');
      })
      .catch(error => {
        notify(error.message, 'warning');
      });
  };
  const typeExrcise = () => {
    return (
            <Card>
      <Title title="Notificações" />
      <CardContent>
        <Grid
          container
          direction="row"
          justify="flex-end"
          alignItems="flex-end"
        >
          <Button
              variant="outlined"
              color="primary"
              style={{ marginRight: 4 }}
              onClick={() => setRenderTypeExrcise(!renderTypeExrcise)}
            >
              Notificação para um Usuário
            </Button>
         
         
        </Grid>

        <Form
          initialValues={defaultNotification}
          onSubmit={handleSubmit}
          subscription={defaultSubscription} // don't redraw entire form each time one field changes
          key="notificationssend" // support for refresh button
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
                  {insertMode === 'one' && (
                    <Grid item xs={6}>
                      <ReferenceInput
                        label="Tipo de treino"
                        source="typeExerciseId"
                        reference="type-training"
                        validate={required()}
                      >
                        <AutocompleteInput optionText="type" />
                      </ReferenceInput>
                    </Grid>
                  )}
                  <Grid item xs={12}>
                    <TextInput
                      label="Título"
                      validate={required()}
                      source="title"
                      fullWidth
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextInput
                      label="Mensagem"
                      validate={required()}
                      source="content"
                      multiline
                    />
                  </Grid>
                </Grid>
                <Grid container style={{ marginTop: 10 }}>
                  <Grid item xs={12} style={{ backgroundColor: 'none' }}>
                    <Box
                      display="flex"
                      justifyContent="space-between"
                      width="100%"
                    >
                      <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        endIcon={<SendIcon />}
                      >
                        Enviar Notificação
                      </Button>
                    </Box>
                  </Grid>
                </Grid>
              </form>
            );
          }}
        />
      </CardContent>
    </Card>
    )
  }

  return (
    renderTypeExrcise?typeExrcise():
    <Card>
      <Title title="Notificações" />
      <CardContent>
        <Grid
          container
          direction="row"
          justify="flex-end"
          alignItems="flex-end"
        >
          {insertMode !== 'one' && (
            <Button
              variant="outlined"
              color="primary"
              style={{ marginRight: 4 }}
              onClick={() => setInsertMode('one')}
            >
              Notificação para um Usuário
            </Button>
          )}
          {insertMode !== 'broadcast' && (
            <>
              <Button
                style={{marginRight:10}}
                variant="contained"
                color="secondary"
                onClick={() => setInsertMode('broadcast')}
              >
                Notificação para todos os Usuários
              </Button>
              <Button
              variant="contained"
              color="secondary"
              onClick={() => setRenderTypeExrcise(!renderTypeExrcise)}
            >
              Escolher tipo de treino.
            </Button>
          </>  
          )}
        </Grid>

        <Form
          initialValues={defaultNotification}
          onSubmit={handleSubmit}
          subscription={defaultSubscription} // don't redraw entire form each time one field changes
          key="notificationssend" // support for refresh button
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
                  {insertMode === 'one' && (
                    <Grid item xs={6}>
                      <ReferenceInput
                        label="Usuário"
                        source="userId"
                        reference="users"
                        validate={required()}
                      >
                        <AutocompleteInput optionText="name" />
                      </ReferenceInput>
                    </Grid>
                  )}
                  <Grid item xs={12}>
                    <TextInput
                      label="Título"
                      validate={required()}
                      source="title"
                      fullWidth
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextInput
                      label="Mensagem"
                      validate={required()}
                      source="content"
                      multiline
                    />
                  </Grid>
                </Grid>
                <Grid container style={{ marginTop: 10 }}>
                  <Grid item xs={12} style={{ backgroundColor: 'none' }}>
                    <Box
                      display="flex"
                      justifyContent="space-between"
                      width="100%"
                    >
                      <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        endIcon={<SendIcon />}
                      >
                        Enviar Notificação
                      </Button>
                    </Box>
                  </Grid>
                </Grid>
              </form>
            );
          }}
        />
      </CardContent>
    </Card>
  );
};

const defaultSubscription = {
  submitting: true,
  pristine: true,
  valid: true,
  invalid: true,
};

export default {
  list: Notifications,
  icon: NotificationsIcon,
  options: { label: 'Notificações' },
};
