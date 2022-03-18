-- Example username/password: luisdias/NQqm6JHWpzrzk5wxsTV84PUZp
insert into customer (password, username, id)
values ('$2a$10$dYc/kDsonbM/fEsERnDYG.itGP9kmjGgTrDN0fzCEaN6y/0JZVhba', 'luisdias', nextval('customer_id_seq'));

insert into device (customer_id, os, system_name, id)
values (currval('customer_id_seq'), 'WINDOWS', 'win-desktop-01', nextval('device_id_seq'));
insert into device (customer_id, os, system_name, id)
values (currval('customer_id_seq'), 'WINDOWS', 'win-desktop-02', nextval('device_id_seq'));
insert into device (customer_id, os, system_name, id)
values (currval('customer_id_seq'), 'MAC', 'mac-desktop-01', nextval('device_id_seq'));
insert into device (customer_id, os, system_name, id)
values (currval('customer_id_seq'), 'MAC', 'mac-desktop-02', nextval('device_id_seq'));
insert into device (customer_id, os, system_name, id)
values (currval('customer_id_seq'), 'MAC', 'mac-desktop-03', nextval('device_id_seq'));

-- Antivirus
insert into customer_service (customer_id, service_id, id)
values (currval('customer_id_seq'), 1, nextval('customer_service_id_seq'));
-- Cloudberry
insert into customer_service (customer_id, service_id, id)
values (currval('customer_id_seq'), 2, nextval('customer_service_id_seq'));
-- TeamViewer
insert into customer_service (customer_id, service_id, id)
values (currval('customer_id_seq'), 4, nextval('customer_service_id_seq'));

