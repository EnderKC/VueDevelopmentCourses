/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2023/12/5 11:16:49                           */
/*==============================================================*/

/*==============================================================*/
/* Table: StuInfo                                               */
/*==============================================================*/
create table StuInfo
(
    stuid                char(20) not null  comment '学号',
    stuname              char(20) not null  comment '姓名',
    gender               char(2)  comment '性别',
    birthday             date  comment '出生日期',
    classname            char(20)  comment '班级',
    address              char(50)  comment '籍贯',
    primary key (stuid)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

alter table StuInfo comment '学生信息表';

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
    username             char(40) not null  comment '用户名',
    password             char(20) not null  comment '密码',
    role                 varchar(20) not null  comment '用户角色',
    stuid                char(20)  comment '学号',
    primary key (username)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

alter table User comment '用户类型字段分为教师和学生两种角色';

/*==============================================================*/
/* Table: course                                                */
/*==============================================================*/
create table course
(
    cid                  char(32) not null  comment '课程编号',
    cname                char(30) not null  comment '课程名',
    primary key (cid)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

alter table course comment '课程表';

/*==============================================================*/
/* Table: course_choosing                                       */
/*==============================================================*/
create table course_choosing
(
    stuid                char(20) not null  comment '学号',
    cid                  char(32) not null  comment '课程编号',
    cdate                date not null  comment '选课日期',
    primary key (stuid, cid)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

alter table course_choosing comment '学生选课';

/*==============================================================*/
/* Table: score                                                 */
/*==============================================================*/
create table score
(
    id                   char(32) not null  comment '编号',
    cid                  char(32) not null  comment '课程编号',
    stuid                char(20) not null  comment '学号',
    score                numeric(4,1) not null  comment '成绩',
    sdate                date not null  comment '考试日期',
    primary key (id)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

alter table score comment '成绩表';

alter table User add constraint FK_USER_R1_STUINFO foreign key (stuid)
    references StuInfo (stuid) on delete restrict on update restrict;

alter table course_choosing add constraint FK_COURSE_C_R4_COURSE foreign key (cid)
    references course (cid) on delete restrict on update restrict;

alter table course_choosing add constraint FK_COURSE_C_R5_STUINFO foreign key (stuid)
    references StuInfo (stuid) on delete restrict on update restrict;

alter table score add constraint FK_SCORE_R2_COURSE foreign key (cid)
    references course (cid) on delete restrict on update restrict;

alter table score add constraint FK_SCORE_R3_STUINFO foreign key (stuid)
    references StuInfo (stuid) on delete restrict on update restrict;

