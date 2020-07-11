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
  dayOfWeek: {
    MONDAY: 'Segunda-Feira',
    TUESDAY: 'Terça-Feira',
    WEDNESDAY: 'Quarta-Feira',
    THURSDAY: 'Quinta-Feira',
    FRIDAY: 'Sexta-Feira',
    SATURDAY: 'Sábado',
    SUNDAY: 'Domingo',
  },
  schedulingStatus: {
    CANCELADO: 'Cancelado',
    MARCADO: 'Marcado',
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
  'agenda-configs': {
    empty: ptbrMessages.page.empty,
    invite: ptbrMessages.page.invite,
    name: 'Agenda Config. |||| Agendas Configs',
    fields: {
      updatedAt: 'Atualizado em',
      updatedBy: 'Atualizado por',
      dayOfWeek: 'Dia da Semana',
      time: 'Horário',
      maxLimit: 'Limite Máximo',
      listTypeExercise: 'Tipos de Treino',
      typeExercise: 'Tipos de Treino',
    },
  },
  schedulings: {
    empty: ptbrMessages.page.empty,
    invite: ptbrMessages.page.invite,
    name: 'Agendamento |||| Agendamentos',
    fields: {
      updatedAt: 'Atualizado em',
      updatedBy: 'Atualizado por',
      userDesc: 'Usuário',
      userId: 'Usuário',
      date: 'Data',
      time: 'Hora',
      status: 'Situação',
      typeTraining: 'Tipos de Treino',
      typeExercise: 'Tipos de Treino',
    },
  },
  'time-offs': {
    empty: ptbrMessages.page.empty,
    invite: ptbrMessages.page.invite,
    name: 'Dia de Folga |||| Dias de Folga',
    fields: {
      updatedAt: 'Atualizado em',
      updatedBy: 'Atualizado por',
      date: 'Data',
      time: 'Hora',
    },
  },
  'type-training': {
    empty: ptbrMessages.page.empty,
    invite: ptbrMessages.page.invite,
    name: 'Tipo de treino |||| Tipos de treino',
    fields: {
      type: 'Novo Tipo de Treino',
    },
  }
};

export default ptbrMessages;
