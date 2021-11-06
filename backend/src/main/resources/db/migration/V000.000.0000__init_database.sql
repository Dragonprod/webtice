create sequence public.hibernate_sequence start 1 increment 1
create table public.attribute (id int8 not null, name varchar(255), description varchar(255), is_event boolean default false, is_glbobal boolean default false, primary key (id))
create table public.tag (id int8 not null, close_tag varchar(255), description varchar(255), is_used boolean default false, tag_name varchar(255), primary key (id))
create table public.tag_attributes (tag_id int8 not null, attribute_id int8 not null, primary key (tag_id, attribute_id))
alter table if exists public.tag_attributes add constraint FKbivygrn50k6nqjn8sl2pgycgq foreign key (attribute_id) references public.attribute
alter table if exists public.tag_attributes add constraint FKh70cpeu0gqyn60c84y5a7ml88 foreign key (tag_id) references public.tag