-- Creare baza de date
CREATE DATABASE IF NOT EXISTS F1_Sezon2024;
USE F1_Sezon2024;

-- TABEL: Echipa
CREATE TABLE Echipa (
    EchipaID INT AUTO_INCREMENT PRIMARY KEY,
    Nume VARCHAR(100) NOT NULL,
    Tara VARCHAR(50)
);

INSERT INTO Echipa (Nume, Tara) VALUES
('Red Bull Racing', 'Austria'),
('Mercedes AMG', 'Germania'),
('Ferrari', 'Italia'),
('McLaren', 'Marea Britanie'),
('Aston Martin', 'Marea Britanie'),
('Alpine', 'Franța'),
('Williams', 'Marea Britanie'),
('Alfa Romeo', 'Elveția'),
('Haas F1 Team', 'SUA'),
('AlphaTauri', 'Italia');

-- TABEL: Pilot
CREATE TABLE Pilot (
    PilotID INT AUTO_INCREMENT PRIMARY KEY,
    Nume VARCHAR(100) NOT NULL,
    Nationalitate VARCHAR(50),
    DataNasterii DATE,
    EchipaID INT,
    NumărStart INT,
    FOREIGN KEY (EchipaID) REFERENCES Echipa(EchipaID)
);

INSERT INTO Pilot (Nume, Nationalitate, DataNasterii, EchipaID, NumărStart) VALUES
('Max Verstappen', 'Olanda', '1997-09-30', 1, 1),
('Sergio Perez', 'Mexic', '1990-01-26', 1, 11),
('Lewis Hamilton', 'Marea Britanie', '1985-01-07', 2, 44),
('George Russell', 'Marea Britanie', '1998-02-15', 2, 63),
('Charles Leclerc', 'Monaco', '1997-10-16', 3, 16),
('Carlos Sainz', 'Spania', '1994-09-01', 3, 55),
('Lando Norris', 'Marea Britanie', '1999-11-13', 4, 4),
('Oscar Piastri', 'Australia', '2001-04-06', 4, 81),
('Fernando Alonso', 'Spania', '1981-07-29', 5, 14),
('Lance Stroll', 'Canada', '1998-10-29', 5, 18),
('Esteban Ocon', 'Franța', '1996-09-17', 6, 31),
('Pierre Gasly', 'Franța', '1996-02-07', 6, 10),
('Alexander Albon', 'Thailanda', '1996-03-23', 8, 23),
('Valtteri Bottas', 'Finlanda', '1989-08-28', 8, 77),
('Kevin Magnussen', 'Danemarca', '1992-10-05', 9, 20),
('Nico Hülkenberg', 'Germania', '1987-08-19', 9, 27),
('Yuki Tsunoda', 'Japonia', '2000-05-11', 10, 22),
('Daniil Kvyat', 'Rusia', '1994-04-26', 10, 26),
('Logan Sargeant', 'SUA', '2000-12-31', 7, 2),
('Alex Albon', 'Thailanda', '1996-03-23', 7, 23);

-- TABEL: Circuit
CREATE TABLE Circuit (
    CircuitID INT AUTO_INCREMENT PRIMARY KEY,
    Nume VARCHAR(100),
    Tara VARCHAR(50),
    Oras VARCHAR(50),
    LungimeKm DECIMAL(5,2)
);

INSERT INTO Circuit (Nume, Tara, Oras, LungimeKm) VALUES
('Bahrain International Circuit', 'Bahrein', 'Sakhir', 5.41),
('Jeddah Street Circuit', 'Arabia Saudită', 'Jeddah', 6.17),
('Albert Park Circuit', 'Australia', 'Melbourne', 5.28),
('Suzuka Circuit', 'Japonia', 'Suzuka', 5.81),
('Shanghai International Circuit', 'China', 'Shanghai', 5.45),
('Miami International Autodrome', 'SUA', 'Miami', 5.41),
('Imola Circuit', 'Italia', 'Imola', 4.91),
('Circuit de Monaco', 'Monaco', 'Monte Carlo', 3.34),
('Circuit de Barcelona-Catalunya', 'Spania', 'Barcelona', 4.66),
('Red Bull Ring', 'Austria', 'Spielberg', 4.32);

-- TABEL: Cursa
CREATE TABLE Cursa (
    CursaID INT AUTO_INCREMENT PRIMARY KEY,
    CircuitID INT,
    DataCursa DATE,
    NumeCursa VARCHAR(100),
    FOREIGN KEY (CircuitID) REFERENCES Circuit(CircuitID)
);

INSERT INTO Cursa (NumeCursa, DataCursa, CircuitID) VALUES
('Grand Prix Bahrain', '2024-03-02', 1),
('Grand Prix Arabia Saudită', '2024-03-09', 2),
('Grand Prix Australia', '2024-03-24', 3),
('Grand Prix Japonia', '2024-04-07', 4),
('Grand Prix China', '2024-04-21', 5),
('Grand Prix Miami', '2024-05-05', 6),
('Grand Prix Emilia-Romagna', '2024-05-19', 7),
('Grand Prix Monaco', '2024-05-26', 8),
('Grand Prix Spania', '2024-06-23', 9),
('Grand Prix Austria', '2024-06-30', 10);

-- TABEL: Masina
CREATE TABLE Masina (
    MasinaID INT AUTO_INCREMENT PRIMARY KEY,
    EchipaID INT,
    Model VARCHAR(50),
    AnProd YEAR,
    Motor VARCHAR(50),
    FOREIGN KEY (EchipaID) REFERENCES Echipa(EchipaID)
);

INSERT INTO Masina (EchipaID, Model, AnProd, Motor) VALUES
(6, 'A524', 2024, 'Renault'),
(5, 'AMR24', 2024, 'Mercedes'),
(3, 'SF-24', 2024, 'Ferrari'),
(9, 'VF-24', 2024, 'Ferrari'),
(8, 'C44', 2024, 'Ferrari'),
(4, 'MCL38', 2024, 'Mercedes'),
(2, 'W15', 2024, 'Mercedes'),
(10, 'VCARB 01', 2024, 'Honda RBPT'),
(1, 'RB20', 2024, 'Honda RBPT'),
(7, 'FW46', 2024, 'Mercedes');

-- TABEL: RezultatCursa
CREATE TABLE RezultatCursa (
    RezultatID INT AUTO_INCREMENT PRIMARY KEY,
    CursaID INT,
    PilotID INT,
    PozitieFinala INT,
    TimpFinal TIME,
    Puncte INT,
    FOREIGN KEY (CursaID) REFERENCES Cursa(CursaID),
    FOREIGN KEY (PilotID) REFERENCES Pilot(PilotID)
);

-- Exemplu rezultate (GP Bahrain 2024)
INSERT INTO RezultatCursa (CursaID, PilotID, PozitieFinala, TimpFinal, Puncte) VALUES
(1, 1, 1, '01:31:24', 25),
(1, 2, 2, '01:31:59', 18),
(1, 3, 3, '01:32:11', 15),
(1, 4, 4, '01:32:32', 12),
(1, 5, 5, '01:32:55', 10),
(1, 6, 6, '01:33:11', 8),
(1, 7, 7, '01:33:31', 6),
(1, 8, 8, '01:33:52', 4),
(1, 9, 9, '01:34:13', 2),
(1, 10, 10, '01:34:35', 1);