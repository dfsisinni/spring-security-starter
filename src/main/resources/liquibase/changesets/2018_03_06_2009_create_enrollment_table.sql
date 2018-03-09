create table enrollment (
  student_id BIGINT NOT NULL REFERENCES students(id),
  course_id VARCHAR(10) NOT NULL REFERENCES courses(id),
  PRIMARY KEY (student_id, course_id)
);