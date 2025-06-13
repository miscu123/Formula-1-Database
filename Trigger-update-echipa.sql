-- Logheaza actualizarea unei echipe
DELIMITER //
CREATE TRIGGER TRG_UPDATE_ECHIPA
AFTER UPDATE ON Echipa
FOR EACH ROW
BEGIN
  INSERT INTO log_actiuni (Actiune, Timp) VALUES (CONCAT('Actualizare echipa: ', OLD.NumeEchipa), NOW());
END;
//
DELIMITER ;