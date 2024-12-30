-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 20 Okt 2024 pada 08.28
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hr_db`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `country`
--

CREATE TABLE `country` (
  `id` int(11) NOT NULL,
  `code` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `region_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `country`
--

INSERT INTO `country` (`id`, `code`, `name`, `region_id`) VALUES
(1, 'EG', 'Egypt', 3),
(2, 'FR', 'France', 2),
(3, 'JP', 'Japan', 1),
(4, 'SP', 'Spain', 2),
(5, 'ID', 'Indonesia', 1),
(6, 'TH', 'Thailand', 1),
(7, 'AU', 'Australia', 4),
(8, 'FJ', 'Fiji', 4);

-- --------------------------------------------------------

--
-- Struktur dari tabel `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(13) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `employee`
--

INSERT INTO `employee` (`id`, `email`, `name`, `phone`) VALUES
(1, 'sarahill@gmail.com', 'Sara Hill', '5151234567'),
(2, 'luis.popp@gmail.com', 'Luis Popp', '5151274561'),
(4, 'zidan@gmail.com', 'Zidan', '081223456778'),
(5, 'meli@gmail.com', 'Meli', '000111222333'),
(8, 'budi@gmail.com', 'budi', '000111222333'),
(9, 'avi@gmail.com', 'avi', '000111222333'),
(10, 'lia@gmail.com', 'lia', '000111222333'),
(11, 'dodi@gmail.com', 'dodi', '000111222333');

-- --------------------------------------------------------

--
-- Struktur dari tabel `privilege`
--

CREATE TABLE `privilege` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `privilege`
--

INSERT INTO `privilege` (`id`, `name`) VALUES
(5, 'create_admin'),
(1, 'create_user'),
(8, 'delete_admin'),
(4, 'delete_user'),
(6, 'read_admin'),
(2, 'read_user'),
(7, 'update_admin'),
(3, 'update_user');

-- --------------------------------------------------------

--
-- Struktur dari tabel `region`
--

CREATE TABLE `region` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `region`
--

INSERT INTO `region` (`id`, `name`) VALUES
(3, 'Africa'),
(1, 'Asia'),
(2, 'Europe'),
(4, 'Oceania');

-- --------------------------------------------------------

--
-- Struktur dari tabel `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(3, 'ADMIN'),
(1, 'USER');

-- --------------------------------------------------------

--
-- Struktur dari tabel `role_privilege`
--

CREATE TABLE `role_privilege` (
  `role_id` int(11) NOT NULL,
  `privilege_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `role_privilege`
--

INSERT INTO `role_privilege` (`role_id`, `privilege_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id`, `password`, `username`) VALUES
(4, '$2a$10$qRhhEQQq5hd6I72Nc32Mout7yFybM/hfR0bTF.u8b9LwNgdHJ0I2K', 'zidan'),
(5, '$2a$10$QlxSpy0DHJ1Z6b2sm.rNaeubqRK6I37huQLmJ3uOOPuwot9BjNFo6', 'meli'),
(8, '$2a$10$huVAny4dpQ4PlkjZcGZfGeeziPRreISk5TCCJUM29xAtdZUS95zsq', 'budi'),
(9, '$2a$10$3FqhsynP5eZVtCIJj8WB/.U5gH/9o2hRhC/9PUEuWRKKJFBnHeNXu', 'avi'),
(10, '$2a$10$Lil5hy4KWbUAZDh1IUsS7uQ.fFowkAPoQfKVbQ8PKgejUVYGxsnEO', 'lia'),
(11, '$2a$10$VwQ06nFWnDmXfKrYTe13W./OQwTZ8QIwFsNfVAMvuxQiXaV4EEF8O', 'dodi');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(4, 1),
(9, 3),
(10, 3),
(11, 1),
(11, 3);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `country`
--
ALTER TABLE `country`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`),
  ADD KEY `FKs3bda8801uhqtttuaur9r6eic` (`region_id`);

--
-- Indeks untuk tabel `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKfopic1oh5oln2khj8eat6ino0` (`email`);

--
-- Indeks untuk tabel `privilege`
--
ALTER TABLE `privilege`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKh7iwbdg4ev8mgvmij76881tx8` (`name`);

--
-- Indeks untuk tabel `region`
--
ALTER TABLE `region`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indeks untuk tabel `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK8sewwnpamngi6b1dwaa88askk` (`name`);

--
-- Indeks untuk tabel `role_privilege`
--
ALTER TABLE `role_privilege`
  ADD KEY `FKdkwbrwb5r8h74m1v7dqmhp99c` (`privilege_id`),
  ADD KEY `FKsykrtrdngu5iexmbti7lu9xa` (`role_id`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`);

--
-- Indeks untuk tabel `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `country`
--
ALTER TABLE `country`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT untuk tabel `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT untuk tabel `privilege`
--
ALTER TABLE `privilege`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT untuk tabel `region`
--
ALTER TABLE `region`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `country`
--
ALTER TABLE `country`
  ADD CONSTRAINT `FKs3bda8801uhqtttuaur9r6eic` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`);

--
-- Ketidakleluasaan untuk tabel `role_privilege`
--
ALTER TABLE `role_privilege`
  ADD CONSTRAINT `FKdkwbrwb5r8h74m1v7dqmhp99c` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`),
  ADD CONSTRAINT `FKsykrtrdngu5iexmbti7lu9xa` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Ketidakleluasaan untuk tabel `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKss65n39o4cvrrlryp5lmv19n0` FOREIGN KEY (`id`) REFERENCES `employee` (`id`);

--
-- Ketidakleluasaan untuk tabel `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
