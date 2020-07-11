import polyglotI18nProvider from 'ra-i18n-polyglot';
import ptbrMessages from './ptBr';

export const messages = {
  ptBr: ptbrMessages,
};

const i18nProvider = polyglotI18nProvider(locale => messages[locale], 'ptBr', {
  allowMissing: true,
});

export default i18nProvider;
