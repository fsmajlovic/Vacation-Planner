BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Users" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"admin_id"	TEXT,
	"daysleft"	INTEGER,
	"requests_id"	INTEGER
);
CREATE TABLE IF NOT EXISTS "Requests" (
	"id"	INTEGER,
	"from_date"	TEXT,
	"to_date"	TEXT
);
COMMIT;
