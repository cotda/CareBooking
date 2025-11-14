import express from "express";
import { registerUser } from "../controllers/auth/registerController.js";
import { loginUser } from "../controllers/auth/loginController.js";
import { verifyEmail } from "../controllers/auth/verifyController.js";
import { forgotPassword } from "../controllers/auth/forgotPasswordController.js";
import { resetPassword } from "../controllers/auth/resetPasswordController.js";


const router = express.Router();

// Register
router.post("/register", registerUser);

// Login
router.post("/login", loginUser);

// Verify Email
router.get("/verify/:token", verifyEmail);

// Reset password
router.post("/forgot-password", forgotPassword);
router.post("/reset-password", resetPassword);

// Test API
router.get("/test", (req, res) => {
  res.json({ message: "User API OK" });
});

export default router;
