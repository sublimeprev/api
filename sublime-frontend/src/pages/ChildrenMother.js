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
    Box} from '@material-ui/core';

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

export default function ChildrenMother({...props}){
    const classes = useStyles();
    const [loading, setLoading] = useState(false);
    const [haschildren, setHaschildren] = useState(true);
    const [buttonDisabled, setButtonDisabled] = useState(true)
    const [children, setchildren] = useState({
        name: "",
        registration: "",
        birthday: "",
        birthdayCertificateDate: "",
        idMother: props.id
    })
    const notify = useNotify();
    const idMother = props.id;

    const handleChange = (evt) => {
        const value = evt.target.value;
        console.log(value)
        setchildren({
            ...children,
            [evt.target.name]: value
        });

    }
    
    const verifychildren = () => {
        setLoading(true)
        axios.get(`${URL.baseURL}/api/children/by-mother/${idMother}`,{
              headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
              }
            }).then(res => {
                setchildren(res.data);
                console.log(res.data)
              setLoading(false);
            }).catch(error => {
                setHaschildren(false)
                setLoading(false)
            });
        }

    const verfifyFildsCreatechildren = () => {
        if(children.name === "" || children.registration === "" || children.birthday === ""|| children.birthCertificateDate === ""){
            setButtonDisabled(true)
        }else {
            setButtonDisabled(false)
        }
    }
    const onSubmitCreateChildren = () => {
        console.log("onsubmit")
        console.log(children)
        setLoading(true)
        axios.post(`${URL.baseURL}/api/children`, children,{
                headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(res => {
                setHaschildren(res.data);
                console.log(res.data)
                notify("Filho cadastrado com sucesso", 'sucess');
                setLoading(false);
            }).catch(error => {
                notify(error.message, 'warning');
                setLoading(false);
            });
    }

    const onSubmitUpdateChildren = () => {
        console.log("onsubmit")
        console.log(children)
        setLoading(true)
        axios.put(`${URL.baseURL}/api/children`, children,{
                headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(res => {
                setHaschildren(res.data);
                console.log(res.data)
                notify("Dados editado", 'sucess');
                setLoading(false);
            }).catch(error => {
                notify(error.message, 'warning');
                setLoading(false);
            });
    }


    useEffect(()=>{
        verfifyFildsCreatechildren()
    },[children])
    
    useEffect(()=>{
        verifychildren()
    },[])

    const createChildren = () => {
        return (
            <>
                <CardContent style={{ textAlign: 'center' }} >
                    <form className={classes.root} noValidate autoComplete="off">
                        <TextField required name="name" label="Nome" variant="filled" value={children.name} onChange={handleChange}/>
                        <TextField required name="registration" label="Matricula" variant="filled" value={children.registration} onChange={handleChange}/>
                        <TextField required type="date" name="birthday" label="Aniversário"value={children.birthday} onChange={handleChange} className={classes.textField}
                            InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField required type="date" name="birthCertificateDate" label="Data de emissão da certidão" value={children.birthCertificateDate} onChange={handleChange} className={classes.textField}
                            InputLabelProps={{
                            shrink: true,
                            }}/>
                        <Toolbar>
                            <Button disabled={buttonDisabled} variant="contained" color="primary" onClick={onSubmitCreateChildren}>
                                <SaveIcon/>SALVAR
                            </Button>
                        </Toolbar>
                    </form>
                </CardContent>
            </>
        )
    }

    const editChildren = () => {
        return (
            <>
                <CardContent style={{ textAlign: 'center' }} >
                    <form className={classes.root} noValidate autoComplete="off">
                    <TextField disabled name="id" label="Id" variant="filled" value={children.id}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField disabled name="createdBy" label="Criado por" variant="filled" value={children.createdBy}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField disabled name="updatedBy" label="Atualizado por" variant="filled" value={children.updatedBy}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField disabled type="datetime" name="createdAt" label="Criado em" variant="filled" value={children.createdAt}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField disabled type="datetime" name="updatedAt" label="Atualizado em" variant="filled" value={children.updatedAt}
                        InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField required name="name" label="Nome" variant="filled" value={children.name} onChange={handleChange}/>
                        <TextField required name="registration" label="Matricula Certidão" variant="filled" value={children.registration} onChange={handleChange}/>
                        <TextField required type="date" name="birthday" label="Data de nascimento" variant="filled" value={children.birthday} onChange={handleChange} className={classes.textField}
                            InputLabelProps={{
                            shrink: true,
                            }}/>
                        <TextField required type="date" name="birthCertificateDate" label="Data de emissão da certidão" variant="filled" value={children.birthCertificateDate} onChange={handleChange}className={classes.textField}
                            InputLabelProps={{
                            shrink: true,
                            }}/>
                        
                        <Toolbar>
                            <Button disabled={buttonDisabled} variant="contained" color="primary" onClick={onSubmitUpdateChildren}>
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
            {loading?<Loading/>:haschildren?editChildren():createChildren()}
            
        </>
    )
}