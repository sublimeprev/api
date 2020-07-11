import { 
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Table,
  Card,
  CardContent,
  Button,
  TextField,
  Grid,
  Toolbar,
} from '@material-ui/core';
import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
import DeleteIcon from '@material-ui/icons/Delete';
import AddIcon from '@material-ui/icons/Add';
import KeyboardBackspaceIcon from '@material-ui/icons/KeyboardBackspace';
import SaveIcon from '@material-ui/icons/Save';
import React, {useEffect, useState} from 'react';
import {
  Title,
  useNotify,
} from 'react-admin';
import axios from 'axios';
import URL from './../providers/URLs';

const TypeExerciseList = () => {

  const [data, setData] = useState([]);
  const notify = useNotify();
  const [create, setCreate] = useState(false);
  const [type, setType] = useState("");

  function getList(){
    axios.get(`${URL.baseURL}/api/type-training/all`,{
      headers: {
        'Accept': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    }).then(res => {
      setData(res.data);
    })
  }

  useEffect(()=>{
    getList()
  },[])

  function deleteTypeExercise(id){ 
    axios.delete(`${URL.baseURL}/api/type-training/${id}`,{
      headers: {
        'Accept': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    }).then(() => {
      notify("Tipo de Treino deletado realizado")
      getList()
    }).catch(()=>{
      notify("Não foi possivel deletar.")
    }) 
  }

  function renderCreate(){
    setCreate(!create);
  }

  const typeExerciseCreate = () => {
    
    const handleChange = ( event ) => {
      const target = event.target;
      setType(target.value)
    }

    const onSubmit = ( event ) => {
      event.preventDefault();

      let body = {
        type:type
      }
      
      axios.post(`${URL.baseURL}/api/type-training`, body, {
        headers: {
          'Accept': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      }).then(res => {
        notify("Novo tipo de treino criado.")    
        getList()
        renderCreate()
      }).catch(() => {
        notify("Não foi possível criar um novo tipo de treino.")
      })
      setType("")
    }

    return(
      <>
        <CardContent style={{ textAlign: 'right' }}>
          <Button
              variant="contained"
              color="secondary"
              style={{backgroundColor: "transparent"}}
              startIcon={<KeyboardBackspaceIcon />}
              onClick={renderCreate}
            >
              VOLTAR
          </Button>
        </CardContent>
        <CardContent style={{ textAlign: 'center' }}>
            <form onSubmit={onSubmit}>
              <Grid container spacing={2}>
                <Grid item xs={3}>
                  <TextField id="standard-basic" label="Novo tipo de treino" type="text" name="type" value={type} onChange={handleChange}/>
                </Grid>
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
              </Grid>
            </form>
        </CardContent>
      </>
    )
  };

  const typeExerciseList = () => {
    return (
      <>
        <CardContent style={{ textAlign: 'right' }}>
          <Button
              variant="contained"
              color="secondary"
              style={{backgroundColor: "transparent"}}
              startIcon={<AddIcon />}
              onClick={renderCreate}
            >
              NOVO
          </Button>
        </CardContent>
        <CardContent style={{ textAlign: 'center' }}>
          <Table> 
          <TableHead>
            <TableRow>
              <TableCell>
                <strong>Tipos de Treino</strong>    
              </TableCell>
            </TableRow>
          </TableHead> 
          {data.map(row => {
            return(
              <TableBody key={row.id}>
                <TableRow>
                  <TableCell>
                    {row.type}
                  </TableCell>
                  <TableCell>
                    <Button
                      variant="contained"
                      style={{backgroundColor: "transparent",color:"red"}}
                      startIcon={<DeleteIcon />}
                      onClick={deleteTypeExercise.bind(this, row.id)}
                    >
                      Delete
                    </Button>
                  </TableCell>
                </TableRow>
              </TableBody>
              )
            })}
          </Table>  
        </CardContent>
      </>
    )
  }
  
  return (
    <Card>
      <Title title="Tipo de Treinos" />
      {create?typeExerciseCreate():typeExerciseList()}
    </Card>  
    
  )
};

export default {
  list: TypeExerciseList,
  icon: AssignmentIndIcon,
};
