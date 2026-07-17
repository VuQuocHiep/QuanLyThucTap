import { api } from "./api";
const USER_URL = "/role";

export const roleApi = {
    createRole : (roleData) => {
        return api.post(`${USER_URL}/createRole`,roleData);
    },
    getAllRole : () => {
        return api.get(`${USER_URL}/getAllRole`);
    },
    getRoleByName : (name) => {
        return api.get(`${USER_URL}/getRoleByName/${encodeURIComponent(name)}`);
    },
    deleteRole : (name) => {
        return api.delete(`${USER_URL}/deleteRole/${encodeURIComponent(name)}`);
    },
    updateRole : (name,roleData) => {
        return api.patch(`${USER_URL}/updateRole/${encodeURIComponent(name)}`,roleData);
    }
}