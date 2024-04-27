CREATE TABLE tags (
                      id bigserial PRIMARY KEY,
                      title varchar(75),
                      slug varchar(100)
);
CREATE TABLE users (
                       id bigserial PRIMARY KEY,
                       role_id INT NOT NULL,
                       first_name varchar(50),
                       middle_name varchar(50),
                       last_name varchar(50),
                       user_name varchar(50) UNIQUE NOT NULL,
                       phone varchar(15),
                       password_hash varchar(32) NOT NULL,
                       registred_at TIMESTAMP default now(),
                       last_login TIMESTAMP,
                       intro TEXT,
                       profile TEXT
);
CREATE TABLE tasks (
                       id bigserial PRIMARY KEY,
                       user_id bigint NOT NULL REFERENCES users(id),
                       created_by bigint NOT NULL REFERENCES users(id),
                       updated_by bigint NOT NULL REFERENCES users(id),
                       title varchar(512) NOT NULL,
                       description varchar(2048),
                       status INT NOT NULL ,
                       hours FLOAT NOT NULL,
                       created_at TIMESTAMP default now(),
                       updated_at TIMESTAMP,
                       planned_start TIMESTAMP,
                       planned_end TIMESTAMP,
                       actual_start TIMESTAMP,
                       actual_end TIMESTAMP,
                       content TEXT,
                       tag_id bigint references tags(id)
);
CREATE TABLE comments(
                         id bigserial PRIMARY KEY,
                         task_id bigint NOT NULL REFERENCES tasks(id) on delete cascade,
                         user_id bigint NOT NULL REFERENCES users(id) on delete cascade,
                         title varchar(100) NOT NULL,
                         created_at TIMESTAMP default now(),
                         updated_at TIMESTAMP,
                         content text
);
create or replace function update_at_changer ()
    returns trigger as $$
begin
    IF row(NEW.*) IS DISTINCT FROM row(OLD.*) THEN
        NEW.updated_at = now();
        RETURN NEW;
    ELSE
        RETURN OLD;
    END IF;
end;
$$ language 'plpgsql';

create trigger comments_updated_at
    before update
    on comments
    for each row
execute procedure update_at_changer ();

create trigger tasks_updated_at
    before update
    on tasks
    for each row
execute procedure update_at_changer ();