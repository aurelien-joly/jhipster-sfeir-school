import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Media } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './people.reducer';
import { IPeople } from 'app/shared/model/people.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPeopleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PeopleDetail extends React.Component<IPeopleDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { peopleEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="jhipsterSfeirSchoolApp.people.detail.title">People</Translate> [<b>{peopleEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="photo">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.photo">Photo</Translate>
              </span>
            </dt>
            <dd>
              <Media object src={peopleEntity.photo} alt="Generic placeholder image" />
            </dd>
            <dt>
              <span id="firstName">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.firstName">First Name</Translate>
              </span>
            </dt>
            <dd>{peopleEntity.firstname}</dd>
            <dt>
              <span id="lastName">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.lastName">Last Name</Translate>
              </span>
            </dt>
            <dd>{peopleEntity.lastname}</dd>
            <dt>
              <span id="companyName">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.companyName">Company Name</Translate>
              </span>
            </dt>
            <dd>{peopleEntity.entity}</dd>
            <dt>
              <span id="entryDate">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.entryDate">Entry Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={peopleEntity.entryDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="birthDate">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.birthDate">Birth Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={peopleEntity.birthDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="gender">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.gender">Gender</Translate>
              </span>
            </dt>
            <dd>{peopleEntity.gender}</dd>
            <dt>
              <span id="email">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.email">Email</Translate>
              </span>
            </dt>
            <dd>{peopleEntity.email}</dd>
            <dt>
              <span id="phoneNumber">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.phoneNumber">Phone Number</Translate>
              </span>
            </dt>
            <dd>{peopleEntity.phone}</dd>
            <dt>
              <span id="isManager">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.isManager">Is Manager</Translate>
              </span>
            </dt>
            <dd>{peopleEntity.isManager ? 'true' : 'false'}</dd>
            <dt>
              <span id="manager">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.manager">Manager</Translate>
              </span>
            </dt>
            <dd>{peopleEntity.manager}</dd>
            <dt>
              <span id="managerId">
                <Translate contentKey="jhipsterSfeirSchoolApp.people.managerId">Manager Id</Translate>
              </span>
            </dt>
            <dd>{peopleEntity.managerId}</dd>
          </dl>
          <Button tag={Link} to="/entity/people" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/people/${peopleEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ people }: IRootState) => ({
  peopleEntity: people.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PeopleDetail);
