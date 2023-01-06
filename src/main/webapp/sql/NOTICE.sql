CREATE TABLE NOTICE(
ID NUMBER,
TITLE NVARCHAR2(100),
WRITER_ID NVARCHAR2(50),
CONTENT CLOB,
REGDATE TIMESTAMP,
HIT NUMBER,
FILES NVARCHAR2(1000),
PUB NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE
);
SELECT * FROM notice;