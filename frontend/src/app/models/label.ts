export class Label {
    name: string;
}
export class LabelWithId {
    id: number;
    name: string;
}

export class LabelWithIdList {
    labels: LabelWithId[];
}