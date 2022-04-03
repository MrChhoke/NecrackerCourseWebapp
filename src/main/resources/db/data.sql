insert into user_of_shop(password, username)
values ('$2a$10$t1AlTxvKGv4dXvhuP2YixOil6MhERrdKoxbAqo8/vlmcgRPBSCvZK', 'admin');

insert into roles(user_of_shop_id, roles)
values (1, 'ADMIN');

delete
from roles a using roles AS b
where a.user_of_shop_id = b.user_of_shop_id
  and a.roles = b.roles
  and a.ctid < b.ctid;