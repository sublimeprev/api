import React, {useState, useEffect} from 'react';
import axios from 'axios';
import URL from '../providers/URLs.js';
import {
    Loading,
    useNotify,
  } from 'react-admin';
import { makeStyles } from '@material-ui/core/styles';
import SaveIcon from '@material-ui/icons/Save';
import {
    CardContent, 
    TextField,
    Toolbar,
    Button,
    InputLabel,
    Select,
    MenuItem,
    FormControl
} from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  root: {
    '& > *': {
      margin: theme.spacing(1),
      width: '25ch',
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        width: 200,
      },
  },
}));

export default function ProcessMother({...props}){
    const classes = useStyles();
    const [loading, setLoading] = useState(false);
    const [hasprocess, setHasProcess] = useState(true);
    const [buttonDisabled, setButtonDisabled] = useState(true)
    const [process, setProcess] = useState({
        status: "",
        requirements: "",
        observation: "",
        idMother: props.id
    })
    const notify = useNotify();
    const idMother = props.id;

    const handleChange = (evt) => {
        const value = evt.target.value;
        console.log(value)
        console.log(evt.target.name)
        setProcess({
            ...process,
            [evt.target.name]: value
        });

    }
    
    const verifyProcess = () => {
        setLoading(true)
        axios.get(`${URL.baseURL}/api/process-mothers/by-mother/${idMother}`,{
              headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
              }
            }).then(res => {
                setProcess(res.data);
                console.log(res.data)
              setLoading(false);
            }).catch(error => {
                setHasProcess(false)
                setLoading(false)
            });
        }

    const verfifyFildsCreateProcess = () => {
        if(process.status === ""){
            setButtonDisabled(true)
        }else {
            setButtonDisabled(false)
        }
    }
    const onSubmitCreateProcess = () => {
        console.log("onsubmit")
        console.log(process)
        setLoading(true)
        axios.post(`${URL.baseURL}/api/process-mothers`, process,{
                headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(res => {
                setHasProcess(res.data);
                console.log(res.data)
                notify("Processo cadastrado com sucesso", 'sucess');
                setLoading(false);
            }).catch(error => {
                notify(error.message, 'warning');
                setLoading(false);
            });
    }

    const onSubmitUpdateProcess = () => {
        console.log("onsubmit")
        console.log(process)
        setLoading(true)
        axios.put(`${URL.baseURL}/api/process-mothers`, process,{
                headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(res => {
                setHasProcess(res.data);
                console.log(res.data)
                notify("Dados editado", 'sucess');
                setLoading(false);
            }).catch(error => {
                notify(error.message, 'warning');
                setLoading(false);
            });
    }


    useEffect(()=>{
        verfifyFildsCreateProcess()
    },[process])
    
    useEffect(()=>{
        verifyProcess()
    },[])

    const createProcess = () => {
        return (
            <>
                <CardContent style={{ textAlign: 'center' }} >
                    <form className={classes.root} noValidate autoComplete="off">
                        <FormControl style={{marginTop: 16}}>
                            <InputLabel id="demo-simple-select-label">Status</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                name="status"
                                value={process.status}
                                onChange={handleChange}
                            >
                            <MenuItem value={'ANALISE'}>Ánalise</MenuItem>
                            <MenuItem value={'CONCEDIDO'}>Concedido</MenuItem>
                            <MenuItem value={'INDEFERIDO'}>Indeferido</MenuItem>
                            <MenuItem value={'EXIGENCIA'}>Exigência</MenuItem>
                            </Select>
                        </FormControl>
                        <TextField multiline rowsMax={4} name="requirements" label="Exigências" variant="filled" value={process.requirements} onChange={handleChange}/>
                        <TextField multiline rowsMax={4} name="observation" label="Obsercações" variant="filled" value={process.observation} onChange={handleChange}/>
                        <Toolbar>
                            <Button disabled={buttonDisabled} variant="contained" color="primary" onClick={onSubmitCreateProcess}>
                                <SaveIcon/>SALVAR
                            </Button>
                        </Toolbar>
                    </form>
                </CardContent>
            </>
        )
    }

    const editProcess = () => {
        return (
            <>
                <CardContent style={{ textAlign: 'center' }} >
                    <form className={classes.root} noValidate autoComplete="off">
                    <TextField disabled name="id" label="Id" variant="filled" value={process.id}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField disabled name="createdBy" label="Criado por" variant="filled" value={process.createdBy}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField disabled name="updatedBy" label="Atualizado por" variant="filled" value={process.updatedBy}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField disabled type="datetime" name="createdAt" label="Criado em" variant="filled" value={process.createdAt}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField disabled type="datetime" name="updatedAt" label="Atualizado em" variant="filled" value={process.updatedAt}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <FormControl style={{marginTop: 16}}>
                            <InputLabel id="demo-simple-select-label">Status</InputLabel>
                            <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            name="status"
                            value={process.status}
                            onChange={handleChange}
                            >
                            <MenuItem value={'ANALISE'}>Ánalise</MenuItem>
                            <MenuItem value={'CONCEDIDO'}>Concedido</MenuItem>
                            <MenuItem value={'INDEFERIDO'}>Indeferido</MenuItem>
                            <MenuItem value={'EXIGENCIA'}>Exigência</MenuItem>
                            </Select>
                        </FormControl>
                        <TextField multiline rowsMax={4} name="requirements" label="Exigências" variant="filled" value={process.requirements} onChange={handleChange}/>
                        <TextField multiline rowsMax={4} name="observation" label="Obsercações" variant="filled" value={process.observation} onChange={handleChange}/>
                        <Toolbar>
                            <Button disabled={buttonDisabled} variant="contained" color="primary" onClick={onSubmitUpdateProcess}>
                                <SaveIcon/>EDIT
                            </Button>
                        </Toolbar>
                    </form>
                </CardContent>
            </>
        )
    }

    return(
        <>
            {loading?<Loading/>:hasprocess?editProcess():createProcess()}
            
        </>
    )
}