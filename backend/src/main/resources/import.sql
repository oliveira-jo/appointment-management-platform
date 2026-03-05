

INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('12345678-1234-1234-1234-123456781010', 'Jonathan Oliveira', '(11) 99999-9999', 'oliveira.jo@hotmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_ADMIN', CURRENT_TIMESTAMP);


INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('12345678-1234-1234-1234-123456789012', 'LeandroBueno', 'Corte de Cabelo', 'leandro@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_PROFESSIONAL', CURRENT_TIMESTAMP);


INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('c7e8f9a0-b1c2-d3e4-f5a6-b7c8d9e0f1a2', 'Lucilene Massoco Oliveira', '(11) 99999-9999','lucilene@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('d8f9a0b1-c2d3-e4f5-a6b7-c8d9e0f1a2b3', 'Lucia Oliveira', '(11) 99999-9999','lucia@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('e9a0b1c2-d3e4-f5a6-b7c8-d9e0f1a2b3c4', 'Carla Brown', '(11) 99999-9999','carla@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('f0a1b2c3-d4e5-f6a7-b8c9-d0e1f2a3b4c5', 'Ana Maria', '(11) 9999-0000','ana@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('a1b2c3d4-e5f6-a7b8-c9d0-e1f2a3b4c5d6', 'Herta B. Massoco', '(11) 99999-9999','herta@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('b2c3d4e5-f678-a1b2-c3d4-e5f6a7b8c0d1', 'Camila Brown', '(11) 99999-9990','camila@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);


INSERT INTO tb_product (id, name, duration_in_seconds, price, created_at) VALUES ('5f41d15f-e3e8-4669-a28e-ae21b313e6af', 'Corte de Cabelo Básico',  1800000000000,  39.00,  CURRENT_TIMESTAMP);
INSERT INTO tb_product (id, name, duration_in_seconds, price, created_at) VALUES ('c0f8c7e2-7b2d-4a1c-8f3e-5c0b8f1e2a3b', 'Corte de Cabelo com Barba',  2700000000000,  59.00,  CURRENT_TIMESTAMP);
INSERT INTO tb_product (id, name, duration_in_seconds, price, created_at) VALUES ('a1b2c3d4-e5f6-7890-a1b2-c3d4e5f67890', 'Corte de Cabelo Premium',  2400000000000,  79.00,  CURRENT_TIMESTAMP);
INSERT INTO tb_product (id, name, duration_in_seconds, price, created_at) VALUES ('b2c3d4e5-f678-9012-a3b4-c5d6e7f89012', 'Corte de Cabelo Premium com Pintura',  3600000000000,  99.00,  CURRENT_TIMESTAMP);


INSERT INTO tb_appointment (id, customer_id, professional_id, product_id, scheduled_at, appointment_status, created_at) VALUES ('c7e8f9a0-b1c2-d3e4-f5a6-b7c8d9e0f1a2', 'c7e8f9a0-b1c2-d3e4-f5a6-b7c8d9e0f1a2', '12345678-1234-1234-1234-123456789012',  'b2c3d4e5-f678-9012-a3b4-c5d6e7f89012',  TIMESTAMP WITH TIME ZONE '2026-03-10T10:00:00Z', 'SCHEDULED', CURRENT_TIMESTAMP);
INSERT INTO tb_appointment (id, customer_id, professional_id, product_id, scheduled_at, appointment_status, created_at) VALUES ('d8f9a0b1-c2d3-e4f5-a6b7-c8d9e0f1a2b3', 'd8f9a0b1-c2d3-e4f5-a6b7-c8d9e0f1a2b3', '12345678-1234-1234-1234-123456789012',  'b2c3d4e5-f678-9012-a3b4-c5d6e7f89012',  TIMESTAMP WITH TIME ZONE '2026-03-11T10:00:00Z', 'SCHEDULED', CURRENT_TIMESTAMP);

