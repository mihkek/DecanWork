-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Май 13 2020 г., 19:57
-- Версия сервера: 10.4.11-MariaDB
-- Версия PHP: 7.2.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `decan`
--

-- --------------------------------------------------------

--
-- Структура таблицы `decanat`
--

CREATE TABLE `decanat` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `decanat`
--

INSERT INTO `decanat` (`id`, `name`) VALUES
(1, 'ФПИТЕ'),
(2, 'ФВТ'),
(8, 'Мед'),
(9, 'ФМТ');

-- --------------------------------------------------------

--
-- Структура таблицы `ekzam`
--

CREATE TABLE `ekzam` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `year` int(20) DEFAULT NULL,
  `idSem` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `ekzam`
--

INSERT INTO `ekzam` (`id`, `name`, `year`, `idSem`) VALUES
(1, 'Математика', 2019, 2),
(2, 'Информатика', 2020, 3),
(3, 'Русский', 2029, 2),
(4, 'Химия', 2020, 2),
(5, 'Физика', 2020, 3);

-- --------------------------------------------------------

--
-- Структура таблицы `ekzst`
--

CREATE TABLE `ekzst` (
  `id` int(11) NOT NULL,
  `idSt` int(11) NOT NULL,
  `idEkz` int(11) NOT NULL,
  `score` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `ekzst`
--

INSERT INTO `ekzst` (`id`, `idSt`, `idEkz`, `score`) VALUES
(4, 1, 1, 'Хорошо'),
(5, 2, 2, 'Отлично'),
(7, 2, 3, 'Удовлетворительно');

-- --------------------------------------------------------

--
-- Структура таблицы `groupa`
--

CREATE TABLE `groupa` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `idKaf` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `groupa`
--

INSERT INTO `groupa` (`id`, `name`, `idKaf`) VALUES
(1, '16во1', 6),
(2, '20во12', 3),
(4, '14вв2', 1),
(5, '17дд3', 7);

-- --------------------------------------------------------

--
-- Структура таблицы `kafedra`
--

CREATE TABLE `kafedra` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `idDec` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `kafedra`
--

INSERT INTO `kafedra` (`id`, `name`, `idDec`) VALUES
(1, 'Кафедра физики', 1),
(3, 'Кафедра химии', 8),
(6, 'ИВС', 2),
(7, 'ТТС', 9);

-- --------------------------------------------------------

--
-- Структура таблицы `semestr`
--

CREATE TABLE `semestr` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `semestr`
--

INSERT INTO `semestr` (`id`, `name`) VALUES
(2, 'Зимний'),
(3, 'Летний'),
(4, 'Итоговый');

-- --------------------------------------------------------

--
-- Структура таблицы `student`
--

CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `idGroup` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `student`
--

INSERT INTO `student` (`id`, `name`, `idGroup`) VALUES
(1, 'Иванов И И', 2),
(2, 'Петров П П', 4),
(3, 'Сидоров В В', 1),
(4, 'Ворошилов М А', 5);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `decanat`
--
ALTER TABLE `decanat`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `ekzam`
--
ALTER TABLE `ekzam`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idSem` (`idSem`);

--
-- Индексы таблицы `ekzst`
--
ALTER TABLE `ekzst`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idSt` (`idSt`,`idEkz`),
  ADD KEY `idEkz` (`idEkz`);

--
-- Индексы таблицы `groupa`
--
ALTER TABLE `groupa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idKaf` (`idKaf`);

--
-- Индексы таблицы `kafedra`
--
ALTER TABLE `kafedra`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idDec` (`idDec`);

--
-- Индексы таблицы `semestr`
--
ALTER TABLE `semestr`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idGroup` (`idGroup`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `decanat`
--
ALTER TABLE `decanat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT для таблицы `ekzam`
--
ALTER TABLE `ekzam`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблицы `ekzst`
--
ALTER TABLE `ekzst`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT для таблицы `groupa`
--
ALTER TABLE `groupa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблицы `kafedra`
--
ALTER TABLE `kafedra`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT для таблицы `semestr`
--
ALTER TABLE `semestr`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `student`
--
ALTER TABLE `student`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `ekzam`
--
ALTER TABLE `ekzam`
  ADD CONSTRAINT `ekzam_ibfk_1` FOREIGN KEY (`idSem`) REFERENCES `semestr` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `ekzst`
--
ALTER TABLE `ekzst`
  ADD CONSTRAINT `ekzst_ibfk_1` FOREIGN KEY (`idSt`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ekzst_ibfk_2` FOREIGN KEY (`idEkz`) REFERENCES `ekzam` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `groupa`
--
ALTER TABLE `groupa`
  ADD CONSTRAINT `groupa_ibfk_1` FOREIGN KEY (`idKaf`) REFERENCES `kafedra` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `kafedra`
--
ALTER TABLE `kafedra`
  ADD CONSTRAINT `kafedra_ibfk_1` FOREIGN KEY (`idDec`) REFERENCES `decanat` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`idGroup`) REFERENCES `groupa` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
