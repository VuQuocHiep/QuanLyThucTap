import { Routes, Route, Navigate } from 'react-router-dom'
import LoginPage from '../layout/login/login'

function Router() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace />} />
      <Route path="/login" element={<LoginPage />} />
    </Routes>
  )
}

export default Router