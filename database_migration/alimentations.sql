-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : jeu. 22 fév. 2024 à 14:55
-- Version du serveur : 5.7.39
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `alimentations`
--

-- --------------------------------------------------------

--
-- Structure de la table `aliments`
--

CREATE TABLE `aliments` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `poids_moyen` float NOT NULL,
  `calories` int(11) NOT NULL,
  `vitamines_C` float NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  `couleur_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `aliments`
--

INSERT INTO `aliments` (`id`, `nom`, `poids_moyen`, `calories`, `vitamines_C`, `type_id`, `couleur_id`) VALUES
(1, 'Pomme', 0.1, 52, 0, 1, 3),
(2, 'Banane', 0.15, 89, 0, 1, 6),
(3, 'Orange', 0.2, 47, 0, 1, 14),
(4, 'Fraise', 0.02, 33, 0, 1, 3),
(5, 'Abricot', 0.05, 48, 0, 1, 5),
(6, 'Ananas', 1, 50, 0, 1, 6),
(7, 'Avocat', 0.2, 160, 0, 1, 13),
(8, 'Cassis', 0.01, 63, 0, 1, 1),
(9, 'Cerise', 0.01, 50, 0, 1, 3),
(10, 'Citron', 0.15, 29, 0, 1, 6),
(11, 'Clémentine', 0.08, 47, 0, 1, 10),
(12, 'Coing', 0.3, 57, 0, 1, 6),
(13, 'Datte', 0.02, 282, 0, 1, 16),
(14, 'Figue', 0.05, 74, 0, 1, 14),
(15, 'Framboise', 0.01, 53, 0, 1, 3),
(16, 'Grenade', 0.2, 83, 0, 1, 3),
(17, 'Groseille', 0.01, 56, 0, 1, 3),
(18, 'Kiwi', 0.07, 61, 0, 1, 2),
(19, 'Litchi', 0.02, 66, 0, 1, 6),
(20, 'Mandarine', 0.08, 53, 0, 1, 13),
(21, 'Mangue', 0.3, 60, 0, 1, 5),
(22, 'Melon', 1, 34, 0, 1, 13),
(23, 'Mirabelle', 0.02, 67, 0, 1, 6),
(24, 'Mûre', 0.01, 43, 0, 1, 1),
(25, 'Myrtille', 0.01, 57, 0, 1, 5),
(26, 'Nectarine', 0.1, 44, 0, 1, NULL),
(27, 'Noix de coco', 1, 354, 0, 1, NULL),
(28, 'Olive', 0.02, 115, 0, 1, 13),
(29, 'Pamplemousse', 0.3, 42, 0, 1, 6),
(30, 'Papaye', 1, 43, 0, 1, NULL),
(31, 'Pastèque', 2, 30, 0, 1, 13),
(32, 'Pêche', 0.15, 39, 0, 1, NULL),
(33, 'Poire', 0.2, 57, 0, 1, 13),
(34, 'Pomelo', 0.25, 38, 0, 1, 6),
(35, 'Prune', 0.04, 46, 0, 1, 14),
(36, 'Raisin', 0.02, 0, 0, 1, 14),
(64, 'Carotte', 0.1, 41, 0, 4, NULL),
(65, 'Brocoli', 0.3, 34, 0, 4, NULL),
(66, 'Pomme de terre', 0.2, 0, 0, 4, NULL),
(67, 'Tomate', 0.15, 0, 0, 4, NULL),
(68, 'Artichaut', 0.5, 47, 0, 4, NULL),
(69, 'Asperge', 0.1, 20, 0, 4, NULL),
(70, 'Aubergine', 0.2, 25, 0, 4, NULL),
(71, 'Betterave', 0.3, 43, 0, 4, NULL),
(72, 'Blette', 0.3, 19, 0, 4, NULL),
(73, 'Brocoli', 0.3, 34, 0, 4, NULL),
(74, 'Carotte', 0.1, 41, 0, 4, NULL),
(75, 'Céleri', 0.4, 16, 0, 4, NULL),
(76, 'Champignon', 0.05, 22, 0, 4, NULL),
(77, 'Chou-fleur', 1, 25, 0, 4, NULL),
(78, 'Chou de Bruxelles', 0.02, 43, 0, 4, NULL),
(79, 'Concombre', 0.2, 15, 0, 4, NULL),
(80, 'Courgette', 0.3, 17, 0, 4, NULL),
(81, 'Échalote', 0.05, 72, 0, 4, NULL),
(82, 'Endive', 0.2, 17, 0, 4, NULL),
(83, 'Épinard', 0.2, 23, 0, 4, NULL),
(84, 'Fenouil', 0.4, 31, 0, 4, NULL),
(85, 'Haricot vert', 0.05, 31, 0, 4, NULL),
(86, 'Laitue', 0.3, 15, 0, 4, NULL),
(87, 'Maïs', 1, 86, 0, 4, NULL),
(88, 'Navet', 0.2, 28, 0, 4, NULL),
(89, 'Oignon', 0.1, 40, 0, 4, NULL),
(90, 'Panais', 0.3, 75, 0, 4, NULL),
(91, 'Petit pois', 0.01, 81, 0, 4, NULL),
(92, 'Poireau', 0.3, 0, 0, 4, NULL),
(93, 'Poivron', 0.2, 0, 0, 4, NULL),
(94, 'Pomme de terre', 0.2, 0, 0, 4, NULL),
(95, 'Potiron', 2, 0, 0, 4, NULL),
(96, 'Radis', 0.02, 0, 0, 4, NULL),
(97, 'Tomate', 0.15, 0, 0, 4, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `couleur`
--

CREATE TABLE `couleur` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `hexadecimal_rvb` char(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `couleur`
--

INSERT INTO `couleur` (`id`, `nom`, `hexadecimal_rvb`) VALUES
(1, 'Noir', '#000000'),
(2, 'blanc', '#FFFFFF'),
(3, 'Rouge', '#FF0000'),
(4, 'Citron vert', '#00FF00'),
(5, 'Bleu', '#0000FF'),
(6, 'Jaune', '#FFFF00'),
(7, 'Cyan / Aqua', '#00FFFF'),
(8, 'Magenta / Fuchsia', '#FF00FF'),
(9, 'argent', '#C0C0C0'),
(10, 'gris', '#808080'),
(11, 'Bordeaux', '#800000'),
(12, 'olive', '#808000'),
(13, 'couleur essai', '#FF4545'),
(14, 'Violet', '#800080'),
(15, 'Sarcelle', '#008080'),
(16, 'Marine', '#000080'),
(17, 'Rouge', '#FF0000');

-- --------------------------------------------------------

--
-- Structure de la table `fruits`
--

CREATE TABLE `fruits` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `couleur` varchar(255) DEFAULT NULL,
  `poids_moyen` float DEFAULT NULL,
  `calories` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `fruits`
--

INSERT INTO `fruits` (`id`, `nom`, `couleur`, `poids_moyen`, `calories`) VALUES
(1, 'Pomme', 'Rouge', 0.1, 52),
(2, 'Banane', 'Jaune', 0.15, 89),
(3, 'Orange', 'Orange', 0.2, 47),
(4, 'Fraise', 'Rouge', 0.02, 33),
(5, 'Abricot', 'Orange', 0.05, 48),
(6, 'Ananas', 'Marron', 1, 50),
(7, 'Avocat', 'Vert', 0.2, 160),
(8, 'Cassis', 'Noir', 0.01, 63),
(9, 'Cerise', 'Rouge', 0.01, 50),
(10, 'Citron', 'Jaune', 0.15, 29),
(11, 'Clémentine', 'Orange', 0.08, 47),
(12, 'Coing', 'Jaune', 0.3, 57),
(13, 'Datte', 'Marron', 0.02, 282),
(14, 'Figue', 'Violet', 0.05, 74),
(15, 'Framboise', 'Rouge', 0.01, 53),
(16, 'Grenade', 'Rouge', 0.2, 83),
(17, 'Groseille', 'Rouge', 0.01, 56),
(18, 'Kiwi', 'Marron', 0.07, 61),
(19, 'Litchi', 'Rose', 0.02, 66),
(20, 'Mandarine', 'Orange', 0.08, 53),
(21, 'Mangue', 'Orange', 0.3, 60),
(22, 'Melon', 'Vert', 1, 34),
(23, 'Mirabelle', 'Jaune', 0.02, 67),
(24, 'Mûre', 'Noir', 0.01, 43),
(25, 'Myrtille', 'Bleu', 0.01, 57),
(26, 'Nectarine', 'Orange', 0.1, 44),
(27, 'Noix de coco', 'Marron', 1, 354),
(28, 'Olive', 'Vert', 0.02, 115),
(29, 'Pamplemousse', 'Jaune', 0.3, 42),
(30, 'Papaye', 'Orange', 1, 43),
(31, 'Pastèque', 'Vert', 2, 30),
(32, 'Pêche', 'Orange', 0.15, 39),
(33, 'Poire', 'Vert', 0.2, 57),
(34, 'Pomelo', 'Jaune', 0.25, 38),
(35, 'Prune', 'Violet', 0.04, 46),
(36, 'Raisin', 'Violet', 0.02, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `legumes`
--

CREATE TABLE `legumes` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `couleur` varchar(255) DEFAULT NULL,
  `poids_moyen` float DEFAULT NULL,
  `calories` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `legumes`
--

INSERT INTO `legumes` (`id`, `nom`, `couleur`, `poids_moyen`, `calories`) VALUES
(1, 'Carotte', 'Orange', 0.1, 41),
(2, 'Brocoli', 'Vert', 0.3, 34),
(3, 'Pomme de terre', 'Marron', 0.2, NULL),
(4, 'Tomate', 'Rouge', 0.15, NULL),
(5, 'Artichaut', 'Vert', 0.5, 47),
(6, 'Asperge', 'Vert', 0.1, 20),
(7, 'Aubergine', 'Violet', 0.2, 25),
(8, 'Betterave', 'Rouge', 0.3, 43),
(9, 'Blette', 'Vert', 0.3, 19),
(10, 'Brocoli', 'Vert', 0.3, 34),
(11, 'Carotte', 'Orange', 0.1, 41),
(12, 'Céleri', 'Vert', 0.4, 16),
(13, 'Champignon', 'Marron', 0.05, 22),
(21, 'Fenouil', 'Vert', 0.4, 31),
(22, 'Haricot vert', 'Vert', 0.05, 31),
(23, 'Laitue', 'Vert', 0.3, 15),
(24, 'Maïs', 'Jaune', 1, 86),
(25, 'Navet', 'Blanc', 0.2, 28),
(26, 'Oignon', 'Marron', 0.1, 40),
(27, 'Panais', 'Blanc', 0.3, 75),
(28, 'Petit pois', 'Vert', 0.01, 81),
(29, 'Poireau', 'Vert', 0.3, NULL),
(30, 'Poivron', 'Rouge', 0.2, NULL),
(31, 'Pomme de terre', 'Marron', 0.2, NULL),
(32, 'Potiron', 'Orange', 2, NULL),
(33, 'Radis', 'Rouge', 0.02, NULL),
(34, 'Tomate', 'Rouge', 0.15, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `type_aliment`
--

CREATE TABLE `type_aliment` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `type_aliment`
--

INSERT INTO `type_aliment` (`id`, `nom`) VALUES
(1, 'fruit'),
(2, 'céréale'),
(3, 'condiment'),
(4, 'légume');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `aliments`
--
ALTER TABLE `aliments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type_id` (`type_id`),
  ADD KEY `couleur_id` (`couleur_id`);

--
-- Index pour la table `couleur`
--
ALTER TABLE `couleur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `fruits`
--
ALTER TABLE `fruits`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `legumes`
--
ALTER TABLE `legumes`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_aliment`
--
ALTER TABLE `type_aliment`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `aliments`
--
ALTER TABLE `aliments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=127;

--
-- AUTO_INCREMENT pour la table `couleur`
--
ALTER TABLE `couleur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT pour la table `fruits`
--
ALTER TABLE `fruits`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT pour la table `legumes`
--
ALTER TABLE `legumes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT pour la table `type_aliment`
--
ALTER TABLE `type_aliment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `aliments`
--
ALTER TABLE `aliments`
  ADD CONSTRAINT `aliments_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type_aliment` (`id`),
  ADD CONSTRAINT `aliments_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `type_aliment` (`id`),
  ADD CONSTRAINT `aliments_ibfk_3` FOREIGN KEY (`couleur_id`) REFERENCES `couleur` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
