create table resume
(
  uuid      char(36) not null
    constraint resume_pkey
    primary key,
  full_name text     not null
);

create table contact
(
  id          serial   not null
    constraint contact_pkey
    primary key,
  resume_uuid char(36) not null
    references resume (uuid)
    on delete cascade,
  type        text     not null,
  value       text     not null
);
create unique index contact_uuid_type_index
  on contact (resume_uuid, type);

create table section
(
  id serial not null
    constraint section_pkey
    primary key,
  section_type text not null,
  section_value text not null,
  resume_uuid char(36) not null
    constraint section_resume_uuid_fk
    references resume
    on delete cascade
);