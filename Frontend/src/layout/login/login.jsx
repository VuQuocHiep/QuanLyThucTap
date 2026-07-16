import './login.scss'

import { useState } from 'react'
import { Row, Col, Form, Input, Button, App } from 'antd'
import {
  AppstoreOutlined,
  SmileOutlined,
  ThunderboltOutlined,
} from '@ant-design/icons'
import { Link, useNavigate } from 'react-router-dom'

import { api } from '../../api/api'

function LoginPage() {
  const navigate = useNavigate()
  const { message } = App.useApp()

  const [loading, setLoading] = useState(false)

  const handleLogin = async (values) => {
    try {
      setLoading(true)

      const response = await api.post('/auth/login', {
        email: values.email,
        password: values.password,
      })

      const accessToken =
        response.accessToken ||
        response.access_token ||
        response.token

      const refreshToken =
        response.refreshToken ||
        response.refresh_token

      if (!accessToken) {
        throw new Error('Backend không trả về access token')
      }

      localStorage.setItem('accessToken', accessToken)

      if (refreshToken) {
        localStorage.setItem('refreshToken', refreshToken)
      }

      // Tùy cấu trúc response backend
      const user = response.user || response.data?.user
      const roles = user?.roles || response.roles || []

      if (user) {
        localStorage.setItem('user', JSON.stringify(user))
      }

      message.success('Đăng nhập thành công')

      redirectByRole(roles)
    } catch (error) {
      message.error(error.message || 'Đăng nhập thất bại')
    } finally {
      setLoading(false)
    }
  }

  const redirectByRole = (roles) => {
    const roleNames = roles.map((role) =>
      typeof role === 'string'
        ? role.toUpperCase()
        : role.name?.toUpperCase()
    )

    if (roleNames.includes('ADMIN')) {
      navigate('/admin', { replace: true })
      return
    }

    if (roleNames.includes('LECTURER')) {
      navigate('/lecturer', { replace: true })
      return
    }

    if (roleNames.includes('STUDENT')) {
      navigate('/student', { replace: true })
      return
    }

    navigate('/home', { replace: true })
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
              Nền tảng số hoá toàn bộ quy trình đăng ký, phân công
              và theo dõi thực tập dành cho sinh viên và giảng viên
              hướng dẫn.
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
                    message: 'Email không đúng định dạng',
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