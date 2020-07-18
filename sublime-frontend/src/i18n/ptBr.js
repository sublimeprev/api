import ptbrMessages from 'ra-language-portuguese';

ptbrMessages.messages = {
  required: 'Preenchimento obrigatório',
  loading: 'Carregando',
  wait: 'Aguarde',
};

ptbrMessages.page = {
  empty: 'Nenhum registro encontrado',
  invite: 'Deseja criar um novo?',
};

ptbrMessages.enums = {
  roles: {
    ADMIN: 'Admin',
  },

  maritalStatus: {
    SOLTEIRO: 'Solteira',
    CASADO: 'Casada',
    DICORCIADO: 'Divorciada',
    VIUVO: 'Viúva'
  },

  schooling: {
    FUNDAMENTAL_INCOMPLETO: 'Fundamental Icompleto',
    FUNDAMENTAL_COMPLETO: 'Fundamental Completo',
    MEDIO_INCOMPLETO: 'Médio Incompleto',
    MEDIO_COMPLETO: 'Médio Completo',
    SUPERIOR_INCOMPLETO: 'Superior Incompleto',
    SUPERIOR_COMPLETO: 'Superior Completo'
  },

  processStatus: {
    ANALISE: 'Ánalise',
    CONCEDIDO: 'Concedido',
    INDEFERIDO: 'Indeferido',
    OBSERVACAO: 'Observação'
  }
};

ptbrMessages.forms = {
  summary: 'Cadastro',
};

ptbrMessages.mbrnTable = {
  pagination: {
    labelDisplayedRows: '{from} - {to} de {count}',
    labelRowsSelect: 'registros',
    labelRowsPerPage: 'Registros por página:',
    firstAriaLabel: 'Primeira página',
    firstTooltip: 'Primeira página',
    previousAriaLabel: 'Página anterior',
    previousTooltip: 'Página anterior',
    nextAriaLabel: 'Próxima página',
    nextTooltip: 'Próxima página',
    lastAriaLabel: 'Última página',
    lastTooltip: 'Última página',
  },
  toolbar: {
    nRowsSelected: '{0} linha(s) selecionada',
  },
  header: {
    actions: 'Ações',
  },
  body: {
    emptyDataSourceMessage: 'Nenhum registro encontrado',
    filterRow: {
      filterTooltip: 'Filtrar',
    },
  },
};

ptbrMessages.resources = {
  users: {
    empty: ptbrMessages.page.empty,
    invite: ptbrMessages.page.invite,
    name: 'Usuário |||| Usuários',
    fields: {
      updatedAt: 'Atualizado em',
      updatedBy: 'Atualizado por',
      username: 'Login',
      name: 'Nome Completo',
      password: 'Nova Senha',
      email: 'E-mail',
      roles: 'Permissões',
      phone: 'Telefone',
      city: 'Cidade',
      birthdate: 'Data de Nascimento',
      schedulingsEndDate: 'Data Final para Agendamentos',
      listTypeExercise: 'Tipos de Treino',
      typeExercise: 'Tipos de Treino',
      encryptedPassword: 'Senha',
      comments: 'Observações',
    },
  },

  mothers: {
    empty: ptbrMessages.page.empty,
    invite: ptbrMessages.page.invite,
    name: 'Mãe |||| Mães',
    fields: {
      updatedAt: 'Atualizado em',
      updatedBy: 'Atualizado por',
      createdAt: 'Criado em',
      createdBy: 'Criado por',
      name: 'Nome Completo',
      email: 'E-mail',
      phone: 'Telefone',
      birthdate: 'Data de Nascimento',
      cpf: 'CPF',
      rg: 'RG',
      pis: 'Número do pis',
      maritalStatus: 'Estado Cívil',
      schooling: 'Escolaridade',
      fatherName: 'Nome do pai',
      motherName: 'Nome da mãe'
    },
  },
};

export default ptbrMessages;
