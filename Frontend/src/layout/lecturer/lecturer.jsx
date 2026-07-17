import React, { useState } from "react";
import {
  AuditOutlined,
  BookOutlined,
  CalendarOutlined,
  CheckCircleOutlined,
  DashboardOutlined,
  FileTextOutlined,
  LockOutlined,
  LogoutOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  MessageOutlined,
  SettingOutlined,
} from "@ant-design/icons";
import { Avatar, Button, Layout, Menu, Modal, theme } from "antd";
import { Link, Outlet, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import avatarDefault from "../../assets/image.png";
import UpdateInformation from "../../components/updateInformation/UpdateInformation";
import { clearUser } from "../../redux/userSlice";
import "./lecturer.scss";

const { Header, Sider, Content } = Layout;

const Lecturer = () => {
  const [collapsed, setCollapsed] = useState(false);
  const [view, setView] = useState(false);
  const [info, setInfo] = useState(false);

  const { user } = useSelector((state) => state.user);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");

    dispatch(clearUser());

    navigate("/login");
  };

  const menuItems = [
    {
      key: "1",
      icon: <DashboardOutlined />,
      label: <Link to="/lecturer">Tổng quan</Link>,
    },
    {
      key: "7",
      icon: <FileTextOutlined />,
      label: <Link to="/lecturer/viewPost">Bài đăng</Link>,
    },
    {
      key: "9",
      icon: <CheckCircleOutlined />,
      label: (
        <Link to="/lecturer/approveStudent">
          Duyệt sinh viên
        </Link>
      ),
    },
    {
      key: "2",
      icon: <BookOutlined />,
      label: <Link to="/lecturer/topic">Quản lý đề tài</Link>,
    },
    {
      key: "3",
      icon: <CheckCircleOutlined />,
      label: <Link to="/lecturer/approval">Duyệt đề tài</Link>,
    },
    {
      key: "4",
      icon: <CalendarOutlined />,
      label: <Link to="/lecturer/schedule">Thời gian biểu</Link>,
    },
    {
      key: "11",
      icon: <CalendarOutlined />,
      label: <Link to="/lecturer/attendance">Điểm danh</Link>,
    },
    {
      key: "10",
      icon: <AuditOutlined />,
      label: (
        <Link to="/lecturer/viewreport">
          Kiểm tra tiến độ
        </Link>
      ),
    },
    {
      key: "6",
      icon: <MessageOutlined />,
      label: <Link to="/lecturer/chat">Phòng chat</Link>,
    },
    {
      key: "8",
      icon: <SettingOutlined />,
      label: (
        <Link to="/lecturer/finalReport">
          Tổng kết điểm
        </Link>
      ),
    },
  ];

  return (
    <div className="lecturer">
      <Layout style={{ minHeight: "100vh" }}>
        <Sider
          trigger={null}
          collapsible
          collapsed={collapsed}
          style={{ background: "#0a687b" }}
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
            {collapsed ? "LT" : "LECTURER"}

            {!collapsed && (
              <div
                style={{
                  fontSize: 15,
                  color: "#c3d4eb",
                }}
              >
                Giảng viên
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
                collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />
              }
              onClick={() => setCollapsed((previous) => !previous)}
              style={{
                fontSize: 16,
                width: 64,
                height: 64,
              }}
            />

            <div className="lecturer-user">
              <div
                className="lecturer-user-info"
                onClick={() => setView((previous) => !previous)}
              >
                <Avatar src={user?.image || avatarDefault} size={45}>
                  {user?.firstname?.charAt(0)?.toUpperCase()}
                </Avatar>

                <span>
                  {user
                    ? `${user.firstname || ""} ${user.lastname || ""}`
                    : "Giảng viên"}
                </span>
              </div>

              {view && (
                <div
                  className="dropdown"
                  onClick={(event) => event.stopPropagation()}
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

export default Lecturer;