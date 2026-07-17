export default function UpdateInformation({ user, onClose }) {
  return (
    <div>
      <p>
        Họ tên: {user?.firstname} {user?.lastname}
      </p>

      <p>Email: {user?.email}</p>

      <button onClick={onClose}>Đóng</button>
    </div>
  );
}