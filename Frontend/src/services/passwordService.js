import apiClient from "../api/apiClient";

export const sendOtp = async (email) => {
  return await apiClient.post("/auth/send-otp", {
    email,
  });
};

export const resetPassword = async (data) => {
  return await apiClient.post("/auth/reset-password", data);
};