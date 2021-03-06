import ApolloClient from 'apollo-boost';
import { ConnectedRouter } from 'connected-react-router';
import { createBrowserHistory } from 'history';
import 'normalize.css';
import React from 'react';
import { ApolloProvider } from 'react-apollo';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import * as apollo from 'core/shared/graphql/apollo/apollo';
import cleanLocalStorageForNewVersion from 'core/shared/utils/cleanLocalStorageForNewVersion';

import App from './App/App';
import './index.css';
import configureStore from './store/configureStore';

const localStorageVersion = '1.0.14';
cleanLocalStorageForNewVersion(localStorageVersion);

const apolloClient = new ApolloClient({
  cache: apollo.makeCache(),
  uri: `${location.protocol}//${location.hostname}${
    location.port ? ':' + location.port : ''
  }/api/v1/graphql/query`,
});
apolloClient.defaultOptions.watchQuery = {
  ...(apolloClient.defaultOptions.watchQuery || {}),
  ...apollo.defaultOptions.watchQuery,
};

const history = createBrowserHistory();

const store = configureStore(history, undefined, [], apolloClient);

ReactDOM.render(
  <ApolloProvider client={apolloClient}>
    <Provider store={store}>
      <ConnectedRouter history={history}>
        <ToastContainer />
        <App />
      </ConnectedRouter>
    </Provider>
  </ApolloProvider>,
  document.getElementById('root')
);
