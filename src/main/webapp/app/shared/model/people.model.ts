import { Moment } from 'moment';

export interface IPeople {
  id?: string;
  photo?: string;
  firstname?: string;
  lastname?: string;
  entity?: string;
  entryDate?: Moment;
  birthDate?: Moment;
  gender?: string;
  email?: string;
  phone?: string;
  isManager?: boolean;
  manager?: string;
  managerId?: string;
}

export const defaultValue: Readonly<IPeople> = {
  isManager: false
};
