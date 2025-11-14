import User from "../../models/User.js";
import bcrypt from "bcryptjs";
import jwt from "jsonwebtoken";
import { sendVerificationEmail } from "../../utils/sendEmail.js";

import dotenv from "dotenv";

dotenv.config();

export const registerUser = async (req, res) => {
  try {
    const { fullName, email, phone, password } = req.body;

    if (!fullName || !email || !phone || !password) {
      return res.status(400).json({ message: "Thiếu thông tin người dùng" });
    }

    // Kiểm tra email tồn tại
    const existingUser = await User.findOne({ email });
    if (existingUser) {
      return res.status(400).json({ message: "Email đã được sử dụng" });
    }

    // Mã hóa mật khẩu
    const hashedPassword = await bcrypt.hash(password, 10);

    // Tạo user mới
    const newUser = new User({
      fullName,
      email,
      phone,
      password: hashedPassword,
      isVerified: false,
    });

    await newUser.save();

    // Token verify email
    const token = jwt.sign({ email }, process.env.JWT_SECRET, {
      expiresIn: "1d",
    });

    await sendVerificationEmail(email, token);

    return res.status(201).json({
      message: "Đăng ký thành công! Vui lòng xác nhận email.",
    });

  } catch (error) {
    console.error("REGISTER ERROR:", error);
    return res.status(500).json({ message: "Lỗi server", error: error.message });
  }
};
