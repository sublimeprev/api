import React from 'react';
import PropTypes from 'prop-types';
import { Box } from '@material-ui/core';

export function TabPanel(props) {
  const { children, value, index } = props;
  return value === index ? (
    <Box style={{ padding: 16 }}>{children}</Box>
  ) : (
    <></>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};
