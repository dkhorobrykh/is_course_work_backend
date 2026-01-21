alter table public.is_course_user_audit
    add keycloak_id text;

alter table public.is_course_user_audit
    alter column id type text using id::text;

alter table public.is_course_user_roles_audit
    alter column user_id type text using user_id::text;