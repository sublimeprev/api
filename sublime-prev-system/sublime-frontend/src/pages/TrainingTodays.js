import React, { useEffect, useState } from 'react';
import CardContent from '@material-ui/core/CardContent';
import { withStyles, makeStyles } from "@material-ui/core/styles";
import {
  ButtonGroup,
  Button,
  Table,
  TableBody, 
  TableCell, 
  TableContainer, 
  TableHead, 
  TableRow, 
} from "@material-ui/core";
import axios from 'axios';
import URL from '../providers/URLs.js';
import { format } from 'date-fns';
import { pt } from 'date-fns/locale';
import {
  Loading,
  } from 'react-admin';

const StyledTableCell = withStyles(theme => ({
  head: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white
  },
  body: {
    fontSize: 15
  }
}))(TableCell);

const StyledTableRow = withStyles(theme => ({
  root: {
    "&:nth-of-type(odd)": {
      backgroundColor: theme.palette.background.default
    }
  }
}))(TableRow);

const useStyles = makeStyles({
  table: {
    minWidth: 70,
  },
  tablec: {
    display:'flex',
    alignItems: 'baseline'
  }
});

export default function CustomizedTables() {
  const [loading, setLoading] = useState();
  const [date, setDate] = useState(new Date());
  const classes = useStyles();
  const defaultDataTrainingToday = [];
  const [dayOffWeekCurrent, setDayOffWeekCurrent] = useState(new Date().getDay());
  const day = format(date, " - iiii", { locale: pt })
  const formattedDate = format(
    date, 
    "'Dia' dd 'de' MMMM' de ' yyyy'" ,
    { locale: pt } 
  );
  const [dataTrainingToday, setDataTrainingToday] = useState(defaultDataTrainingToday);
  
  const calculateDays = (day) => {
    let dateCurrent = date;
    if (day < dayOffWeekCurrent){
      let restNegative = dayOffWeekCurrent - day;
      dateCurrent.setDate(dateCurrent.getDate() - restNegative)
    }else if(day > dayOffWeekCurrent) {
      let restPositive = day - dayOffWeekCurrent;
      dateCurrent.setDate(dateCurrent.getDate() + restPositive)
    }else{
      setDayOffWeekCurrent(day);
      return null;
    }
    setDate(dateCurrent)
    setDayOffWeekCurrent(day);
    getScheduling()
  }

  const changeColorButton= (id) => {
    let button = document.getElementById(`${id}`).style.backgroundColor = "#BDBDBD";
  }

  const getScheduling = () => {
    setLoading(true)
    axios.get(`${URL.baseURL}/api/schedulings/training-dashboard/${date}`,{
          headers: {
            'Accept': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        }).then(res => {
          
          setDataTrainingToday(res.data);
          setLoading(false);
          changeColorButton(date.getDay());
        })
  }

  useEffect(()=>{
    getScheduling()
  },[])

  function tablesMorning() {
  const morning = [];
  dataTrainingToday.forEach(field => {
    if(field.morning){
      morning.push(field)
    }
  })
  return (
    morning.length === 0?<>
    <CardContent style={{ textAlign: 'center' }}>
      Você não possui alunos para a manhã.
    </CardContent></>:morning.map(scheduling => (<>
      <Table key={scheduling.id+scheduling.hour} className={classes.table} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>{scheduling.hour.slice(0,-3)}  -  {scheduling.typeExercise}</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {scheduling.userTypeExercise.map(row => (
            <StyledTableRow key={row}>
              <StyledTableCell component="th" scope="row">
                {row.name}
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </>))
    );
  }

  function tablesAfternoon() {
    const afternoon = [];
    dataTrainingToday.forEach(field => {
      if(!field.morning){
        afternoon.push(field)
      }
    })
    return (
      afternoon.length === 0?<>
      <CardContent style={{ textAlign: 'center' }}>
        Você não possui alunos para a tarde.
      </CardContent></>:afternoon.map(scheduling => (<>
        <Table key={scheduling.id+scheduling.hour} className={classes.table} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell>{scheduling.hour.slice(0,-3)}  -  {scheduling.typeExercise}</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {scheduling.userTypeExercise.map(row => (
              <StyledTableRow key={row}>
                <StyledTableCell component="th" scope="row">
                  {row.name}
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </>))
    );
  }
  if (loading) return <Loading />;
  return (
    <>
      <CardContent style={{ display: 'flex',justifyContent: 'space-between', alignItems: 'center'}}>
        <label>{formattedDate}{day}</label>
        <ButtonGroup  variant="contained">
          <Button id="1" onClick={calculateDays.bind(this,1)}>Seg</Button>
          <Button id="2" onClick={calculateDays.bind(this,2)}>Ter</Button>
          <Button id="3" onClick={calculateDays.bind(this,3)}>Qua</Button>
          <Button id="4" onClick={calculateDays.bind(this,4)}>Qui</Button>
          <Button id="5" onClick={calculateDays.bind(this,5)}>Sex</Button>
        </ButtonGroup>
      </CardContent>
      {
        loading?(<Loading />):(
          <>
            <CardContent style={{ textAlign: 'center' }}>
              <label>Manhã</label>
            </CardContent>
            <TableContainer className={classes.tablec}>   
              {tablesMorning()}
            </TableContainer>
            <CardContent style={{ textAlign: 'center' }}>
              <label>Tarde</label>
            </CardContent>
            <TableContainer className={classes.tablec}>   
              {tablesAfternoon()}
            </TableContainer>
          </>
        )
      }
      
    </>
  ); 
}
