import { Routes, Route, Navigate } from 'react-router-dom'
import LoginPage from '../layout/login/login'
import Admin from '../layout/admin/admin'
function Router() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path='/admin' element={<Admin/>}/>
    </Routes>
  )
}

export default Router