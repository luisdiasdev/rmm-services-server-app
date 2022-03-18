-- Antivirus
insert into available_service (name, description, id)
values ('Antivirus', 'Protect your devices', nextval('avail_serv_id_seq'));
insert into available_service_pricing_policy (available_service_id, os, price, id)
values (currval('avail_serv_id_seq'), 'WINDOWS', 500, nextval('avail_serv_pol_id_seq'));
insert into available_service_pricing_policy (available_service_id, os, price, id)
values (currval('avail_serv_id_seq'), 'MAC', 700, nextval('avail_serv_pol_id_seq'));

-- Cloudberry
insert into available_service (name, description, id)
values ('Cloudberry', 'Backup your data', nextval('avail_serv_id_seq'));
insert into available_service_pricing_policy (available_service_id, os, price, id)
values (currval('avail_serv_id_seq'), 'ALL', 300, nextval('avail_serv_pol_id_seq'));

-- PSA
insert into available_service (name, description, id)
values ('PSA', 'Ticketing System', nextval('avail_serv_id_seq'));
insert into available_service_pricing_policy (available_service_id, os, price, id)
values (currval('avail_serv_id_seq'), 'ALL', 200, nextval('avail_serv_pol_id_seq'));

-- TeamViewer
insert into available_service (name, description, id)
values ('TeamViewer', 'Remote Connection Tool', nextval('avail_serv_id_seq'));
insert into available_service_pricing_policy (available_service_id, os, price, id)
values (currval('avail_serv_id_seq'), 'ALL', 100, nextval('avail_serv_pol_id_seq'));
