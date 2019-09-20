import {ProjectId} from './project';

export interface TaskWithId {
  id: number;
  shortDescription: string;
  fullDescription: string;
  state: State;
  project: ProjectId;
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
