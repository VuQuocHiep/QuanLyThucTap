const API_BASE_URL = 'http://localhost:8080'

const request = async (
  endpoint,
  { method = 'GET', body, headers = {}, ...options } = {}
) => {
  const token = localStorage.getItem('accessToken')

  const isFormData = body instanceof FormData
  const normalizedEndpoint = endpoint.startsWith('/')
    ? endpoint
    : `/${endpoint}`

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
    config.body =
      isFormData || typeof body === 'string'
        ? body
        : JSON.stringify(body)
  }

  const response = await fetch(
    `${API_BASE_URL}${normalizedEndpoint}`,
    config
  )

  const contentType = response.headers.get('content-type') || ''

  const responseBody = contentType.includes('application/json')
    ? await response.json().catch(() => null)
    : await response.text().catch(() => '')

  if (!response.ok) {
    console.log('URL:', `${API_BASE_URL}${normalizedEndpoint}`)
    console.log('Status:', response.status)
    console.log('Response:', responseBody)

    throw new Error(
      responseBody?.message ||
      responseBody?.error ||
      `Backend lỗi ${response.status}`
    )
  }

  return responseBody
}

export const api = {
  get: (endpoint, options) =>
    request(endpoint, {
      ...options,
      method: 'GET',
    }),

  post: (endpoint, body, options) =>
    request(endpoint, {
      ...options,
      method: 'POST',
      body,
    }),

  put: (endpoint, body, options) =>
    request(endpoint, {
      ...options,
      method: 'PUT',
      body,
    }),

  patch: (endpoint, body, options) =>
    request(endpoint, {
      ...options,
      method: 'PATCH',
      body,
    }),

  delete: (endpoint, options) =>
    request(endpoint, {
      ...options,
      method: 'DELETE',
    }),
}