USE 'chatop';

ALTER TABLE IF EXISTS MESSAGES DROP CONSTRAINT IF EXISTS fk_message_rental;
ALTER TABLE IF EXISTS MESSAGES DROP CONSTRAINT IF EXISTS fk_message_user;
DROP TABLE IF EXISTS MESSAGES;

ALTER TABLE IF EXISTS `USERS_ROLES` DROP CONSTRAINT IF EXISTS fk_role_user;
ALTER TABLE IF EXISTS `USERS_ROLES` DROP CONSTRAINT IF EXISTS fk_role_role;
DROP TABLE IF EXISTS `USERS_ROLES`;

ALTER TABLE IF EXISTS RENTALS DROP CONSTRAINT IF EXISTS fk_rental_owner;
DROP TABLE IF EXISTS RENTALS;

DROP TABLE IF EXISTS USERS;

DROP TABLE IF EXISTS ROLES;

CREATE TABLE `USERS` (
                         `id` integer PRIMARY KEY AUTO_INCREMENT,
                         `email` varchar(255) NOT NULL,
                         `name` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `created_at` timestamp NOT NULL,
                         `updated_at` timestamp
);

CREATE TABLE `RENTALS` (
                           `id` integer PRIMARY KEY AUTO_INCREMENT,
                           `name` varchar(255) NOT NULL,
                           `surface` numeric,
                           `price` numeric NOT NULL,
                           `picture` varchar(255) NOT NULL,
                           `description` varchar(2000),
                           `owner_id` integer NOT NULL,
                           `created_at` timestamp NOT NULL,
                           `updated_at` timestamp
);

CREATE TABLE `MESSAGES` (
                            `id` integer PRIMARY KEY AUTO_INCREMENT,
                            `rental_id` integer NOT NULL,
                            `user_id` integer NOT NULL,
                            `message` varchar(2000) NOT NULL,
                            `created_at` timestamp NOT NULL,
                            `updated_at` timestamp
);

CREATE TABLE `ROLES` (
                         `id` integer PRIMARY KEY AUTO_INCREMENT,
                         `name` varchar(255) NOT NULL,
                         `created_at` timestamp DEFAULT NOW(),
                         `updated_at` timestamp
);

CREATE TABLE `USERS_ROLES` (
                               `id` integer PRIMARY KEY AUTO_INCREMENT,
                               `user_id` integer NOT NULL,
                               `role_id` integer NOT NULL
);

CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);

ALTER TABLE `RENTALS` ADD CONSTRAINT fk_rental_owner FOREIGN KEY(`owner_id`) REFERENCES `USERS` (`id`) ;
ALTER TABLE `MESSAGES` ADD CONSTRAINT fk_message_user FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`) ;
ALTER TABLE `MESSAGES` ADD CONSTRAINT fk_message_rental FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`);
ALTER TABLE `USERS_ROLES` ADD CONSTRAINT fk_role_user FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`) ;
ALTER TABLE `USERS_ROLES` ADD CONSTRAINT fk_role_role FOREIGN KEY (`role_id`) REFERENCES `ROLES` (`id`);

INSERT INTO `ROLES` (`name`) VALUE ('USER');