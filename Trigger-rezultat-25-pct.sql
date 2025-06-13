-- Nu permite inserarea unui rezultat cu mai mult de 25 puncte
DELIMITER //
CREATE TRIGGER TRG_INSERT_REZULTAT
BEFORE INSERT ON RezultatCursa
FOR EACH ROW
BEGIN
  IF NEW.Puncte > 25 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Punctajul nu poate depssi 25!';
  END IF;
END;
//
DELIMITER ;