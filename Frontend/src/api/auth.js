import { api } from './api'

const AUTH_URL = '/auth'

export const authApi = {
  login: (authData) => api.post(`${AUTH_URL}/login`, authData),

  introspect: (token) =>
    api.post(`${AUTH_URL}/introspect`, {
      token,
    }),

  refresh: (refreshToken) =>
    api.post(`${AUTH_URL}/refresh`, {
      refreshToken,
    }),
}

export const login = authApi.login
export const introspect = authApi.introspect
export const refreshToken = authApi.refresh