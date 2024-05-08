export type CourseRequest = {
  name: string;
  language: string;
  price: number;
  description: string;
  imageUrl: string;
};

export type CourseResponse = {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
  imageUrl: string;
  price: number;
  description: string;
  language: string;
  studyTime: string;
};

export type Schedules = {
  id: string;
  name: string;
  description: string;
  startDate: string;
  endDate: string;
  numberOfSessions: number;
};

type Semesters = {
  id: string;
  name: string;
  description: string;
  schedules: Schedules[];
};

export type CourseResponseDetails = {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
  price: number;
  description: string;
  language: string;
  semesters: Semesters[];
};
export type CourseResponseSelect = {
  id: number;
  name: string;
};
