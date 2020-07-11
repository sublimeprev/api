import {
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  FormControlLabel,
  Paper,
  TextField,
} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import React, { useState } from 'react';
import {
  Button,
  useDataProvider,
  useNotify,
  useRefresh,
  useUnselectAll,
} from 'react-admin';
import Draggable from 'react-draggable';

const PaperComponent = props => {
  return (
    <Draggable
      handle="#draggable-dialog-title"
      cancel={'[class*="MuiDialogContent-root"]'}
    >
      <Paper {...props} />
    </Draggable>
  );
};

const useStyles = makeStyles(theme => ({
  root: {
    '& .MuiTextField-root': {
      marginTop: theme.spacing(1),
    },
  },
}));

const CancelBulkButton = ({ selectedIds, resource }) => {
  const [open, setOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    title: 'Sublime Prev Salário Maternidade',
    content: 'Seu treino foi cancelado',
    sendNotify: false,
  });
  const classes = useStyles();
  const dataProvider = useDataProvider();
  const refresh = useRefresh();
  const notify = useNotify();
  const unselectAll = useUnselectAll();
  const updateMany = () => {
    setLoading(true);
    dataProvider('PUT', `${resource}/cancel?ids=${selectedIds}`, formData)
      .then(() => {
        refresh();
        notify('Agendamentos cancelados');
        unselectAll(resource);
        setLoading(false);
      })
      .catch(error => {
        notify(error.message, 'warning');
        setLoading(false);
      });
  };
  const handleClick = () => setOpen(true);
  const handleDialogClose = () => setOpen(false);
  const handleConfirm = () => {
    updateMany();
    setOpen(false);
  };
  return (
    <>
      <Button
        label="Cancelar selecionados"
        disabled={loading}
        onClick={handleClick}
        variant="outlined"
        style={{ color: '#a94442', borderColor: '#a94442' }}
      ></Button>
      {/* <Confirm
        isOpen={open}
        loading={loading}
        title="Cancelar Agendamentos"
        content="Você tem certeza que deseja cancelar os agendamentos selecionados?"
        onConfirm={handleConfirm}
        onClose={handleDialogClose}
      /> */}
      <Dialog
        open={open}
        onClose={handleDialogClose}
        PaperComponent={PaperComponent}
        aria-labelledby="draggable-dialog-title"
      >
        <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
          Cancelar Agendamentos
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            Você tem certeza que deseja cancelar os agendamentos selecionados?
            <form className={classes.root}>
              <FormControlLabel
                control={
                  <Checkbox
                    checked={formData.sendNotify}
                    onChange={event => {
                      setFormData({
                        ...formData,
                        sendNotify: event.target.checked,
                      });
                    }}
                    value="primary"
                    inputProps={{ 'aria-label': 'primary checkbox' }}
                  />
                }
                label="Enviar Notificação para Usuários"
              />
              {formData.sendNotify && (
                <>
                  <TextField
                    label="Título"
                    value={formData.title}
                    onChange={event => {
                      setFormData({
                        ...formData,
                        sendNotify: event.target.value,
                      });
                    }}
                  />
                  <TextField
                    label="Mensagem"
                    value={formData.content}
                    onChange={event => {
                      setFormData({
                        ...formData,
                        sendNotify: event.target.value,
                      });
                    }}
                  />
                </>
              )}
            </form>
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={handleDialogClose}
            color="primary"
            label="Cancelar"
          />
          <Button onClick={handleConfirm} color="primary" label="Confirmar" />
        </DialogActions>
      </Dialog>
    </>
  );
};

export default CancelBulkButton;
