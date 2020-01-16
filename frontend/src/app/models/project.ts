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
export interface ProjectArchived {
  archived: boolean;
}
