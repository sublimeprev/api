import {
  AUTH_LOGIN,
  AUTH_LOGOUT,
  AUTH_CHECK,
  AUTH_GET_PERMISSIONS,
  AUTH_ERROR,
} from 'react-admin';
import URLs from './URLs';

export default (type, params) => {
  if (type === AUTH_LOGIN) {
    const { username, password } = params;
    const basicAuth = `Basic ${btoa('cesargymapp:cesargymapp-secret')}`;
    const request = new Request(
      `${URLs.baseURL}/oauth/token?username=${username}&password=${password}&grant_type=password`,
      {
        method: 'POST',
        headers: new Headers({
          'Content-Type': 'application/json',
          Authorization: basicAuth,
        }),
      }
    );
    return fetch(request)
      .then(response => {
        if (response.status < 200 || response.status >= 300) {
          throw new Error(response.statusText);
        }
        return response.json();
      })
      .then(json => {
        localStorage.setItem('token', json.access_token);
        return fetch(
          new Request(`${URLs.baseURL}/profile/me`, {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${json.access_token}`,
            },
          })
        )
          .then(resp => resp.json())
          .then(user => {
            localStorage.setItem('roles', JSON.stringify(user.roles));
            return json;
          });
      });
  }
  if (type === AUTH_LOGOUT) {
    const token = localStorage.getItem('token');
    if (token) {
      return fetch(
        new Request(`${URLs.baseURL}/profile/logout`, {
          method: 'DELETE',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
      ).then(() => {
        localStorage.removeItem('token');
        localStorage.removeItem('roles');
        return Promise.resolve();
      });
    }
  }
  if (type === AUTH_CHECK) {
    return localStorage.getItem('token') ? Promise.resolve() : Promise.reject();
  }
  if (type === AUTH_GET_PERMISSIONS) {
    const roles = localStorage.getItem('roles');
    return roles ? Promise.resolve(JSON.parse(roles)) : Promise.reject();
  }
  if (type === AUTH_ERROR) {
    const status = params.status;
    if (status === 401) {
      localStorage.removeItem('token');
      return Promise.reject();
    }
    return Promise.resolve();
  }
  return Promise.resolve();
};
