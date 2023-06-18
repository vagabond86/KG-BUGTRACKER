INSERT INTO person (id, email, login, password, user_real_name)
SELECT nextval('person_seq'),
       CONCAT('email', number, '@wsb.com'),
       CONCAT('login', number),
       CONCAT('password', number),
       CONCAT('User', number)
FROM GENERATE_SERIES(1, 100) AS number;

INSERT INTO project(id, date_created, description, enabled, name, creator_id)
SELECT nextval('project_seq'),
       now(),
       'Description ' || number,
       TRUE,
       'Project ' || number,
       (ARRAY [1702, 2201, 1902, 2301, 2251, 2302, 2303, 2304, 2151, 2101])[1 + (number - 1) % 10]
FROM GENERATE_SERIES(1, 100) AS number;

INSERT INTO issue(id, date_created, description, last_updated, name, priority, status, type, assignee_id, creator_id,
                  project_id)
SELECT nextval('issue_seq'),
       now(),
       'Description ' || number,
       now(),
       'Issue ' || number,
       (ARRAY ['MINOR', 'NORMAL', 'MAJOR' ])[1 + (number - 1) % 3],
       (ARRAY ['TODO', 'IN_PROGRESS', 'DONE' ])[1 + (number - 1) % 3],
       (ARRAY ['BUG', 'TASK'])[1 + (number - 1) % 2],
       (ARRAY [1702, 2201, 1902, 2301, 2251, 2302, 2303, 2304, 2151, 2101])[1 + (number - 1) % 10],
       (ARRAY [1702, 2201, 1902, 2301, 2251, 2302, 2303, 2304, 2151, 2101])[1 + (number - 1) % 10],
       601102
FROM GENERATE_SERIES(1, 100) AS number;
