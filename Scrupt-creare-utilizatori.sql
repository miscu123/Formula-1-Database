CREATE TABLE Utilizator (
  UserID INT AUTO_INCREMENT PRIMARY KEY,
  Username VARCHAR(50) UNIQUE,
  PasswordHash VARCHAR(64) -- stocare hash
);