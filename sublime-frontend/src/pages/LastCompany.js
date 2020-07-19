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

export default function LastCompanyMother({...props}){
    const classes = useStyles();
    const [loading, setLoading] = useState(false);
    const [haslastCompany, setHasLastCompany] = useState(true);
    const [buttonDisabled, setButtonDisabled] = useState(true)
    const [lastCompany, setLastCompany] = useState({
        name: "",
        unemploymentInsurance: "",
        reasonForDismissal: "",
        dismissalDate: "",
        admissionDate: "",
        idMother: props.id
    })
    const notify = useNotify();
    const idMother = props.id;

    const handleChange = (evt) => {
        const value = evt.target.value;
        console.log(value)
        console.log(evt.target.name)
        setLastCompany({
            ...lastCompany,
            [evt.target.name]: value
        });

    }
    
    const verifyLastCompany = () => {
        setLoading(true)
        axios.get(`${URL.baseURL}/api/last-company/by-mother/${idMother}`,{
              headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
              }
            }).then(res => {
                setLastCompany(res.data);
                console.log(res.data)
              setLoading(false);
            }).catch(error => {
                setHasLastCompany(false)
                setLoading(false)
            });
        }

    const verfifyFildsCreateLastCompany = () => {
        if(lastCompany.name === "" || lastCompany.reasonForDismissal === "" || lastCompany.unemploymentInsurance === ""){
            setButtonDisabled(true)
        }else {
            setButtonDisabled(false)
        }
    }
    const onSubmitCreateLastCompany = () => {
        console.log("onsubmit")
        console.log(lastCompany)
        setLoading(true)
        axios.post(`${URL.baseURL}/api/last-company`, lastCompany,{
                headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(res => {
                setHasLastCompany(res.data);
                console.log(res.data)
                notify("lastCompany cadastrado com sucesso", 'sucess');
                setLoading(false);
            }).catch(error => {
                notify(error.message, 'warning');
                setLoading(false);
            });
    }

    const onSubmitUpdateLastCompany = () => {
        console.log("onsubmit")
        console.log(lastCompany)
        setLoading(true)
        axios.put(`${URL.baseURL}/api/last-company`, lastCompany,{
                headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(res => {
                setHasLastCompany(res.data);
                console.log(res.data)
                notify("Dados editado", 'sucess');
                setLoading(false);
            }).catch(error => {
                notify(error.message, 'warning');
                setLoading(false);
            });
    }


    useEffect(()=>{
        verfifyFildsCreateLastCompany()
    },[lastCompany])
    
    useEffect(()=>{
        verifyLastCompany()
    },[])

    const createLastCompany = () => {
        return (
            <>
                <CardContent style={{ textAlign: 'center' }} >
                    <form className={classes.root} noValidate autoComplete="off">
                        <TextField name="name" label="Nome da empresa" variant="filled" value={lastCompany.name} onChange={handleChange}/>
                        <FormControl style={{marginTop: 16}}>
                            <InputLabel id="demo-simple-select-label">Motivo da demissão</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                name="reasonForDismissal"
                                value={lastCompany.reasonForDismissal}
                                onChange={handleChange}
                            >
                            <MenuItem value={'DEMISSAO_SEM_JUSTA_CAUSA'}>Demissão sem justa causa</MenuItem>
                            <MenuItem value={'DEMISSAO_COM_JUSTA_CAUSA'}>Demissão com justa causa</MenuItem>
                            <MenuItem value={'PEDIDO_DEMISSAO'}>Pedido de demissão</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{marginTop: 16}}>
                            <InputLabel id="demo-simple-select-label">Pegou seguro desemprego</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                name="unemploymentInsurance"
                                value={lastCompany.unemploymentInsurance}
                                onChange={handleChange}
                            >
                            <MenuItem value={true}>Sim</MenuItem>
                            <MenuItem value={false}>Não</MenuItem>
                            </Select>
                        </FormControl>
                        <TextField name="admissionDate" type="date" label="Data de admissão" variant="filled" value={lastCompany.admissionDate} onChange={handleChange}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField name="dismissalDate" type="date" label="Data de demissão" variant="filled" value={lastCompany.dismissalDate} onChange={handleChange}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <Toolbar>
                            <Button disabled={buttonDisabled} variant="contained" color="primary" onClick={onSubmitCreateLastCompany}>
                                <SaveIcon/>SALVAR
                            </Button>
                        </Toolbar>
                    </form>
                </CardContent>
            </>
        )
    }

    const editLastCompany = () => {
        return (
            <>
                <CardContent style={{ textAlign: 'center' }} >
                    <form className={classes.root} noValidate autoComplete="off">
                        <TextField disabled name="id" label="Id" variant="filled" value={lastCompany.id}
                            InputLabelProps={{
                                shrink: true,
                                }}/>
                        <TextField disabled name="createdBy" label="Criado por" variant="filled" value={lastCompany.createdBy}
                            InputLabelProps={{
                                shrink: true,
                                }}/>
                        <TextField disabled name="updatedBy" label="Atualizado por" variant="filled" value={lastCompany.updatedBy}
                            InputLabelProps={{
                                shrink: true,
                                }}/>
                        <TextField disabled type="datetime" name="createdAt" label="Criado em" variant="filled" value={lastCompany.createdAt}
                            InputLabelProps={{
                                shrink: true,
                                }}/>
                        <TextField disabled type="datetime" name="updatedAt" label="Atualizado em" variant="filled" value={lastCompany.updatedAt}
                            InputLabelProps={{
                                shrink: true,
                                }}/>
                        <TextField name="name" label="Nome da empresa" variant="filled" value={lastCompany.name} onChange={handleChange}/>
                        <FormControl style={{marginTop: 16}}>
                            <InputLabel id="demo-simple-select-label">Motivo da demissão</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                name="reasonForDismissal"
                                value={lastCompany.reasonForDismissal}
                                onChange={handleChange}
                            >
                                <MenuItem value={'DEMISSAO_SEM_JUSTA_CAUSA'}>Demissão sem justa causa</MenuItem>
                                <MenuItem value={'DEMISSAO_COM_JUSTA_CAUSA'}>Demissão com justa causa</MenuItem>
                                <MenuItem value={'PEDIDO_DEMISSAO'}>Pedido de demissão</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl style={{marginTop: 16}}>
                            <InputLabel id="demo-simple-select-label">Pegou seguro desemprego</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                name="unemploymentInsurance"
                                value={lastCompany.unemploymentInsurance}
                                onChange={handleChange}
                            >
                                <MenuItem value={true}>Sim</MenuItem>
                                <MenuItem value={false}>Não</MenuItem>
                            </Select>
                        </FormControl>
                        <TextField name="admissionDate" type="date" label="Data de admissão" variant="filled" value={lastCompany.admissionDate} onChange={handleChange}
                            InputLabelProps={{
                                shrink: true,
                                }}/>
                        <TextField name="dismissalDate" type="date" label="Data de demissão" variant="filled" value={lastCompany.dismissalDate} onChange={handleChange}
                            InputLabelProps={{
                                shrink: true,
                                }}/><Toolbar>
                        <Button disabled={buttonDisabled} variant="contained" color="primary" onClick={onSubmitUpdateLastCompany}>
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
            {loading?<Loading/>:haslastCompany?editLastCompany():createLastCompany()}
            
        </>
    )
}