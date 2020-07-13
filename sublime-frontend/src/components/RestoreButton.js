import React, { useState } from 'react';
import { Button, useDataProvider, useRefresh } from 'react-admin';

const RestoreButton = ({ record, resource }) => {
  const dataProvider = useDataProvider();
  const refresh = useRefresh();
  const [loading, setLoading] = useState(false);
  const handleClick = async () => {
    setLoading(true);
    await dataProvider('PUT', `${resource}/restore/${record.id}`, {});
    setLoading(false);
    refresh();
  };
  return (
    <Button
      disabled={loading}
      label="Restaurar"
      variant="contained"
      color="secondary"
      onClick={handleClick}
    />
  );
};

export default RestoreButton;
