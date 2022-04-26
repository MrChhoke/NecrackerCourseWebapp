INSERT INTO roles(user_of_shop_id, roles) VALUES (1, 'ADMIN');

delete
from roles a using roles AS b
where a.user_of_shop_id = b.user_of_shop_id
  and a.roles = b.roles
  and a.ctid < b.ctid;