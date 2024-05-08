export type ClazzResponse = {
  id: number;
  clazzCode: string;
  courseName: string;
  teacherFullName: number;
};

type Students = {
  id: number;
  fullName: string;
};

export type ClazzResponseDetails = {
  id: number;
  clazzCode: string;
  courseName: string;
  courseId: number;
  teacherFullName: number;
  startDate: string;
  endDate: string;
  students: Students[];
};

export type ClazzResponseGrades = {
  id: number;
  clazzCode: string;
  courseName: string;
  teacherFullName: number;
  courseImageUrl: string;
  courseId: number;
};
