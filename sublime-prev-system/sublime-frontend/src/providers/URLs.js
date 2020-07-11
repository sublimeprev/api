let baseURL = `${window.location.protocol}//${window.location.host}`;
if (baseURL === 'http://localhost:3000') baseURL = 'http://localhost:8080';
export default {
  baseURL,
};
