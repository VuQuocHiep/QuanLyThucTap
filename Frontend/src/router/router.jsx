import { Routes, Route, Navigate } from "react-router-dom";

import LoginPage from "../layout/login/login";
import Admin from "../layout/admin/admin";
import Lecturer from "../layout/lecturer/lecturer";
import Student from "../layout/student/student";
import OverviewStudent from "../pages/student/overview";
import OverviewLecturer from "../pages/lecturer/overview";
import Teacher from "../pages/student/teacher";
function Router() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/admin" element={<Admin />} />
      <Route path="/lecturer" element={<Lecturer />} />
      <Route path="/student" element={<Student />}>
        <Route index element={<Navigate to="overview" replace />} />
        <Route path="overview" element={<OverviewStudent />} />
        <Route path="teacher" element={<Teacher/>}/>
      </Route>
      <Route path="/lecturer" element={<Lecturer/>}>
        <Route index element={<Navigate to="overview" replace />} />
        <Route path="overview" element={<OverviewLecturer />} />
      </Route>
    </Routes>
  );
}

export default Router;