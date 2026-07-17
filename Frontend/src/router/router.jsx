import { Routes, Route, Navigate } from 'react-router-dom'
import LoginPage from '../layout/login/login'
import Admin from '../layout/admin/admin'
import Lecturer from "../layout/lecturer/lecturer"
import Student from "../layout/student/student"
function Router() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path='/admin' element={<Admin/>}/>
      <Route path='/lecturer' element={<Lecturer/>}/>
      <Route path='/student' element={<Student/>}/>
    </Routes>
  )
}

export default Router