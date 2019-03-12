import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import People from './people';
import PeopleDetail from './people-detail';
import PeopleUpdate from './people-update';
import PeopleDeleteDialog from './people-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PeopleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PeopleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PeopleDetail} />
      <ErrorBoundaryRoute path={match.url} component={People} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PeopleDeleteDialog} />
  </>
);

export default Routes;
