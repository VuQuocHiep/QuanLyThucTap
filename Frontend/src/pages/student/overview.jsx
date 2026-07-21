import React, { useEffect, useState } from 'react';
import { Card, Row, Col, Statistic, message, Button } from 'antd';
import { ReloadOutlined, BookOutlined, CheckCircleOutlined, ClockCircleOutlined, UserOutlined } from '@ant-design/icons';
import { useSelector } from 'react-redux';
import { getAllRegister,getByStudent } from '../../api/register';
import './overview.scss';
import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Legend,
} from "recharts";
function OverviewStudent() {
  const user = useSelector((state) => state.user);
  const [registerList,setRegisterList]=useState([]);
  const [registered, setRegistered] = useState(0);
  const [pending, setPending] = useState(0);
  const [approved, setApproved] = useState(0);
  const [loading, setLoading] = useState(false);
  const [userData, setUserData] = useState({
    totalTopic: 0,
    PENDING: 0,
    APPROVED: 0
  });
  const COLORS = ["#1890ff", "#52c41a", "#faad14", "#e71616"];
    const fetchData = async () => {
    console.log("fetchData chạy");

    setLoading(true);

    try {
        const response = await getByStudent(user.userId);

        console.log("response:", response);

        const list = response

        console.log("list:", list);

        const pendingCount = list.filter(
        (item) => item.status === "PENDING"
        ).length;

        const approvedCount = list.filter(
        (item) =>
            item.status === "APPROVED" ||
            item.status === "APPROVAL"
        ).length;

        setRegistered(list.length);
        setPending(pendingCount);
        setApproved(approvedCount);

        setUserData({
        totalTopic: list.length,
        Pending: pendingCount,
        Approved: approvedCount,
        });
    } catch (error) {
        console.error("Lỗi API:", error);
    } finally {
        setLoading(false);
    }
    };
  useEffect(() => {
    fetchData();
  }, [user]);
  const userPie = [
    { name: "TotalTopic", value: userData.totalTopic },
    { name: "Pending", value: userData.Pending },
    { name: "Approved", value: userData.Approved },
  ];
  return (
    <div className="student-overview">
      <div className="overview-header">
        <h1>Tổng Quan</h1>
        <Button icon={<ReloadOutlined />} onClick={fetchData} loading={loading}>
          Cập nhật
        </Button>
      </div>
      <Row gutter={16}>
        <Col span={8}>
          <Card>
            <Statistic title="Đề tài đã đăng ký" value={registered} prefix={<BookOutlined />} />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic title="Chờ duyệt" value={pending} prefix={<ClockCircleOutlined />} />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic title="Đã duyệt" value={approved} prefix={<CheckCircleOutlined />} />
          </Card>
        </Col>
      </Row>
      <Row gutter={16} style={{marginTop:20}}>
        <Col span={12}>
          <Card title="Topic Status Distribution">
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie
                  data={userPie}
                  dataKey="value"
                  nameKey="name"
                  outerRadius={100}
                  label
                >
                  {userPie.map((_, i) => (
                    <Cell key={i} fill={COLORS[i]} />
                  ))}
                </Pie>
                <Tooltip />
              </PieChart>
            </ResponsiveContainer>
          </Card>
        </Col>
        <Col span={12}>
          <Card title="Registration Overview">
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={userPie}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="value" fill="#1890ff" />
              </BarChart>
            </ResponsiveContainer>
          </Card>
        </Col>
      </Row>
    </div>
  );
}
export default OverviewStudent;