export type TeacherCerate = {
  fullName: string;
  username: string;
};
type Clazz = {
  id: number;
};
export type TeacherResponses = {
  id: number;
  fullName: string;
  username: string;
  clazz: Clazz[];
  numberOfClazzes: number;
};

export type TeacherFilter = {
  page: number;
  totalPage: number;
};
