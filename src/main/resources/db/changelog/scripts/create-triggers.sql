set search_path = "main_schema";

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