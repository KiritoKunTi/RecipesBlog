drop table if exists Receipts;
drop table if exists Sessions;
drop table if exists Users;
create table if not exists Users (
                                id bigint not null auto_increment primary key ,
                                username varchar(50) not null,
                                password varchar(512) not null
);
create table if not exists Sessions (
                                    id bigint not null auto_increment primary key ,
                                    uuid varchar(512) not null,
                                    user_ID bigint not null,
                                    expired_at timestamp not null,
                                    foreign key(user_id) references Users(id)
);

create table if not exists Receipts(
                                    id bigint not null auto_increment primary key,
                                    name varchar(512) not null,
                                    user_id bigint not null,
                                    description text not null,
                                    created_at timestamp not null,
                                    foreign key(user_id) references Users(id)
);

create table if not exists Ingredients(
                                       id bigint not null auto_increment primary key,
                                       name varchar(512) not null,
                                       receipt_id bigint not null,
                                       measurement varchar(256) not null,
                                       amount  float(4, 2) not null,
                                       foreign key(receipt_id) references Receipts(id)
)