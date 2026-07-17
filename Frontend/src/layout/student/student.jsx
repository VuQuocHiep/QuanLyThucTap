import React, { useState } from "react";
import {
  BarChartOutlined,
  BookOutlined,
  CalendarOutlined,
  DashboardOutlined,
  FileTextOutlined,
  LockOutlined,
  LogoutOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  MessageOutlined,
  SettingOutlined,
  TeamOutlined,
  TrophyOutlined,
} from "@ant-design/icons";
import { Avatar, Button, Layout, Menu, Modal, theme } from "antd";
import { Link, Outlet, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import avatarDefault from "../../assets/image.png";
import UpdateInformation from "../../components/updateInformation/UpdateInformation";
import { clearUser } from "../../redux/userSlice";
import "./student.scss";

const { Header, Sider, Content } = Layout;

const Student = () => {
  const [collapsed, setCollapsed] = useState(false);
  const [info, setInfo] = useState(false);
  const [view, setView] = useState(false);

  const { user } = useSelector((state) => state.user);

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");

    dispatch(clearUser());

    setView(false);
    setInfo(false);

    navigate("/login");
  };

  const menuItems = [
    {
      key: "1",
      icon: <DashboardOutlined />,
      label: <Link to="/student">Tổng quan</Link>,
    },
    {
      key: "2",
      icon: <FileTextOutlined />,
      label: <Link to="/student/viewPost">Bài đăng</Link>,
    },
    {
      key: "3",
      icon: <TeamOutlined />,
      label: <Link to="/student/teacher">Đăng ký giảng viên</Link>,
    },
    {
      key: "4",
      icon: <BookOutlined />,
      label: <Link to="/student/register">Đăng ký đề tài</Link>,
    },
    {
      key: "5",
      icon: <CalendarOutlined />,
      label: <Link to="/student/schedule">Lịch báo cáo</Link>,
    },
    {
      key: "6",
      icon: <BarChartOutlined />,
      label: (
        <Link to="/student/reportProgress">
          Báo cáo tiến độ
        </Link>
      ),
    },
    {
      key: "7",
      icon: <SettingOutlined />,
      label: (
        <Link to="/student/attendanceStudent">
          Điểm danh
        </Link>
      ),
    },
    {
      key: "8",
      icon: <MessageOutlined />,
      label: <Link to="/student/chat">Phòng chat</Link>,
    },
    {
      key: "9",
      icon: <TrophyOutlined />,
      label: (
        <Link to="/student/finalReport">
          Tổng kết điểm
        </Link>
      ),
    },
  ];

  return (
    <div className="student">
      <Layout style={{ minHeight: "100vh" }}>
        <Sider
          trigger={null}
          collapsible
          collapsed={collapsed}
          style={{ background: "#285b89" }}
        >
          <div
            style={{
              fontSize: collapsed ? "22px" : "30px",
              textAlign: "center",
              fontWeight: 600,
              color: "white",
              margin: "10px 0 20px",
              padding: 5,
            }}
          >
            {collapsed ? "ST" : "STUDENT"}

            {!collapsed && (
              <div
                style={{
                  fontSize: 15,
                  color: "#c3d4eb",
                }}
              >
                Sinh viên
              </div>
            )}
          </div>

          <Menu
            theme="dark"
            mode="inline"
            defaultSelectedKeys={["1"]}
            items={menuItems}
            style={{ background: "transparent" }}
          />
        </Sider>

        <Layout>
          <Header
            style={{
              padding: "0 20px",
              height: 64,
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              background: colorBgContainer,
            }}
          >
            <Button
              type="text"
              icon={
                collapsed
                  ? <MenuUnfoldOutlined />
                  : <MenuFoldOutlined />
              }
              onClick={() => {
                setCollapsed((previous) => !previous);
              }}
              style={{
                fontSize: 16,
                width: 64,
                height: 64,
              }}
            />

            <div className="student-user">
              <div
                className="student-user-info"
                onClick={() => {
                  setView((previous) => !previous);
                }}
              >
                <Avatar
                  src={user?.image || avatarDefault}
                  size={45}
                >
                  {user?.firstname
                    ?.charAt(0)
                    ?.toUpperCase()}
                </Avatar>

                <span>
                  {user
                    ? `${user.firstname || ""} ${
                        user.lastname || ""
                      }`
                    : "Sinh viên"}
                </span>
              </div>

              {view && (
                <div
                  className="dropdown"
                  onClick={(event) => {
                    event.stopPropagation();
                  }}
                >
                  <div
                    className="dropdown-item"
                    onClick={() => {
                      setInfo(true);
                      setView(false);
                    }}
                  >
                    <LockOutlined />
                    <span>Thông tin</span>
                  </div>

                  <div
                    className="dropdown-item"
                    onClick={handleLogout}
                  >
                    <LogoutOutlined />
                    <span>Đăng xuất</span>
                  </div>
                </div>
              )}
            </div>
          </Header>

          <Content
            style={{
              padding: 24,
              height: "calc(100vh - 64px)",
              background: colorBgContainer,
              borderRadius: borderRadiusLG,
              boxSizing: "border-box",
              overflow: "auto",
            }}
          >
            <Outlet />
          </Content>
        </Layout>
      </Layout>

      <Modal
        title="Thông tin cá nhân"
        open={info}
        onCancel={() => setInfo(false)}
        footer={null}
        zIndex={9999}
        destroyOnHidden
      >
        <UpdateInformation
          user={user}
          onClose={() => setInfo(false)}
        />
      </Modal>
    </div>
  );
};

export default Student;