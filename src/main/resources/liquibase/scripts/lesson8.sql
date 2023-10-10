-- liquibase formatted sql

-- changeset Victor: создание ИНДЕКСов для поиска студента и названию и цвету факультета
CREATE INDEX student_name ON student ("name");
CREATE INDEX faculty_name_color ON faculty ("name",color);

