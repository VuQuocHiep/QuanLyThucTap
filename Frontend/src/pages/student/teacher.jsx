import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import {
  AppstoreOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import {
  Button,
  Card,
  Col,
  Empty,
  message,
  Row,
  Select,
  Table,
  Tag,
} from "antd";
import moment from "moment";

import {
  getByUser,
  getByInternship,
} from "../../api/userInternship";

import {
  createRegisterLecturer,
  countStudentOfLecturer,
  getLecturerByStudentInternshiptopic,
} from "../../api/registerLecturer";

export default function Teacher() {
  const { user } = useSelector((state) => state.user);

  const [view, setView] = useState("table");
  const [lecturers, setLecturers] = useState([]);
  const [internships, setInternships] = useState([]);
  const [selectedInternship, setSelectedInternship] = useState(null);
  const [registrations, setRegistrations] = useState([]);
  const [studentCounts, setStudentCounts] = useState({});
  const [loading, setLoading] = useState(false);
  const [registeringId, setRegisteringId] = useState(null);

  const [registerTime, setRegisterTime] = useState({
    open: null,
    close: null,
  });

  const getResponseData = (response) => {
    if (response?.data?.message !== undefined) {
      return response.data.message;
    }

    if (response?.message !== undefined) {
      return response.message;
    }

    if (response?.data !== undefined) {
      return response.data;
    }

    return response;
  };

  const getRegisterStatus = () => {
    if (
      !selectedInternship ||
      !registerTime.open ||
      !registerTime.close
    ) {
      return "NO_INTERNSHIP";
    }

    const now = new Date();
    const open = new Date(`${registerTime.open}T00:00:00`);
    const close = new Date(`${registerTime.close}T23:59:59`);

    if (now < open) {
      return "NOT_OPEN";
    }

    if (now > close) {
      return "CLOSED";
    }

    return "OPEN";
  };

  const registerStatus = getRegisterStatus();

  const fetchInternships = async () => {
    if (!user?.userId) {
      return;
    }

    try {
      const response = await getByUser(user.userId);
      const data = getResponseData(response);
      const list = Array.isArray(data) ? data : [];

      setInternships(list);

      if (list.length === 0) {
        setSelectedInternship(null);
        setRegisterTime({
          open: null,
          close: null,
        });
        return;
      }

      const firstInternship = list[0]?.internshipEntity;

      setSelectedInternship(
        firstInternship?.internshipId ?? null,
      );

      setRegisterTime({
        open: firstInternship?.registerOpenDate ?? null,
        close: firstInternship?.registerCloseDate ?? null,
      });
    } catch (error) {
      console.error(error);

      message.error(
        error.response?.data?.message ||
          "Không lấy được danh sách đợt thực tập",
      );
    }
  };

  const fetchLecturers = async () => {
    if (!selectedInternship) {
      setLecturers([]);
      return;
    }

    try {
      setLoading(true);

      const response = await getByInternship(
        selectedInternship,
      );

      const data = getResponseData(response);
      const list = Array.isArray(data) ? data : [];

      const lecturerList = list
        .filter(
          (item) =>
            item?.type === "LECTURER" &&
            item?.userEntity,
        )
        .map((item) => ({
          ...item.userEntity,
          maxStudent: item.maxStudent,
          userInternshipId: item.userInternshipId,
        }));

      setLecturers(lecturerList);
    } catch (error) {
      console.error(error);

      setLecturers([]);

      message.error(
        error.response?.data?.message ||
          "Không lấy được danh sách giảng viên",
      );
    } finally {
      setLoading(false);
    }
  };

  const fetchRegistrations = async () => {
    if (!user?.userId || !selectedInternship) {
      setRegistrations([]);
      return;
    }

    try {
      const response =
        await getLecturerByStudentInternshiptopic(
          user.userId,
          selectedInternship);

      const data = getResponseData(response);

      setRegistrations(
        Array.isArray(data) ? data : data ? [data] : [],
      );
    } catch (error) {
      console.error(error);
      setRegistrations([]);
    }
  };

  const fetchAllCounts = async () => {
    if (!selectedInternship || lecturers.length === 0) {
      setStudentCounts({});
      return;
    }

    try {
      const countEntries = await Promise.all(
        lecturers.map(async (lecturer) => {
          try {
            const response = await countStudentOfLecturer(
            lecturer.userId,
            selectedInternship,
            );

            const data = getResponseData(response);
            const count = Number(data) || 0;

            return [lecturer.userId, count];
          } catch (error) {
            console.error(error);
            return [lecturer.userId, 0];
          }
        }),
      );

      setStudentCounts(Object.fromEntries(countEntries));
    } catch (error) {
      console.error(error);

      message.error(
        error.response?.data?.message ||
          "Không lấy được số lượng sinh viên",
      );
    }
  };

  const handleChangeInternship = (internshipId) => {
    setSelectedInternship(internshipId);

    const selectedItem = internships.find(
      (item) =>
        item?.internshipEntity?.internshipId ===
        internshipId,
    );

    setRegisterTime({
      open:
        selectedItem?.internshipEntity
          ?.registerOpenDate ?? null,
      close:
        selectedItem?.internshipEntity
          ?.registerCloseDate ?? null,
    });
  };

  const getLecturerIdFromRegistration = (
    registration,
  ) => {
    return (
      registration?.lecturer?.userId ??
      registration?.lecturerEntity?.userId ??
      registration?.lecturerId ??
      null
    );
  };

  const getCurrentRegistration = (lecturerId) => {
    return registrations.find(
      (item) =>
        getLecturerIdFromRegistration(item) ===
        lecturerId,
    );
  };

  const handleRegister = async (lecturerId) => {
    if (!user?.userId || !selectedInternship) {
      message.error("Thiếu thông tin đăng ký");
      return;
    }

    try {
      setRegisteringId(lecturerId);

      await createRegisterLecturer({
        studentId: user.userId,
        lecturerId,
        internshipId: selectedInternship,
      });

      await Promise.all([
        fetchRegistrations(),
        fetchAllCounts(),
      ]);

      message.success("Đăng ký giảng viên thành công");
    } catch (error) {
      console.error(error);

      message.error(
        error.response?.data?.message ||
          "Đăng ký giảng viên thất bại",
      );
    } finally {
      setRegisteringId(null);
    }
  };

  useEffect(() => {
    fetchInternships();
  }, [user?.userId]);

  useEffect(() => {
    if (!selectedInternship) {
      setLecturers([]);
      setRegistrations([]);
      setStudentCounts({});
      return;
    }

    fetchLecturers();
    fetchRegistrations();
  }, [selectedInternship, user?.userId]);

  useEffect(() => {
    fetchAllCounts();
  }, [lecturers, selectedInternship]);

  const renderRegisterButton = (record) => {
    const currentRegistration =
      getCurrentRegistration(record.userId);

    const status =
      currentRegistration?.status ?? "NONE";

    const count =
      studentCounts[record.userId] ?? 0;

    const maxStudent = record.maxStudent ?? 0;

    let text = "Đăng ký";
    let buttonType = "primary";
    let danger = false;
    let disabled = registerStatus !== "OPEN";

    if (status === "PENDING") {
      text = "Chờ duyệt";
      buttonType = "default";
      disabled = true;
    } else if (status === "APPROVAL") {
      text = "Đã được duyệt";
      buttonType = "default";
      disabled = true;
    } else if (status === "REJECT") {
      text = "Bị từ chối";
      buttonType = "default";
      danger = true;
      disabled = true;
    }

    if (
      status === "NONE" &&
      maxStudent > 0 &&
      count >= maxStudent
    ) {
      text = "Đã đủ sinh viên";
      buttonType = "default";
      danger = true;
      disabled = true;
    }

    return (
      <Button
        type={buttonType}
        danger={danger}
        disabled={disabled}
        loading={registeringId === record.userId}
        onClick={() => handleRegister(record.userId)}
      >
        {text}
      </Button>
    );
  };

  const columns = [
    {
      title: "Tên giảng viên",
      key: "fullname",
      render: (_, record) => (
        <b>
          {record.firstname} {record.lastname}
        </b>
      ),
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Học vị",
      key: "degree",
      render: (_, record) =>
        record.lecturer?.degree || "N/A",
    },
    {
      title: "Khoa",
      key: "department",
      render: (_, record) =>
        record.lecturer?.department || "N/A",
    },
    {
      title: "Kinh nghiệm",
      key: "experience",
      render: (_, record) =>
        record.lecturer?.experience || "N/A",
    },
    {
      title: "Sinh viên",
      key: "students",
      render: (_, record) => (
        <>
          <Tag color="green">
            {studentCounts[record.userId] ?? 0}
          </Tag>

          <span>/</span>

          <Tag color="red">
            {record.maxStudent ?? 0}
          </Tag>
        </>
      ),
    },
    {
      title: "Thao tác",
      key: "action",
      render: (_, record) =>
        renderRegisterButton(record),
    },
  ];

  return (
    <div style={{ padding: 20 }}>
      <div
        style={{
          marginBottom: 20,
          display: "flex",
          alignItems: "center",
          gap: 8,
        }}
      >
        <Button
          type={view === "grid" ? "primary" : "default"}
          onClick={() => setView("grid")}
          icon={<AppstoreOutlined />}
        />

        <Button
          type={
            view === "table" ? "primary" : "default"
          }
          onClick={() => setView("table")}
          icon={<UnorderedListOutlined />}
        />

        <Select
          value={selectedInternship}
          placeholder="Chọn đợt thực tập"
          onChange={handleChangeInternship}
          options={internships
            .filter(
              (item) => item?.internshipEntity,
            )
            .map((item) => ({
              value:
                item.internshipEntity.internshipId,
              label: item.internshipEntity.name,
            }))}
          style={{
            width: 350,
            marginLeft: "auto",
          }}
        />
      </div>

      {registerStatus !== "OPEN" && (
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            marginBottom: 15,
          }}
        >
          {registerStatus === "NO_INTERNSHIP" && (
            <div
              style={{
                fontSize: 18,
                padding: "8px 20px",
              }}
            >
              Chưa có đợt đăng ký
            </div>
          )}

          {registerStatus === "NOT_OPEN" && (
            <div
              style={{
                fontSize: 18,
                padding: "8px 20px",
              }}
            >
              Chưa đến thời gian đăng ký giảng viên
            </div>
          )}

          {registerStatus === "CLOSED" && (
            <div
              style={{
                fontSize: 18,
                padding: "8px 20px",
              }}
            >
              Đã hết thời gian đăng ký giảng viên
            </div>
          )}
        </div>
      )}

      {registerTime.open && registerTime.close && (
        <div
          style={{
            display: "flex",
            gap: 20,
            justifyContent: "center",
            marginBottom: 20,
          }}
        >
          <div>
            Mở đăng ký:{" "}
            {moment(registerTime.open).format(
              "DD/MM/YYYY",
            )}
          </div>

          <div>
            Đóng đăng ký:{" "}
            {moment(registerTime.close).format(
              "DD/MM/YYYY",
            )}
          </div>
        </div>
      )}

      {view === "table" && (
        <Table
          loading={loading}
          columns={columns}
          dataSource={lecturers}
          rowKey="userId"
          pagination={{
            pageSize: 5,
          }}
        />
      )}

      {view === "grid" && (
        <>
          {loading ? (
            <div style={{ textAlign: "center" }}>
              Đang tải...
            </div>
          ) : lecturers.length === 0 ? (
            <Empty description="Không có giảng viên" />
          ) : (
            <Row gutter={[16, 16]}>
              {lecturers.map((lecturer) => (
                <Col
                  xs={24}
                  sm={12}
                  md={8}
                  lg={6}
                  key={lecturer.userId}
                >
                  <Card
                    title={`${lecturer.firstname ?? ""} ${
                      lecturer.lastname ?? ""
                    }`}
                  >
                    <p>
                      <b>Email:</b>{" "}
                      {lecturer.email || "N/A"}
                    </p>

                    <p>
                      <b>Học vị:</b>{" "}
                      {lecturer.lecturer?.degree ||
                        "N/A"}
                    </p>

                    <p>
                      <b>Khoa:</b>{" "}
                      {lecturer.lecturer?.department ||
                        "N/A"}
                    </p>

                    <p>
                      <b>Kinh nghiệm:</b>{" "}
                      {lecturer.lecturer?.experience ||
                        "N/A"}
                    </p>

                    <p>
                      <b>Sinh viên:</b>{" "}
                      <Tag color="green">
                        {studentCounts[
                          lecturer.userId
                        ] ?? 0}
                      </Tag>
                      /
                      <Tag color="red">
                        {lecturer.maxStudent ?? 0}
                      </Tag>
                    </p>

                    {renderRegisterButton(lecturer)}
                  </Card>
                </Col>
              ))}
            </Row>
          )}
        </>
      )}
    </div>
  );
}