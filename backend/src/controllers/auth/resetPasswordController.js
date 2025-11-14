import User from "../../models/User.js";
import bcrypt from "bcryptjs";

export const resetPassword = async (req, res) => {
  try {
    const { token, newPassword } = req.body;

    // Validate đầu vào
    if (!token || !newPassword) {
      return res.status(400).json({ message: "Thiếu token hoặc mật khẩu mới" });
    }

    // Tìm user theo token còn hạn
    const user = await User.findOne({
      resetPasswordToken: token,
      resetPasswordExpires: { $gt: Date.now() }, // Token còn hiệu lực
    });

    if (!user) {
      return res.status(400).json({
        message: "Token không hợp lệ hoặc đã hết hạn",
      });
    }

    // Mã hóa mật khẩu mới
    const hashedPassword = await bcrypt.hash(newPassword, 10);

    // Lưu mật khẩu mới
    user.password = hashedPassword;

    // Xóa token sau khi reset thành công
    user.resetPasswordToken = undefined;
    user.resetPasswordExpires = undefined;

    await user.save();

    return res.status(200).json({
      message: "Đặt lại mật khẩu thành công!",
    });

  } catch (error) {
    console.error("RESET PASSWORD ERROR:", error);
    return res.status(500).json({
      message: "Lỗi server",
      error: error.message,
    });
  }
};
