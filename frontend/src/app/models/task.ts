import { LabelWithId } from './label';
import { ProjectWithId } from './project';
import {Id} from './shared';

export interface TaskWithId {
  id: number;
  shortDescription: string;
  fullDescription: string;
  state: State;
  project: Id;
  labels: LabelWithId[]
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
export interface ActiveTask {
  id: number;
  shortDescription: string;
  fullDescription: string;
  project: ProjectWithId;
  labels: LabelWithId[]
}
export interface ActiveTaskList {
  toDo: ActiveTask[];
  inProgress: ActiveTask[];
}