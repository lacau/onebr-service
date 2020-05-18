-- roles
insert into role(name) VALUES('ADMIN');
insert into role(name) VALUES('USER');

-- REMOVER ---
-- user temp
insert into profile(name, email) VALUES('Leandro Lacau', 'llacau@gmail.com');
insert into profile(name, email) VALUES('Sr Test', 'llacau@gmail.com');
-- password = 'test'
insert into public.user(fk_profile , username , password , active ) values(1, 'lacau', '$2y$10$MEXA4ZllMvxaxZdSEl8oUee3q7NgFibvwcAeJ3.z.rFh5mmz6NeMm', true);
insert into public.user(fk_profile , username , password , active ) values(2, 'test', '$2y$10$MEXA4ZllMvxaxZdSEl8oUee3q7NgFibvwcAeJ3.z.rFh5mmz6NeMm', true);
insert into user_role values(1, 1);
insert into user_role values(2, 2);
--------------

-- users
insert into profile(name, email) VALUES('Nilton Lincopan', 'lincopan@usp.br');
insert into public.user(fk_profile , username , password , active) values((select p.id from profile p where p.email = 'lincopan@usp.br'), 'nlincopan', '$2y$10$MEXA4ZllMvxaxZdSEl8oUee3q7NgFibvwcAeJ3.z.rFh5mmz6NeMm', true);
insert into user_role values((select u.id from public.user u where u.username = 'nlincopan'), (select r.id from role r where r.name = 'ADMIN'));

insert into profile(name, email) VALUES('Fernanda Esposito', 'fernandaesposito@usp.br');
insert into public.user(fk_profile , username , password , active) values((select p.id from profile p where p.email = 'fernandaesposito@usp.br'), 'fesposito', '$2y$10$MEXA4ZllMvxaxZdSEl8oUee3q7NgFibvwcAeJ3.z.rFh5mmz6NeMm', true);
insert into user_role values((select u.id from public.user u where u.username = 'fesposito'), (select r.id from role r where r.name = 'USER'));
insert into user_specie values((select u.id from public.user u where u.username = 'fesposito'), 2);

insert into profile(name, email) VALUES('Bruna Fuga', 'bruna.fuga@hotmail.com');
insert into public.user(fk_profile , username , password , active) values((select p.id from profile p where p.email = 'bruna.fuga@hotmail.com'), 'bfuga', '$2y$10$MEXA4ZllMvxaxZdSEl8oUee3q7NgFibvwcAeJ3.z.rFh5mmz6NeMm', true);
insert into user_role values((select u.id from public.user u where u.username = 'bfuga'), (select r.id from role r where r.name = 'USER'));
insert into user_specie values((select u.id from public.user u where u.username = 'bfuga'), 1);

insert into profile(name, email) VALUES('Herisson Fontana', 'hfontana@outlook.com');
insert into public.user(fk_profile , username , password , active) values((select p.id from profile p where p.email = 'hfontana@outlook.com'), 'hfontana', '$2y$10$MEXA4ZllMvxaxZdSEl8oUee3q7NgFibvwcAeJ3.z.rFh5mmz6NeMm', true);
insert into user_role values((select u.id from public.user u where u.username = 'hfontana'), (select r.id from role r where r.name = 'USER'));
insert into user_specie values((select u.id from public.user u where u.username = 'hfontana'), 3);

insert into profile(name, email) VALUES('Adriana Cardenas', 'adrianacardenas@gmail.com');
insert into public.user(fk_profile , username , password , active) values((select p.id from profile p where p.email = 'adrianacardenas@gmail.com'), 'acardenas', '$2y$10$MEXA4ZllMvxaxZdSEl8oUee3q7NgFibvwcAeJ3.z.rFh5mmz6NeMm', true);
insert into user_role values((select u.id from public.user u where u.username = 'acardenas'), (select r.id from role r where r.name = 'USER'));

insert into profile(name, email) VALUES('Brenda Cardoso', 'brenda.cardoso@usp.br');
insert into public.user(fk_profile , username , password , active) values((select p.id from profile p where p.email = 'brenda.cardoso@usp.br'), 'bcardoso', '$2y$10$MEXA4ZllMvxaxZdSEl8oUee3q7NgFibvwcAeJ3.z.rFh5mmz6NeMm', true);
insert into user_role values((select u.id from public.user u where u.username = 'bcardoso'), (select r.id from role r where r.name = 'USER'));