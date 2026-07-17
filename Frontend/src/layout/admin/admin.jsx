import { Avatar, Button, Layout, Menu, Modal } from "antd";
import {
  BookOutlined,
  DashboardOutlined,
  FileTextOutlined,
  LockOutlined,
  LogoutOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  RobotOutlined,
  SafetyOutlined,
  SettingOutlined,
  TeamOutlined,
} from "@ant-design/icons";
import { useState } from "react";
import { Link, Outlet, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import avatarDefault from "../../assets/image.png";
import { clearUser } from "../../redux/userSlice";
import UpdateInformation from "../../components/UpdateInformation/UpdateInformation";
import "./admin.scss";

const { Header, Sider, Content } = Layout;

export default function Admin() {
  const [collapsed, setCollapsed] = useState(false);
  const [view, setView] = useState(false);
  const [info, setInfo] = useState(false);

  const { user } = useSelector((state) => state.user);

  const dispatch = useDispatch();
  const navigate = useNavigate();

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
      label: <Link to="/admin/overview">Tổng quan</Link>,
    },
    {
      key: "2",
      icon: <TeamOutlined />,
      label: <Link to="/admin/manageUser">QL Tài khoản</Link>,
    },
    {
      key: "3",
      icon: <SafetyOutlined />,
      label: <Link to="/admin/role">QL Quyền</Link>,
    },
    {
      key: "4",
      icon: <BookOutlined />,
      label: <Link to="/admin/manageInternship">QL Kì thực tập</Link>,
    },
    {
      key: "5",
      icon: <RobotOutlined />,
      label: <Link to="/admin/manageChatbot">Quản lý chatbot</Link>,
    },
    {
      key: "6",
      icon: <FileTextOutlined />,
      label: <Link to="/admin/managePost">QL Bài đăng</Link>,
    },
    {
      key: "7",
      icon: <SettingOutlined />,
      label: <Link to="/admin/settings">Cài đặt</Link>,
    },
  ];

  return (
    <div className="admin-page">
      <Layout style={{ minHeight: "100vh" }}>
        <Sider
          trigger={null}
          collapsible
          collapsed={collapsed}
          style={{ background: "#285b89" }}
        >
          <div
            style={{
              fontSize: collapsed ? "24px" : "40px",
              textAlign: "center",
              fontWeight: 600,
              color: "white",
              margin: "10px 0 20px",
            }}
          >
            {collapsed ? "AD" : "Admin"}
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
              background: "#ffffff",
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

            <div className="admin-user">
              <div
                className="admin-user-info"
                onClick={() => setView((previous) => !previous)}
              >
                <Avatar src={user?.image || avatarDefault} size={45}>
                  {user?.firstname?.charAt(0)?.toUpperCase()}
                </Avatar>

                <span>
                  {user
                    ? `${user.firstname || ""} ${user.lastname || ""}`
                    : "Admin"}
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

                  <div className="dropdown-item" onClick={handleLogout}>
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
}