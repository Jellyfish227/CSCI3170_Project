/*
Student ID: 1155193237 Name: Yu Ching Hei
*/

/* Query 1 */
Spool result1.lst
SELECT e.ENAME
FROM ENGINEER e
         JOIN OFFICE o ON e.OFFICEID = o.OFFICEID
WHERE o.ONAME = 'Compatibility Tools Group'
  AND e.ESALARY = (
    SELECT MAX(e2.ESALARY)
    FROM ENGINEER e2
             JOIN OFFICE o2 ON e2.OFFICEID = o2.OFFICEID
    WHERE o2.ONAME = 'Compatibility Tools Group'
)
ORDER BY e.ENAME ASC;
Spool off

/*Query 2*/
Spool result2.lst
SELECT DISTINCT e.ENAME
FROM ENGINEER e
         JOIN OFFICE o ON e.OFFICEID = o.OFFICEID
WHERE o.ONAME = 'Compatibility Tools Group'
  AND e.ESALARY = (
    SELECT MAX(e2.ESALARY)
    FROM ENGINEER e2
             JOIN OFFICE o2 ON e2.OFFICEID = o2.OFFICEID
    WHERE o2.ONAME = 'Compatibility Tools Group'
)
ORDER BY e.ENAME ASC;
Spool off

/*Query 3*/
Spool result3.lst
SELECT o.OFFICEID, o.ONAME
FROM OFFICE o
         JOIN PROJECT p ON o.OFFICEID = p.OFFICEID
GROUP BY o.OFFICEID, o.ONAME
HAVING SUM(p.PBUDGET) > 20000
ORDER BY o.OFFICEID DESC;
Spool off

/*Query 4*/
Spool result4.lst
SELECT o.PCATEGORY, AVG(COUNT) AS HEAD_COUNT
FROM (
         SELECT PCATEGORY, COUNT(ENGINEERID) AS COUNT
         FROM PROJECT
                  NATURAL JOIN RESEARCH
         GROUP BY PCATEGORY
     ) o
GROUP BY o.PCATEGORY
ORDER BY o.PCATEGORY DESC;
Spool off

/*Query 5*/
Spool result5.lst

Spool off

/*Query 6*/
Spool result6.lst

Spool off