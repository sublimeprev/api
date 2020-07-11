import React, { useEffect, useState } from 'react';
import { withDataProvider, useNotify, Loading, translate } from 'react-admin';
import { makeStyles } from '@material-ui/core/styles';
import {
  Button,
  Grid,
  ListItem,
  ListItemIcon,
  Checkbox,
  ListItemText,
  CardContent,
} from '@material-ui/core';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    textAlign: 'center',
    justifyContent: 'center',
  },
  paper: {
    height: 140,
    width: 100,
  },
  control: {
    padding: theme.spacing(2),
  },
}));

const DayOfWeek = translate(({ translate, item }) => {
  return <>{translate(`enums.dayOfWeek.${item}`)}</>;
});

function UserAgendaConfig({ resource, record, dataProvider }) {
  const controllerEndpoint = 'user-agenda-configs/users';
  const notify = useNotify();
  const [loading, setLoading] = useState(false);
  const [configsByUser, setConfigsByUser] = useState({});
  const [configsByUserAgenda, setConfigsByUserAgenda] = useState([]);
  const [idTypeExercise, setIdTypeExercise] = useState("");
  const classes = useStyles();

  useEffect(() => {
    fetchData();
    // eslint-disable-next-line
  }, [record]);

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await dataProvider(
        'GET',
        `${controllerEndpoint}/${record.id}`
      );
      setConfigsByUserAgenda(response.data || []);
    } catch (error) {
      notify(error.message, 'warning');
    }
    setLoading(false);
  };

  const typeExerciseId = (item) => { 
      item.typeExerciseId = item.listTypeExercise[0].id
  }

  const save = () => {
    configsByUserAgenda.forEach(item => {
      saveChanges(item)
    })
  }

  const saveChanges = async (item) => {
    setLoading(true);
    
    try {
      await dataProvider(
        'PUT',
        `${controllerEndpoint}/${record.id}`,
        item.configs
      );
    } catch (error) {
      notify(error.message, 'warning');
    }
    setLoading(false);
  };

  const handleCheck = (key, id, configsNow) => {
    const configs = {
      ...configsNow.configs,
      [key]: configsNow.configs[key].map(item =>
        item.agendaConfigId === id ? { ...item, checked: !item.checked } : item
      ),
    };
    configsNow.configs = configs;

    const newConfigsByUserAgenda = [];
    
    newConfigsByUserAgenda.unshift(configsNow)  

    configsByUserAgenda.forEach(item => {
      if (configsNow.typeExercise !== item.typeExercise){
        newConfigsByUserAgenda.unshift(item)
        newConfigsByUserAgenda.reverse();
      } 
      if (configsNow.typeExercise === item.typeExercise){
        newConfigsByUserAgenda.reverse();
      }
    })
    
    setConfigsByUserAgenda(newConfigsByUserAgenda);
  };
  return loading ? (
      <Loading
        loadingPrimary="messages.loading"
        loadingSecondary="messages.wait"
      />
    ) :(
    <><Grid container className={classes.root} spacing={2}>
      {configsByUserAgenda.map(itemObject => {
        return(
          <>
            <CardContent style={{ textAlign: 'center' }}>
              <label><strong>{itemObject.typeExercise}</strong></label>
            </CardContent>
            <Grid container className={classes.root} spacing={2}>
              {Object.keys(itemObject.configs).map(key => {
                return(
                  <Grid item xs={2} key={key}>
                    <DayOfWeek item={key} />
                    {itemObject.configs[key].map(item => {
                      return item.hasTrainingAvailable?(
                      <ListItem
                        key={item.agendaConfigId}
                        button
                        onClick={() => handleCheck(key, item.agendaConfigId, itemObject)}
                        style={{ paddingTop: 0, paddingBottom: 0 }}
                      >
                        <ListItemIcon>
                          <Checkbox
                            checked={item.checked}
                            value="primary"
                            inputProps={{ 'aria-label': 'primary checkbox' }}
                            size="small"
                          />
                            {typeExerciseId(item)}
                        </ListItemIcon>
                        <ListItemText primary={item.time} />
                      </ListItem>):(
                        <ListItem
                          disabled
                          key={item.agendaConfigId}
                          button
                          onClick={() => handleCheck(key, item.agendaConfigId, itemObject)}
                          style={{ paddingTop: 0, paddingBottom: 0 }}
                        >
                          <ListItemIcon>
                            <Checkbox
                              checked={item.checked}
                              value="primary"
                              inputProps={{ 'aria-label': 'primary checkbox' }}
                              size="small"
                            />
                              {typeExerciseId(item)}
                          </ListItemIcon>
                          <ListItemText primary={item.time} />
                        </ListItem>)
                    })}
                  </Grid>
                )
              })}
            </Grid>
          </>
        )
      })}
        <Grid item xs={12} style={{ textAlign: 'end', paddingRight: 20 }}>
          <Button
            onClick={() => save()}
            color="primary"
            variant="contained"
          >
            Salvar
          </Button>
        </Grid>
      </Grid>
    </>)
}

export default withDataProvider(UserAgendaConfig);