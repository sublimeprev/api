import React from 'react';
import { TextInput, Filter, BooleanInput } from 'react-admin';

export const ListFilterWithDeleteds = props => (
  <Filter {...props}>
    <TextInput label="Pesquisar" source="q" alwaysOn />
    {/* <BooleanInput
      label="Exibir Excluídos"
      source="deleted"
      inputProps={{ 'aria-label': 'secondary checkbox' }}
      alwaysOn
    /> */}
  </Filter>
);

export default props => (
  <Filter {...props}>
    <TextInput label="Pesquisar" source="q" alwaysOn />
  </Filter>
);
