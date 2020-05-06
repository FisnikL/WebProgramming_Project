import {GroupModerator} from './group-moderator';
import {GroupPicture} from './group-picture';

export class GroupDetails {
  public id: number;
  public code: string;
  public name: string;
  public description: string;
  public moderators: GroupModerator[];
}
