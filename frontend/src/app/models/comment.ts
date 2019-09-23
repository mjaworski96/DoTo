import {Id} from './shared';

export class CommentWithId {
  id: number;
  content: string;
  task: Id;
}
export class Comments {
  comments: CommentWithId[];
}
export class Comment {
  content: string;
}
