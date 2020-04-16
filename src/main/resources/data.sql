/**
 * Create script to initiate DB
 */

-- Create 11 players

insert into player (id, balance, version) values (1, 342, 0);

insert into player (id, balance, version) values (2, 149.67, 0);

insert into player (id, balance, version) values (3, 1146, 0);

insert into player (id, balance, version) values (4, 0, 0);

insert into player (id, balance, version) values (5, 625, 0);

insert into player (id, balance, version) values (6, 0, 0);

insert into player (id, balance, version) values (7, 120, 0);

insert into player (id, balance, version) values (8, 237, 0);

insert into player (id, balance, version) values (9, 0, 0);

insert into player (id, balance, version) values (10, 0, 0);

insert into player (id, balance, version) values (11, 0, 0);


-- Create transactions

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (100, now(), 'ddf2536661233', 'DEBIT', 
40, 231, 1);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (101, now(), 'ddf2536661221', 'DEBIT', 
12, 219, 1);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (102, now(), 'ddf253666123', 'CREDIT', 
123, 342, 1);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (103, now(), 'ddf2536kjjjk', 'DEBIT', 
50.33, 149.67, 2);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (104, now(), 'ddf25trttttt', 'CREDIT', 
1233, 1233, 3);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (105, now(), 'uyy536661233', 'DEBIT', 
99, 1134, 3);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (106, now(), 'ddfjjg661233', 'CREDIT', 
12, 1146, 3);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (107, now(), 'ddf2536fjfbjf233', 'CREDIT', 
2, 572, 5);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (108, now(), 'ddf25546666', 'DEBIT', 
13, 559, 5);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (109, now(), 'ddf25234556655', 'CREDIT', 
66, 625, 5);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (110, now(), 'jdfjdkfjdfdf', 'CREDIT', 
120, 120, 7);

insert into transaction (id, date_created, number, type, amount, new_balance, player_id) values (111, now(), 'jfdjkffdddd', 'CREDIT', 
273, 273, 8);

