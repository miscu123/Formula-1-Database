-- Afiseaza mesaj la stergerea unui pilot
DELIMITER //
CREATE TRIGGER TRG_DELETE_PILOT
AFTER DELETE ON Pilot
FOR EACH ROW
BEGIN
  INSERT INTO log_actiuni (Actiune, Timp) VALUES (CONCAT('Pilot sters: ', OLD.Nume), NOW());
END;
//
DELIMITER ;