export interface ProjectWithId {
  id: number;
  name: string;
  description: string;
  archived: boolean;
}
export interface Project {
  name: string;
  description: string;
}
export interface ProjectStateChange {
  archived: boolean;
}
