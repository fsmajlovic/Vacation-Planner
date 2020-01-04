BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Users" (
	"id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"admin_id"	INTEGER,
	"daysleft"	INTEGER,
	"requests_id"	INTEGER
);
COMMIT;
