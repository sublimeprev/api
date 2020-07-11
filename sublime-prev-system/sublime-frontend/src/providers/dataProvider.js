/* eslint-disable no-param-reassign */
import { fetchUtils } from 'react-admin';
import URLs from './URLs';
import springServerProvider from './springProvider';

const httpClient = (url, options = {}) => {
  if (!options.headers) {
    options.headers = new Headers({ Accept: 'application/json' });
  }
  const token = localStorage.getItem('token');
  options.headers.set('Authorization', `Bearer ${token}`);
  return fetchUtils.fetchJson(url, options);
};

const springProvider = springServerProvider(`${URLs.baseURL}/api`, httpClient);

export default springProvider;
