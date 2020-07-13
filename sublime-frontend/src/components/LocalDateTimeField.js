import React from 'react';
import { format, parseISO } from 'date-fns';
import { Typography } from '@material-ui/core';

const LocalDateTimeField = ({ source, record = {} }) =>
  record[source] ? (
    <Typography>
      {format(parseISO(record[source]), "yyyy-MM-dd'T'HH:mm:ss.SSSxxx")}
    </Typography>
  ) : (
    <span />
  );

LocalDateTimeField.defaultProps = {
  addLabel: true,
};

export default LocalDateTimeField;
