--TABLE roles
CREATE TABLE labels (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(50) NOT NULL,
	project_id INTEGER NOT NULL
);

ALTER TABLE labels ADD CONSTRAINT labels_pkey PRIMARY KEY (id);

CREATE SEQUENCE public.labels_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE labels_id_seq OWNED BY labels.id;
ALTER TABLE labels 
    ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);
	
ALTER TABLE labels 
	ADD CONSTRAINT fk_labels_projects FOREIGN KEY (project_id) REFERENCES projects(id);
	
--TABLE labels_tasks

CREATE TABLE labels_tasks (
    label_id INTEGER NOT NULL,
    task_id INTEGER NOT NULL
);

ALTER TABLE labels_tasks 
	ADD CONSTRAINT label_tasks_pkey PRIMARY KEY (label_id, task_id);
ALTER TABLE labels_tasks 
	ADD CONSTRAINT fk_labels_tasks_labels FOREIGN KEY (label_id) REFERENCES labels(id) ON DELETE CASCADE;
ALTER TABLE labels_tasks 
	ADD CONSTRAINT fk_labels_tasks_tasks FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE;