import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPeople, defaultValue } from 'app/shared/model/people.model';

export const ACTION_TYPES = {
  SEARCH_PEOPLE: 'people/SEARCH_PEOPLE',
  FETCH_PEOPLE_LIST: 'people/FETCH_PEOPLE_LIST',
  FETCH_PEOPLE: 'people/FETCH_PEOPLE',
  CREATE_PEOPLE: 'people/CREATE_PEOPLE',
  UPDATE_PEOPLE: 'people/UPDATE_PEOPLE',
  DELETE_PEOPLE: 'people/DELETE_PEOPLE',
  RESET: 'people/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPeople>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type PeopleState = Readonly<typeof initialState>;

// Reducer

export default (state: PeopleState = initialState, action): PeopleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PEOPLE):
    case REQUEST(ACTION_TYPES.FETCH_PEOPLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PEOPLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PEOPLE):
    case REQUEST(ACTION_TYPES.UPDATE_PEOPLE):
    case REQUEST(ACTION_TYPES.DELETE_PEOPLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PEOPLE):
    case FAILURE(ACTION_TYPES.FETCH_PEOPLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PEOPLE):
    case FAILURE(ACTION_TYPES.CREATE_PEOPLE):
    case FAILURE(ACTION_TYPES.UPDATE_PEOPLE):
    case FAILURE(ACTION_TYPES.DELETE_PEOPLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PEOPLE):
    case SUCCESS(ACTION_TYPES.FETCH_PEOPLE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PEOPLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PEOPLE):
    case SUCCESS(ACTION_TYPES.UPDATE_PEOPLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PEOPLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/people';
const apiSearchUrl = 'api/_search/people';

// Actions

export const getSearchEntities: ICrudSearchAction<IPeople> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PEOPLE,
  payload: axios.get<IPeople>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IPeople> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PEOPLE_LIST,
    payload: axios.get<IPeople>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPeople> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PEOPLE,
    payload: axios.get<IPeople>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPeople> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PEOPLE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPeople> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PEOPLE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPeople> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PEOPLE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
