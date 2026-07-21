import {api} from "./api";
export const userInternshipApi={
    getByUser : (userId)=>{
        return api.get(`/userInternship/getByUser/${encodeURIComponent(userId)}`);
    },
    getByInternship : (internshipId)=>{
        return api.get(`/userInternship/getByInternship/${encodeURIComponent(internshipId)}`)
    },
    getLecturerByInternship:(internshipId,userId)=>{
        return api.get(`/userInternship/getLecturerByInternship/${encodeURIComponent(internshipId)}/${encodeURIComponent(userId)}`)
    }
}
export const getByUser=userInternshipApi.getByUser;
export const getByInternship=userInternshipApi.getByInternship;
export const getLecturerByInternship=userInternshipApi.getLecturerByInternship;