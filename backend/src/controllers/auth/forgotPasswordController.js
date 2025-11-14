import User from "../../models/User.js";
import crypto from "crypto";
import nodemailer from "nodemailer";
import dotenv from "dotenv";

dotenv.config();

export const forgotPassword = async (req, res) => {
  try {
    const { email } = req.body;

    if (!email)
      return res.status(400).json({ message: "Thiếu email" });

    const user = await User.findOne({ email });
    if (!user)
      return res.status(404).json({ message: "Email không tồn tại" });

    const resetToken = crypto.randomBytes(32).toString("hex");

    user.resetPasswordToken = resetToken;
    user.resetPasswordExpires = Date.now() + 3600000; // 1h
    await user.save();

    // Gửi token thay vì gửi link
    const transporter = nodemailer.createTransport({
      service: "gmail",
      auth: {
        user: process.env.GMAIL_USER,
        pass: process.env.GMAIL_PASS,
      },
    });

    await transporter.sendMail({
      to: email,
      subject: "Mã đặt lại mật khẩu",
      html: `
        <h2>CareBooking - Đặt lại mật khẩu</h2>
        <p>Dưới đây là mã đặt lại mật khẩu của bạn (hiệu lực 1 giờ):</p>
        <h3 style="color:#2563EB">${resetToken}</h3>
        <p>Hãy mở ứng dụng CareBooking và nhập mã này để đặt lại mật khẩu.</p>
      `,
    });

    return res.json({
      message: "Đã gửi mã đặt lại mật khẩu!",
	  resetToken: resetToken,   // TRẢ VỀ TOKEN
  	  email: email
    });

  } catch (err) {
    console.error("FORGOT-PASSWORD ERROR:", err);
    res.status(500).json({ message: "Lỗi server" });
  }
};
