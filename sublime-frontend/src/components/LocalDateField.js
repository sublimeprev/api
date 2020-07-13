import React from 'react';
import { format, parseISO } from 'date-fns';
import { Typography } from '@material-ui/core';

const LocalDateField = ({ source, record = {} }) =>
  record[source] ? (
    <Typography>{format(parseISO(record[source]), 'dd/MM/yyyy')}</Typography>
  ) : (
    <span />
  );

export default LocalDateField;
