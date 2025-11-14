// backend\src\utils\sendEmail.js

import nodemailer from "nodemailer";
import dotenv from "dotenv";

dotenv.config();

export const sendVerificationEmail = async (to, token) => {
  const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
      user: process.env.GMAIL_USER,
      pass: process.env.GMAIL_PASS,
    },
  });

  const verifyUrl = `http://localhost:3000/api/users/verify/${token}`;

  const mailOptions = {
    from: "CareBooking vanket4949@gmail.com",
    to,
    subject: "Xác nhận tài khoản CareBooking",
    html: `
      <h2>Xác nhận tài khoản CareBooking</h2>
      <p>Nhấn vào liên kết bên dưới để xác nhận email:</p>
      <a href="${verifyUrl}">${verifyUrl}</a>
    `,
  };

  await transporter.sendMail(mailOptions);
};
