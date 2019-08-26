##社区项目
frist
##资料
hahah 
##工具
使用了bootstrap3框架生成了一个简易的导航栏
git 
##sql
create table USER
(
    ID           INTEGER default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_43ED9998_6524_4A6C_9E5E_089676B480A2) auto_increment,
    ACCOUNT_ID   VARCHAR(50),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
