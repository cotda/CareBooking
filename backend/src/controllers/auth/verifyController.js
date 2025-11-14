import User from "../../models/User.js";
import jwt from "jsonwebtoken";
import dotenv from "dotenv";

dotenv.config();

export const verifyEmail = async (req, res) => {
  try {
    const { token } = req.params;

    const decoded = jwt.verify(token, process.env.JWT_SECRET);

    const user = await User.findOne({ email: decoded.email });
    if (!user)
      return res.status(404).json({ message: "Người dùng không tồn tại" });

    user.isVerified = true;
    await user.save();

    res.send("<h3>Xác nhận email thành công! Bạn có thể đăng nhập.</h3>");

  } catch (error) {
    return res.status(400).json({
      message: "Liên kết xác nhận không hợp lệ hoặc đã hết hạn",
    });
  }
};
