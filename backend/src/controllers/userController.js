import User from "../models/User.js";

export const registerUser = async (req, res) => {
  try {
    const { fullName, email, phone, password } = req.body;

    if (!fullName || !email || !phone || !password) {
      return res.status(400).json({ message: "Thiếu thông tin người dùng" });
    }

    const newUser = new User({ fullName, email, phone, password });
    await newUser.save();

    res.status(201).json({ message: "Đăng ký thành công!" });
  } catch (error) {
    res.status(500).json({ message: "Lỗi server", error: error.message });
  }
};
