# 🏥 CareBooking Backend (Node.js + Express + MongoDB)

Backend API cho ứng dụng đặt lịch khám bệnh **CareBooking**, được xây dựng bằng **Node.js + Express**, sử dụng **MongoDB** để lưu trữ dữ liệu người dùng, lịch đặt và các thông tin liên quan.

---

## 🚀 Công nghệ sử dụng
- **Node.js**
- **Express.js**
- **MongoDB + Mongoose**
- **JWT Authentication**
- **Bcryptjs** (mã hóa mật khẩu)
- **Nodemailer** (gửi email)
- **Dotenv** (quản lý biến môi trường)
- **Nodemon** (reload nhanh khi dev)

---

## 📦 Cài đặt & Chạy Dự Án

### 1️⃣ Clone dự án
```bash
git clone https://github.com/<your-repository>/carebooking-backend.git

### 2️⃣ Cài đặt phụ thuộc
npm install

### 3 Tạo file cấu hình .env

### 4️⃣ Chạy Backend
cd backend -> npm run dev


⚠️ LƯU Ý KHI CLONE VỀ MÁY KHÁC — PHẢI ĐỔI PORT
cmd -> ipconfig
Wireless LAN adapter Wi-Fi:

   Connection-specific DNS Suffix  . : 
   Link-local IPv6 Address . . . . . : ***
   IPv4 Address. . . . . . . . . . . : port // Lấy port này thay vào BASE_URL=http://port:3000
   Subnet Mask . . . . . . . . . . . : ***
   Default Gateway . . . . . . . . . : ***

### Đổi port trong file app\api.properties (nếu dùng emulator thì dùng port 10.0.2.2)