import { Button, Card, Col, Row, Typography, App } from 'antd'
import { useNavigate } from 'react-router-dom'
import './admin.scss'

const { Title, Paragraph } = Typography

export default function Admin() {
  const navigate = useNavigate()
  const { message } = App.useApp()

  const handleLogout = () => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('token')
    localStorage.removeItem('user')

    message.success('Đăng xuất thành công')
    navigate('/login', { replace: true })
  }

  return (
    <div className="admin-page">
      <Row
        justify="center"
        align="middle"
        className="admin-page__wrapper"
      >
        <Col xs={22} sm={20} md={16} lg={12}>
          <Card className="admin-card" bordered={false}>
            <Title level={2}>Trang quản trị</Title>

            <Paragraph>
              Chào mừng bạn đến với trang quản trị. Tại đây bạn có
              thể xem tổng quan hệ thống, quản lý người dùng và cấu
              hình quyền truy cập.
            </Paragraph>

            <div className="admin-card__actions">
              <Button type="primary" danger onClick={handleLogout}>
                Đăng xuất
              </Button>
            </div>
          </Card>
        </Col>
      </Row>
    </div>
  )
}