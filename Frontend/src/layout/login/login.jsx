import './login.scss'

import { jwtDecode } from 'jwt-decode'
import { useState } from 'react'
import { useDispatch } from 'react-redux'
import { Row, Col, Form, Input, Button, App } from 'antd'
import {
  AppstoreOutlined,
  SmileOutlined,
  ThunderboltOutlined,
} from '@ant-design/icons'
import { Link, useNavigate } from 'react-router-dom'

import { login } from '../../api/auth'
import { getMyInfo } from '../../api/user'
import { setUser } from '../../redux/userSlice'

function LoginPage() {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const { message } = App.useApp()

  const [loading, setLoading] = useState(false)

  const getRolesFromToken = (token) => {
    try {
      const payload = jwtDecode(token)

      const scope = Array.isArray(payload.scope)
        ? payload.scope
        : String(payload.scope || '').split(' ')

      return scope
        .filter((item) => item.startsWith('ROLE_'))
        .map((item) => item.replace('ROLE_', ''))
    } catch (error) {
      console.error('JWT không hợp lệ:', error)
      return []
    }
  }

  const redirectByRole = (roles) => {
    const roleRoutes = {
      ADMIN: '/admin',
      LECTURER: '/lecturer',
      STUDENT: '/student',
    }

    const role = roles.find(
      (item) => roleRoutes[item]
    )

    navigate(roleRoutes[role] || '/home', {
      replace: true,
    })
  }

  const handleLogin = async (values) => {
    try {
      setLoading(true)

      // Xóa token cũ để tránh gửi token hỏng vào login
      localStorage.removeItem('accessToken')
      localStorage.removeItem('refreshToken')
      localStorage.removeItem('roles')

      // 1. Đăng nhập
      const data = await login({
        email: values.email.trim(),
        password: values.password,
      })

      const accessToken = data?.token
      const refreshToken = data?.refreshToken

      if (!accessToken) {
        throw new Error(
          'Backend không trả về access token'
        )
      }

      // 2. Lưu token
      localStorage.setItem(
        'accessToken',
        accessToken
      )

      if (refreshToken) {
        localStorage.setItem(
          'refreshToken',
          refreshToken
        )
      }

      // 3. Lấy role từ token
      const roles = getRolesFromToken(accessToken)

      localStorage.setItem(
        'roles',
        JSON.stringify(roles)
      )

      // 4. Gọi GET /user/me
      const userData = await getMyInfo()

      // 5. Lưu UserEntity vào Redux
      dispatch(setUser(userData))

      message.success('Đăng nhập thành công')

      // 6. Chuyển trang
      redirectByRole(roles)
    } catch (error) {
      console.error('Login error:', error)

      localStorage.removeItem('accessToken')
      localStorage.removeItem('refreshToken')
      localStorage.removeItem('roles')

      message.error(
        error?.message || 'Đăng nhập thất bại'
      )
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="login">
      <Row
        gutter={[80, 40]}
        align="middle"
        style={{
          minHeight: '100vh',
          background:
            'linear-gradient(135deg, #c8d5e8 0%, #c3d0db 60%, #f0e9e9 100%)',
          padding: '80px 10%',
        }}
      >
        <Col xs={24} lg={12}>
          <div className="login__left">
            <div className="login__left-year">
              Năm học 2025–2026
            </div>

            <div className="login__left-header">
              Hệ thống Quản lý Đăng ký Thực tập Sinh viên
            </div>

            <div className="login__left-content">
              Nền tảng số hoá toàn bộ quy trình đăng ký,
              phân công và theo dõi thực tập dành cho sinh
              viên và giảng viên hướng dẫn.
            </div>
          </div>

          <div className="login__left-experience">
            <div className="login__left-item">
              <AppstoreOutlined className="icon" />
              Nhiều tính năng
            </div>

            <div className="login__left-item">
              <SmileOutlined className="icon" />
              Trải nghiệm tốt
            </div>

            <div className="login__left-item">
              <ThunderboltOutlined className="icon" />
              Chat thời gian thực
            </div>
          </div>
        </Col>

        <Col xs={24} lg={12}>
          <div className="login__right">
            <div className="login__right-header">
              Đăng nhập
            </div>

            <Form
              layout="vertical"
              onFinish={handleLogin}
              autoComplete="off"
            >
              <Form.Item
                label="Email"
                name="email"
                rules={[
                  {
                    required: true,
                    message: 'Vui lòng nhập email',
                  },
                  {
                    type: 'email',
                    message:
                      'Email không đúng định dạng',
                  },
                ]}
              >
                <Input
                  size="large"
                  placeholder="nguyenvana@gmail.com"
                />
              </Form.Item>

              <Form.Item
                label="Mật khẩu"
                name="password"
                rules={[
                  {
                    required: true,
                    message: 'Vui lòng nhập mật khẩu',
                  },
                ]}
              >
                <Input.Password
                  size="large"
                  placeholder="Nhập mật khẩu"
                />
              </Form.Item>

              <div className="login__right-forget">
                <Link to="/auth/reset">
                  Quên mật khẩu
                </Link>
              </div>

              <Form.Item>
                <Button
                  type="primary"
                  htmlType="submit"
                  size="large"
                  loading={loading}
                  block
                >
                  Đăng nhập
                </Button>
              </Form.Item>
            </Form>

            <div className="login__right-account">
              Chưa có tài khoản? Liên hệ quản trị viên
            </div>
          </div>
        </Col>
      </Row>
    </div>
  )
}

export default LoginPage