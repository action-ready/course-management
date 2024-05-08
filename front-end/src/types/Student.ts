export type StudentViewImport = {
  fullName: string;
  studentCode: string;
};

export type StudentResponseGrades = {
  gradesId: number | null;
  studentId: number;
  fullName: string;
  studentCode: string;
  grade: number;
};

export type StudentResponse = {
  id: number;
  fullName: string;
  studentCode: string;
  courseName: string;
};

export type StudentCreate = {
  fullName: string;
  courseId: number | null;
  username: string;
};

export type StudentFilter = {
  studentCode: string;
  page: number;
  totalPage: number;
};

type Grades = {
  id: number;
  grade: number;
  clazzClazzCode: string;
  scheduleName: string;
  scheduleSemesterName: string;
};

export type StudentResponseDetails = {
  id: number;
  fullName: string;
  studentCode: string;
  courseName: string;
  grades: Grades[];
};
