CREATE PROCEDURE CURSA_TOP3(IN cursa_id INT)
BEGIN
  DECLARE done INT DEFAULT FALSE;
  DECLARE pilot VARCHAR(100);
  DECLARE cur CURSOR FOR
    SELECT CONCAT(P.Nume, ' ', P.Prenume)
    FROM RezultatCursa R
    JOIN Pilot P ON R.PilotID = P.PilotID
    WHERE R.CursaID = cursa_id
    ORDER BY R.PozitieFinala ASC
    LIMIT 3;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN cur;
  read_loop: LOOP
    FETCH cur INTO pilot;
    IF done THEN
      LEAVE read_loop;
    END IF;
    SELECT pilot AS PilotTop;
  END LOOP;
  CLOSE cur;
END;