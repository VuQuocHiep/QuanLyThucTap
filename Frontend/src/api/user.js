import { api } from './api'

const USER_URL = '/user'

export const userApi = {
  createUser: (userData) => {
    return api.post(`${USER_URL}/createUser`, userData)
  },

  getAllUser: () => {
    return api.get(`${USER_URL}/getAllUser`)
  },

  getUserByEmail: (email) => {
    return api.get(
      `${USER_URL}/getUserByEmail/${encodeURIComponent(email)}`
    )
  },

  updateUser: (email, userData) => {
    return api.patch(
      `${USER_URL}/updateUser/${encodeURIComponent(email)}`,
      userData
    )
  },

  deleteUser: (email) => {
    return api.post(
      `${USER_URL}/deleteUser/${encodeURIComponent(email)}`
    )
  },
}