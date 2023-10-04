SELECT students.name, students.age, faculty.name
FROM students
INNER JOIN faculty ON students.faculty_id = id;