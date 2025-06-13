CREATE PROCEDURE AFISEAZA_REZULTATE_PILOT(IN pilot_id INT)
BEGIN
  SELECT C.NumeCursa, R.PozitieFinala, R.Puncte
  FROM RezultatCursa R
  JOIN Cursa C ON R.CursaID = C.CursaID
  WHERE R.PilotID = pilot_id;
END;