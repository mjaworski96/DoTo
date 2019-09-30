--TABLE roles
CREATE TABLE roles (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(20) NOT NULL
);

ALTER TABLE roles ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
ALTER TABLE roles 
	ADD CONSTRAINT con_unq_name UNIQUE (name);

CREATE SEQUENCE public.roles_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE roles_id_seq OWNED BY roles.id;
ALTER TABLE roles 
    ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);

--TABLE users

CREATE TABLE users (
    id INTEGER NOT NULL,
    username CHARACTER VARYING(20),
    password CHARACTER VARYING(60),
    email CHARACTER VARYING(254)
);
ALTER TABLE users 
	ADD CONSTRAINT users_pkey PRIMARY KEY (id);
ALTER TABLE users 
	ADD CONSTRAINT con_unq_username UNIQUE (username);
ALTER TABLE users 
	ADD CONSTRAINT con_unq_email UNIQUE (email);
ALTER TABLE users
	ADD CONSTRAINT con_min_username CHECK (LENGTH("username") >= 3);
ALTER TABLE users
	ADD CONSTRAINT con_min_email CHECK (LENGTH("email") >= 3);

CREATE SEQUENCE users_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE users_id_seq OWNED BY users.id;
ALTER TABLE users 
    ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);

--TABLE user_roles

CREATE TABLE user_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL
);

ALTER TABLE user_roles 
	ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);
ALTER TABLE user_roles 
	ADD CONSTRAINT fk_user_roles_roles FOREIGN KEY (role_id) REFERENCES roles(id);
ALTER TABLE user_roles 
	ADD CONSTRAINT fk_user_roles_users FOREIGN KEY (user_id) REFERENCES users(id);

--TABLE projects

CREATE TABLE projects (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(50) NOT NULL,
    description CHARACTER VARYING(300),
    owner_id INTEGER NOT NULL
);

ALTER TABLE projects 
	ADD CONSTRAINT projects_pkey PRIMARY KEY (id);
ALTER TABLE projects 
	ADD CONSTRAINT fk_projects_users FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE projects
	ADD CONSTRAINT con_min_name CHECK (LENGTH("name") >= 1);

CREATE SEQUENCE projects_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE projects_id_seq OWNED BY projects.id;
ALTER TABLE projects 
    ALTER COLUMN id SET DEFAULT nextval('public.projects_id_seq'::regclass);

--TABLE states

CREATE TABLE states (
    id INTEGER NOT NULL,
    name CHARACTER VARYING(20) NOT NULL
);

ALTER TABLE states 
	ADD CONSTRAINT states_pkey PRIMARY KEY (id);

CREATE SEQUENCE states_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE states_id_seq OWNED BY states.id;
ALTER TABLE states 
    ALTER COLUMN id SET DEFAULT nextval('public.states_id_seq'::regclass);

--TABLE tasks
CREATE TABLE tasks (
    id INTEGER NOT NULL,
    short_description CHARACTER VARYING(100) NOT NULL,
    full_description CHARACTER VARYING(1000),
    project_id INTEGER NOT NULL,
    state_id INTEGER NOT NULL
);

ALTER TABLE tasks 
	ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);
ALTER TABLE tasks 
	ADD CONSTRAINT fk_tasks_projects FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE;
ALTER TABLE tasks 
	ADD CONSTRAINT fk_tasks_states FOREIGN KEY (state_id) REFERENCES states(id);
ALTER TABLE tasks
	ADD CONSTRAINT con_min_short_description CHECK (LENGTH("short_description") >= 1);

CREATE SEQUENCE tasks_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE tasks_id_seq OWNED BY tasks.id;
ALTER TABLE tasks 
    ALTER COLUMN id SET DEFAULT nextval('public.tasks_id_seq'::regclass);

--TABLE comments

CREATE TABLE comments (
    id INTEGER NOT NULL,
    content CHARACTER VARYING(500) NOT NULL,
    task_id INTEGER NOT NULL
);

ALTER TABLE comments 
	ADD CONSTRAINT comments_pkey PRIMARY KEY (id);
ALTER TABLE comments 
	ADD CONSTRAINT fk_comments_tasks FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE;
ALTER TABLE comments
	ADD CONSTRAINT con_min_content CHECK (LENGTH("content") >= 1);

CREATE SEQUENCE comments_id_seq
    AS INTEGER
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE comments_id_seq OWNED BY comments.id;
ALTER TABLE comments 
    ALTER COLUMN id SET DEFAULT nextval('public.comments_id_seq'::regclass);
