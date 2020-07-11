import React, {useState, useEffect } from 'react';
import axios from 'axios';
import URL from '../providers/URLs.js';
import { format, parseISO } from 'date-fns';
import { Typography } from '@material-ui/core';
import {  
    Grid,  
    Toolbar,
    Select, 
    CardContent,
    Button,
    FormControl,
    InputLabel,
    MenuItem,
    FormControlLabel,
    Radio,
    RadioGroup,
} from '@material-ui/core';
import PlaylistAddIcon from '@material-ui/icons/PlaylistAdd';
import SaveIcon from '@material-ui/icons/Save';
import {
useNotify,
Loading,
} from 'react-admin';

export default function CreateShenduling(){
    const [users, setUsers] = useState([{}]);
    const [idUser, setIdUser] = useState();
    const [date, setDate] = useState("");
    const [dateSchenduling, setDateSchenduling] = useState([{}]);
    const [times, setTimes] = useState([]);
    const [time, setTime] = useState("");
    const [loading, setLoading] = useState(true);
    const [showSave, setShowSave] = useState(false);
    const [typesExercise, setTypesExercise] = useState([]);
    const [idTypeExercise, setIdTypeExercise] = useState("");
    const notify = useNotify();
   
    useEffect(()=>{
      axios.get(`${URL.baseURL}/api/users`,{
        headers: {
          'Accept': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      }).then(res => {
        setUsers(res.data.content);
        setLoading(false)
      })
    },[])
    
    const onChangeUser = ( event ) => {
      const target = event.target;
      setTypesExercise(target.value.listTypeExercise);
      setIdUser(target.value.id)
    }
  
    const onChangeTypeExercise = ( event ) => {
      const target = event.target;
      setIdTypeExercise(target.value);
    }

    const onChangeDate = ( event ) => {
        const target = event.target;
        setDate(target.value.date);
        setTimes(target.value.times);
    }

    const onChangeTime = ( event ) => {
      const target = event.target;
      setTime(target.value);
    }
  
    const onSubmitSchenduling = (event) => {
      
      event.preventDefault();
      if(idTypeExercise !== ""){
        setShowSave(true)
        setLoading(true)
        axios.get(`${URL.baseURL}/api/agenda-configs/availableTimes/${idTypeExercise.id}`,{
          headers: {
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
         
        }).then(res => {
          setDateSchenduling(res.data)
          setLoading(false)
        }).catch(error => {
          notify("Não existe horários para este dia com o tipo de treino que o usuário têm.", 'warning');
        })
      }else{
        notify("verifique os campos e tente novamente.", 'warning');
      }
    }

    const onSubmit = (event) => {
        event.preventDefault();
        if(date === "" || time === ""){
          notify("verifique os campos e tente novamente.", 'warning');
        }else{
          let body = {
            date: date,
            idTypeExercise: idTypeExercise.id,
            status: "MARCADO",
            time: time,
            userId: idUser
          }
          axios.post(`${URL.baseURL}/api/schedulings`, body, {
            headers: {
              'Accept': 'application/json',
              'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
          }).then(res => {
            notify("Novo agendamento criado.");
            setShowSave(false)
          }).catch(error => {
            notify("Não existe horários para este dia com o tipo de treino que o usuário têm.", 'warning');
          })
        }
        
    }
  
    const getScheduling = () => {
      return(
        <>
          <form onSubmit={onSubmitSchenduling}>
            <Grid container spacing={2}>
              <Grid item xs={3}>
                <FormControl>
                  <InputLabel htmlFor="demo-customized-textbox">Usuário</InputLabel>
                  <Select
                    label="Usuário"
                    labelId="demo-simple-select-placeholder-label-label"
                    id="demo-simple-select-placeholder-label"
                    value={idUser}
                    onChange={onChangeUser}
                    displayEmpty
                  >
                    {users.map(user => {
                        return<MenuItem key={user.id} value={user}>{user.name}</MenuItem>  
                      })}
                  </Select>
                </FormControl>
                <FormControl style={{marginBottom: 10}}>
                  <InputLabel htmlFor="demo-customized-textbox">Tipos de treino</InputLabel>
                  <Select
                    label="Tipos de treino"
                    labelId="demo-simple-select-placeholder-label-label"
                    id="demo-simple-select-placeholder-label"
                    value={idTypeExercise}
                    onChange={onChangeTypeExercise}
                    displayEmpty
                  >
                    {typesExercise.map(typeExercise => {
                        return<MenuItem key={typeExercise.id} value={typeExercise}>{typeExercise.type}</MenuItem>  
                      })}
                  </Select>
                </FormControl>
                  <Button
                    variant="contained"
                    startIcon={<PlaylistAddIcon />}
                    style={{backgroundColor: "black", color:"white", marginBottom: 10}}
                    type="submit"
                  >
                    Buscar horários
                  </Button>
              </Grid>
            </Grid>
          </form>
        </>
      )
    }
  
    const formSchenduling = () => {
      if (loading) return <Loading />;
      return (
        <>
          <form onSubmit={onSubmit}>
              <FormControl style={{display:"flex", maxWidth:240}}>
                  <InputLabel htmlFor="demo-customized-textbox">Data</InputLabel>
                  <Select
                    label="Data"
                    labelId="demo-simple-select-placeholder-label-label"
                    id="demo-simple-select-placeholder-label"
                    value={date}
                    onChange={onChangeDate}
                    displayEmpty
                    inputProps={{
                      id: 'age-native-required',
                    }}
                  >
                  {dateSchenduling.map(date => (
                      !date.full & date.hasTrainingAvailable?
                      <MenuItem key={date.date} value={date}><Typography>{format(parseISO(date.date), 'dd/MM/yyyy')}</Typography></MenuItem>:
                      <MenuItem disabled value="disabled"><Typography>{format(parseISO(date.date), 'dd/MM/yyyy')}</Typography></MenuItem>
                    ))}
                  </Select>
              </FormControl>
              <FormControl component="fieldset">
                <RadioGroup style={{flexDirection:"row"}} aria-label="gender" name="gender1" onChange={onChangeTime} inputProps={{
                      id: 'age-native-required',
                    }}>
                  {times.map(time => (
                    !time.full & time.hasTrainingAvailable?
                    <FormControlLabel 
                      key={time.time} 
                      value={time.time} 
                      control={<Radio />} 
                      label={time.time} />:
                    <FormControlLabel
                      value="disabled"
                      disabled
                      control={<Radio />}
                      label={time.time}
                    />
                    
                  ))}
                </RadioGroup>
              </FormControl>
              <Toolbar item xs={3}>
                <Button
                  variant="contained"
                  startIcon={<SaveIcon />}
                  style={{backgroundColor: "black", color:"white"}}
                  type="submit"
                >
                  Salvar
                </Button>
              </Toolbar>
          </form>
        </>
      )
    }
    return (   
        <>
          <CardContent style={{ textAlign: 'center' }}>
            {getScheduling()}
            {showSave && formSchenduling()}
          </CardContent>
        </>
    )
}