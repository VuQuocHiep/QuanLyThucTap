import { api } from "./api";
const REGISTER_URL = "/register";

export const registerApi = {
    getByStudent : (userId)=>{
        return api.get(`${REGISTER_URL}/getByStudent/${encodeURIComponent(userId)}`);
    },
    getAllRegister : ()=>{
        return api.get(`${REGISTER_URL}/getAllRegister`);
    }
}
export const  getByStudent=registerApi.getByStudent
export const  getAllRegister=registerApi.getAllRegister