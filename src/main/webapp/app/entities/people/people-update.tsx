import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './people.reducer';
import { IPeople } from 'app/shared/model/people.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPeopleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPeopleUpdateState {
  isNew: boolean;
}

export class PeopleUpdate extends React.Component<IPeopleUpdateProps, IPeopleUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { peopleEntity } = this.props;
      const entity = {
        ...peopleEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/people');
  };

  render() {
    const { peopleEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="jhipsterReactSchoolApp.people.home.createOrEditLabel">
              <Translate contentKey="jhipsterReactSchoolApp.people.home.createOrEditLabel">Create or edit a People</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : peopleEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="people-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="photoLabel" for="photo">
                    <Translate contentKey="jhipsterReactSchoolApp.people.photo">Photo</Translate>
                  </Label>
                  <AvField id="people-photo" type="text" name="photo" />
                </AvGroup>
                <AvGroup>
                  <Label id="firstNameLabel" for="firstName">
                    <Translate contentKey="jhipsterReactSchoolApp.people.firstName">First Name</Translate>
                  </Label>
                  <AvField id="people-firstName" type="text" name="firstName" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="lastName">
                    <Translate contentKey="jhipsterReactSchoolApp.people.lastName">Last Name</Translate>
                  </Label>
                  <AvField id="people-lastName" type="text" name="lastName" />
                </AvGroup>
                <AvGroup>
                  <Label id="companyNameLabel" for="companyName">
                    <Translate contentKey="jhipsterReactSchoolApp.people.companyName">Company Name</Translate>
                  </Label>
                  <AvField id="people-companyName" type="text" name="companyName" />
                </AvGroup>
                <AvGroup>
                  <Label id="entryDateLabel" for="entryDate">
                    <Translate contentKey="jhipsterReactSchoolApp.people.entryDate">Entry Date</Translate>
                  </Label>
                  <AvField id="people-entryDate" type="date" className="form-control" name="entryDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="birthDateLabel" for="birthDate">
                    <Translate contentKey="jhipsterReactSchoolApp.people.birthDate">Birth Date</Translate>
                  </Label>
                  <AvField id="people-birthDate" type="date" className="form-control" name="birthDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="genderLabel" for="gender">
                    <Translate contentKey="jhipsterReactSchoolApp.people.gender">Gender</Translate>
                  </Label>
                  <AvField id="people-gender" type="text" name="gender" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="email">
                    <Translate contentKey="jhipsterReactSchoolApp.people.email">Email</Translate>
                  </Label>
                  <AvField id="people-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneNumberLabel" for="phoneNumber">
                    <Translate contentKey="jhipsterReactSchoolApp.people.phoneNumber">Phone Number</Translate>
                  </Label>
                  <AvField id="people-phoneNumber" type="text" name="phoneNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="isManagerLabel" check>
                    <AvInput id="people-isManager" type="checkbox" className="form-control" name="isManager" />
                    <Translate contentKey="jhipsterReactSchoolApp.people.isManager">Is Manager</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="managerLabel" for="manager">
                    <Translate contentKey="jhipsterReactSchoolApp.people.manager">Manager</Translate>
                  </Label>
                  <AvField id="people-manager" type="text" name="manager" />
                </AvGroup>
                <AvGroup>
                  <Label id="managerIdLabel" for="managerId">
                    <Translate contentKey="jhipsterReactSchoolApp.people.managerId">Manager Id</Translate>
                  </Label>
                  <AvField id="people-managerId" type="text" name="managerId" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/people" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  peopleEntity: storeState.people.entity,
  loading: storeState.people.loading,
  updating: storeState.people.updating,
  updateSuccess: storeState.people.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PeopleUpdate);
