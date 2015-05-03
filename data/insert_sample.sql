-- Role
insert into Role values('A1', 190000);
insert into Role values('A2', 192000);
insert into Role values('A3', 195000);
insert into Role values('C1', 200000);
insert into Role values('C2', 201000);
insert into Role values('C3', 202000);
insert into Role values('C4', 203000);
insert into Role values('C5', 204000);
insert into Role values('M1', 300000);
insert into Role values('M2', 320000);
insert into Role values('M3', 350000);
insert into Role values('CE', 500000);

-- Capability
insert into Capability values('AS', 50000);
insert into Capability values('PG', 100000);
insert into Capability values('SE', 150000);
insert into Capability values('PL', 270000);
insert into Capability values('PM', 300000);

-- Employee
insert into Employee values(1, '愛媛 蜜柑', '1987-07-18', '2013-04-01', 'A3', 'SE', 8900, 32000, 6432, 12000, 3200, 8900, 1250);
insert into Employee values(2, '大阪 太郎', '1988-09-30', '2010-08-01', 'C4', 'PL', 3320, 50000, 3200, 13000, 4000, 10300, 1570);
insert into Employee values(3, '埼玉 花子', '1982-08-06', '2008-04-01', 'M2', 'PM', 13000, 12000, 9811, 13500, 5230, 7300, 1830);
insert into Employee values(4, '東京 次郎', '1983-10-10', '2014-12-01', 'A1', 'AS', 5910, 40000, 5391, 11500, 2900, 15200, 1110);

-- Work
insert into Work values(1, 201504, 10.0, 0.0, 13.5, 3.5);
insert into Work values(2, 201504, 5.5, 0.0, 0.0, 0.0);
insert into Work values(3, 201504, 29.0, 11.0, 13.5, 10.5);
insert into Work values(4, 201504, 13.5, 3.0, 13.5, 5.0);
