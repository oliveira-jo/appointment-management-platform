
    create table tb_appointment (
        created_at timestamp(6),
        ends_at timestamp(6),
        scheduled_at timestamp(6),
        reminder_sent BOOLEAN,
        updated_at timestamp(6),
        customer_id uuid,
        id uuid not null,
        product_id uuid,
        professional_id uuid,
        appointment_status varchar(255) check (appointment_status in ('CONFIRMED','CANCELLED','COMPLETED')),
        notes varchar(255),
        primary key (id)
    );

    create table tb_product (
        duration_in_seconds numeric(21,0),
        price numeric(38,2),
        created_at timestamp(6),
        updated_at timestamp(6),
        id uuid not null,
        name varchar(255),
        primary key (id)
    );

    create table tb_user (
        created_at timestamp(6),
        updated_at timestamp(6),
        id uuid not null,
        email varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        user_role varchar(255) not null check (user_role in ('ROLE_ADMIN','ROLE_PROFESSIONAL','ROLE_CUSTOMER')),
        primary key (id)
    );

    alter table if exists tb_appointment 
       add constraint FKtnt3lh99ot5y11e69rlh59u21 
       foreign key (customer_id) 
       references tb_user;

    alter table if exists tb_appointment 
       add constraint FKp5gu85jxi6qgtluj4oexf50cj 
       foreign key (product_id) 
       references tb_product;

    alter table if exists tb_appointment 
       add constraint FK2rxn0s5335csn5rws27b8p0p7 
       foreign key (professional_id) 
       references tb_user;


INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('12345678-1234-1234-1234-123456781010', 'Jonathan Oliveira', '(11) 99999-9999', 'admin@', '$2a$10$6mKFS7q5HnuF8tIHGJIiJefmk1S8x6ms2KQ/Y3pHRUUVx0VeufxxS', 'ROLE_ADMIN', CURRENT_TIMESTAMP);

INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('12345678-1234-1234-1234-123456789012', 'Leandro Bueno', '(11) 99999-9999', 'leandro-cabelereiro@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_PROFESSIONAL', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('12345678-1234-1234-1234-123456789013', 'Mauro Vargas', '(11) 99999-9999', 'mauro-cabelereiro@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_PROFESSIONAL', CURRENT_TIMESTAMP);

INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('c7e8f9a0-b1c2-d3e4-f5a6-b7c8d9e0f1a2', 'Lucilene Massoco Oliveira', '(54) 98114-0395','oliveira.jo@hotmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('d8f9a0b1-c2d3-e4f5-a6b7-c8d9e0f1a2b3', 'Lucia Oliveira', '(54) 98114-0395','lucia@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('e9a0b1c2-d3e4-f5a6-b7c8-d9e0f1a2b3c4', 'Carla Brown', '(11) 99999-9999','carla@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('f0a1b2c3-d4e5-f6a7-b8c9-d0e1f2a3b4c5', 'Ana Maria', '(11) 9999-0000','ana@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('a1b2c3d4-e5f6-a7b8-c9d0-e1f2a3b4c5d6', 'Herta B. Massoco', '(11) 99999-9999','herta@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);
INSERT INTO tb_user (id, name, phone, email, password, user_role, created_at) VALUES ('b2c3d4e5-f678-a1b2-c3d4-e5f6a7b8c0d1', 'Camila Brown', '(11) 99999-9990','camila@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'ROLE_CUSTOMER', CURRENT_TIMESTAMP);


INSERT INTO tb_product (id, name, duration_in_seconds, price, created_at) VALUES ('5f41d15f-e3e8-4669-a28e-ae21b313e6af', 'Corte de Cabelo',  1800000000000,  40.00,  CURRENT_TIMESTAMP);
INSERT INTO tb_product (id, name, duration_in_seconds, price, created_at) VALUES ('c0f8c7e2-7b2d-4a1c-8f3e-5c0b8f1e2a3b', 'Corte de Cabelo Premium',  2700000000000,  55.00,  CURRENT_TIMESTAMP);
INSERT INTO tb_product (id, name, duration_in_seconds, price, created_at) VALUES ('a1b2c3d4-e5f6-7890-a1b2-c3d4e5f67890', 'Corte de Cabelo Premium e Barba',  3600000000000,  70.00,  CURRENT_TIMESTAMP);
INSERT INTO tb_product (id, name, duration_in_seconds, price, created_at) VALUES ('b2c3d4e5-f678-9012-a3b4-c5d6e7f89012', 'Pintura de Cabelo',  3600000000000,  80.00,  CURRENT_TIMESTAMP);

INSERT INTO tb_appointment (id, customer_id, professional_id, product_id, scheduled_at, reminder_sent, appointment_status, created_at) VALUES ('c7e8f9a0-b1c2-d3e4-f5a6-b7c8d9e0f1a2', 'c7e8f9a0-b1c2-d3e4-f5a6-b7c8d9e0f1a2', '12345678-1234-1234-1234-123456789012',  'b2c3d4e5-f678-9012-a3b4-c5d6e7f89012',  TIMESTAMP WITH TIME ZONE '2026-04-02T19:00:00Z', false, 'CONFIRMED', CURRENT_TIMESTAMP);

