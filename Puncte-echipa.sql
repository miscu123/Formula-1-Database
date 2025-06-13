CREATE PROCEDURE TOTAL_PUNCTE_PE_ECHIPA()
BEGIN
  SELECT E.NumeEchipa, SUM(R.Puncte) AS TotalPuncte
  FROM RezultatCursa R
  JOIN Pilot P ON R.PilotID = P.PilotID
  JOIN Echipa E ON P.EchipaID = E.EchipaID
  GROUP BY E.EchipaID;
END;