import {Id} from './shared';

export interface TaskWithId {
  id: number;
  shortDescription: string;
  fullDescription: string;
  state: State;
  project: Id;
}
export interface State {
  name: string;
}
export interface Tasks {
  tasks: TaskWithId[];
}
export interface Task {
  shortDescription: string;
  fullDescription: string;
}
