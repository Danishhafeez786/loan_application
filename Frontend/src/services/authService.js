import apiClient from "../api/apiClient";

export const signupUser = async (userData) => {
  try {
    const response = await apiClient.post("/auth/signup", userData);

    return response.data;
  } catch (error) {
    throw error.response?.data || "Signup failed";
  }
};

export const signinUser = async (userData) => {
  try {
    const response = await apiClient.post("/auth/signin", userData);

    return response.data;
  } catch (error) {
    throw error.response?.data || "Signin failed";
  }
};