import React from 'react';
import { format, parseISO } from 'date-fns';
import { Typography } from '@material-ui/core';

const LocalDateTimeField = ({ source, record = {} }) =>
  record[source] ? (
    <Typography>
      {format(parseISO(record[source]), 'dd/MM/yyyy HH:mm')}
    </Typography>
  ) : (
    <span />
  );

LocalDateTimeField.defaultProps = {
  addLabel: true,
};

export default LocalDateTimeField;
