import { api } from "./api";
export const registerLecturerApi = {
    createRegisterLecturer : (data)=>{
        return api.post("/registerLecturer/createRegisterLecturer",data);
    },
    countStudentOfLecturer : (userId,internshipId)=>{
        return api.get(`/registerLecturer/countStudentOfLecturer/${encodeURIComponent(userId)}/${encodeURIComponent(internshipId)}`)
    },
    getLecturerByStudentInternshiptopic : (userId,internshipId)=>{
        return api.get(`/registerLecturer/student/${encodeURIComponent(userId)}/internship/${encodeURIComponent(internshipId)}`);
    }
}
export const createRegisterLecturer=registerLecturerApi.createRegisterLecturer;
export const countStudentOfLecturer=registerLecturerApi.countStudentOfLecturer;
export const getLecturerByStudentInternshiptopic=registerLecturerApi.getLecturerByStudentInternshiptopic;