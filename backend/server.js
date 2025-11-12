// server.js
import express from "express";
import cors from "cors";
import mongoose from "mongoose";
import userRoutes from "./src/routes/userRoutes.js";

const app = express();

// Middleware
app.use(cors());
app.use(express.json());

// Kết nối MongoDB
mongoose.connect("mongodb://localhost:27017/carebooking")
  .then(() => console.log("MongoDB connected"))
  .catch(err => console.error("MongoDB error:", err));

// Routes
app.use("/api/users", userRoutes);

// Start server
const PORT = 3000;
app.listen(PORT, () => console.log(`Server running on http://localhost:${PORT}`));
