import React, {useState, useEffect} from 'react';
import axios from 'axios';
import URL from '../providers/URLs.js';
import {
    Loading,
    useNotify,
    required,
    SaveButton,
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
  },
}));

export default function AddressMother({...props}){
    const classes = useStyles();
    const [loading, setLoading] = useState(false);
    const [hasAddress, setHasAddress] = useState(true);
    const [buttonDisabled, setButtonDisabled] = useState(true)
    const [address, setAddress] = useState({
        street: "",
        numberHouse: "",
        neighborhood: "",
        city: "",
        state: "",
        complement: "",
        zipcode: "",
        idMother: props.id
    })
    const notify = useNotify();
    const idMother = props.id;

    const handleChange = (evt) => {
        const value = evt.target.value;
        console.log(value)
        setAddress({
            ...address,
            [evt.target.name]: value
        });

    }
    
    const verifyAddress = () => {
        setLoading(true)
        axios.get(`${URL.baseURL}/api/address-mothers/${idMother}`,{
              headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
              }
            }).then(res => {
                setAddress(res.data);
                console.log(res.data)
              setLoading(false);
            }).catch(error => {
                setHasAddress(false)
            });
        }

    const verfifyFildsCreateAddress = () => {
        if(address.street === "" || address.neighborhood === "" || address.city === ""|| address.state === ""){
            setButtonDisabled(true)
        }else {
            setButtonDisabled(false)
        }
    }
    const onSubmitCreateAddress = () => {
        console.log("onsubmit")
        console.log(address)
        setLoading(true)
        axios.post(`${URL.baseURL}/api/address-mothers`, address,{
                headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(res => {
                setHasAddress(res.data);
                console.log(res.data)
                notify("Endereço salvo", 'sucess');
                setLoading(false);
            }).catch(error => {
                notify(error.message, 'warning');
            });
    }

    const onSubmitUpdateAddress = () => {
        console.log("onsubmit")
        console.log(address)
        setLoading(true)
        axios.put(`${URL.baseURL}/api/address-mothers`, address,{
                headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }).then(res => {
                setHasAddress(res.data);
                console.log(res.data)
                notify("Endereço editado", 'sucess');
                setLoading(false);
            }).catch(error => {
                notify(error.message, 'warning');
            });
    }


    useEffect(()=>{
        verfifyFildsCreateAddress()
    },[address])
    
    useEffect(()=>{
        verifyAddress()
    },[])

    const createAddress = () => {
        return (
            <>
                <CardContent style={{ textAlign: 'center' }} >
                    <form className={classes.root} noValidate autoComplete="off">
                        <TextField required name="state" label="Estado" variant="filled" value={address.state} onChange={handleChange}/>
                        <TextField required name="city" label="Cidade" variant="filled" value={address.city} onChange={handleChange}/>
                        <TextField required name="neighborhood" label="Bairro" variant="filled" value={address.neighborhood} onChange={handleChange}/>
                        <TextField required name="street" label="Rua" variant="filled" value={address.street} onChange={handleChange}/>
                        <TextField name="complement" label="Complemento" variant="filled" value={address.complement} onChange={handleChange}/>
                        <TextField name="numberHouse" type="number" label="Número da casa" variant="filled" value={address.numberHouse} onChange={handleChange} />
                        <TextField name="zipcode" type="number" label="CEP" variant="filled" value={address.zipcode} onChange={handleChange}/>
                        <Toolbar>
                            <Button disabled={buttonDisabled} variant="contained" color="primary" onClick={onSubmitCreateAddress}>
                                <SaveIcon/>SALVAR
                            </Button>
                        </Toolbar>
                    </form>
                </CardContent>
            </>
        )
    }

    const editAddress = () => {
        return (
            <>
                <CardContent style={{ textAlign: 'center' }} >
                    <form className={classes.root} noValidate autoComplete="off">
                    <TextField required name="state" label="Estado" variant="filled" value={address.state} onChange={handleChange}/>
                        <TextField required name="city" label="Cidade" variant="filled" value={address.city} onChange={handleChange}/>
                        <TextField required name="neighborhood" label="Bairro" variant="filled" value={address.neighborhood} onChange={handleChange}/>
                        <TextField required name="street" label="Rua" variant="filled" value={address.street} onChange={handleChange}/>
                        <TextField name="complement" label="Complemento" variant="filled" value={address.complement} onChange={handleChange}/>
                        <TextField name="numberHouse" type="number" label="Número da casa" variant="filled" value={address.numberHouse} onChange={handleChange} />
                        <TextField name="zipcode" type="number" label="CEP" variant="filled" value={address.zipcode} onChange={handleChange}/>
                        <Toolbar>
                            <Button disabled={buttonDisabled} variant="contained" color="primary" onClick={onSubmitUpdateAddress}>
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
            {hasAddress?editAddress():createAddress()}
            
        </>
    )
}