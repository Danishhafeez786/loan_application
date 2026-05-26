import apiClient from "../api/apiClient";

export const signupUser = async (userData) => {
  try {
    const response = await apiClient.post("/auth/signup", userData);

    return response.data;
  } catch (error) {
    throw error.response?.data || "Signup failed";
  }
};

export const LoginUser = async (userData) => {
  try {
    const response = await apiClient.post("/auth/Login", userData);

    return response.data;
  } catch (error) {
    throw error.response?.data || "Login failed";
  }
};