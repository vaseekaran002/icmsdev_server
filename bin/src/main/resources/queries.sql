INSERT INTO tenants(id, name, status, description) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 'Goldware', 'active', 'super tenant for creating other tenants');
--default roles
INSERT INTO roles(id,name) VALUES(1,'USER');
INSERT INTO roles(id,name) VALUES(2,'SUPER_ADMIN');
INSERT INTO roles(id,name) VALUES(3,'TENANT_ADMIN');
INSERT INTO roles(id,name) VALUES(4,'MUSICIAN');

--users
INSERT INTO users(id, email, password, username, tenant_id, first_name, last_name) VALUES ('3ed2c758-5e09-4732-b094-10c917996601', 'superadmin@gmail.com', '$2a$10$LyoftssoV/7VAxTqWM2ou.zKyyd/65mPOvhslrwX2wCc9i8GVAl5e', 'superadmin', '67efdecb-849f-4f21-8d7f-13df06a3ce09', 'superadmin', 'superadmin');
INSERT INTO user_roles(user_id, role_id) VALUES ('3ed2c758-5e09-4732-b094-10c917996601', 2);
--metadata
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (1, 'mygroups', 3, 'My Groups', 'active','USER');
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (2, 'mypages', 2, 'Favorite Bands', 'active','USER');
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (3, 'events', 1, 'Events','active','USER');
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (4, 'exclusive', 4, 'Exclusive Content', 'active','USER');
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (5, 'marketplace', 5,'Marketplace', 'active','USER');
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (6, 'exclusive', 2, 'Exclusive Content', 'active','MUSICIAN');
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (7, 'marketplace', 6,'Marketplace', 'active','MUSICIAN');
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (8, 'admin', 1,'Administration', 'active','MUSICIAN');
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (9, 'pages', 3, 'Pages', 'active','MUSICIAN');
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (10, 'fans', 5, 'Fans', 'active','MUSICIAN');
INSERT INTO metadata(id, component_name, component_order, display_name, status,role) VALUES (11, 'groups', 4, 'Groups', 'active','MUSICIAN');

INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 1);
INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 2);
INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 3);
INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 4);
INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 5);
INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 6);
INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 7);
INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 8);
INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 9);
INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 10);
INSERT INTO tenant_metadata(tenant_id, meta_data_id) VALUES ('67efdecb-849f-4f21-8d7f-13df06a3ce09', 11);
