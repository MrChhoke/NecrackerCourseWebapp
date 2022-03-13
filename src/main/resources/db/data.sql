
insert ignore into user_of_shop(password,username)
values ('$2a$10$t1AlTxvKGv4dXvhuP2YixOil6MhERrdKoxbAqo8/vlmcgRPBSCvZK','admin');
insert into roles(user_of_shop_id, roles) values (1,'ADMIN');