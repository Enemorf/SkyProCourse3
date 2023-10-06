ALTER TABLE students ALTER COLUMN age CHECK ( age > 16 );

ALTER TABLE students ADD PRIMARY KEY (name);

ALTER TABLE faculty ADD CONSTRAINT name_color_unique UNIQUE (name, color);

ALTER TABLE students ALTER COLUMN age INT DEFAULT 20;

