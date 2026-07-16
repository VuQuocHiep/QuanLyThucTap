const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL || '/api').replace(/\/$/, '')

const request = async (endpoint, { method = 'GET', body, headers = {}, ...options } = {}) => {
  const token = localStorage.getItem('accessToken') || localStorage.getItem('token')
  const isFormData = body instanceof FormData
  const normalizedEndpoint = endpoint.startsWith('/') ? endpoint : `/${endpoint}`

  const config = {
    method,
    headers: {
      ...(isFormData ? {} : { 'Content-Type': 'application/json' }),
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...headers,
    },
    ...options,
  }

  if (body !== undefined && body !== null) {
    config.body = isFormData || typeof body === 'string' ? body : JSON.stringify(body)
  }

  const response = await fetch(`${API_BASE_URL}${normalizedEndpoint}`, config)

  if (!response.ok) {
    const contentType = response.headers.get('content-type') || ''
    const errorBody = contentType.includes('application/json')
      ? await response.json().catch(() => null)
      : await response.text().catch(() => '')

    const errorMessage =
      errorBody?.message ||
      errorBody?.error ||
      (typeof errorBody === 'string' ? errorBody : '') ||
      'Yeu cau toi backend that bai'

    throw new Error(errorMessage)
  }

  const contentType = response.headers.get('content-type') || ''
  if (contentType.includes('application/json')) {
    return response.json()
  }

  return response.text()
}

export const api = {
  get: (endpoint, options) => request(endpoint, { ...options, method: 'GET' }),
  post: (endpoint, body, options) => request(endpoint, { ...options, method: 'POST', body }),
  put: (endpoint, body, options) => request(endpoint, { ...options, method: 'PUT', body }),
  patch: (endpoint, body, options) => request(endpoint, { ...options, method: 'PATCH', body }),
  delete: (endpoint, options) => request(endpoint, { ...options, method: 'DELETE' }),
}
