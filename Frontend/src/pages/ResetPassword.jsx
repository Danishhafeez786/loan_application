import { useState } from "react";
import { Link } from "react-router-dom";
import AuthLayout from "../components/AuthLayout";

function ResetPassword() {
  const [otpSent, setOtpSent] = useState(false);

  return (
    <AuthLayout
      title="Reset Password"
      subtitle="Recover access to your account"
      footer={
        <p className="text-center mt-6">
          <Link
            to="/"
            className="text-green-600 font-semibold"
          >
            Back to Login
          </Link>
        </p>
      }
    >
      <form className="space-y-4">

        <input
          type="email"
          placeholder="Enter Email Address"
          className="w-full border rounded-xl px-4 py-3"
        />

        {!otpSent && (
          <button
            type="button"
            onClick={() => setOtpSent(true)}
            className="w-full bg-green-600 text-white py-3 rounded-xl"
          >
            Send OTP
          </button>
        )}

        {otpSent && (
          <>
            <input
              type="text"
              placeholder="Enter OTP"
              className="w-full border rounded-xl px-4 py-3"
            />

            <input
              type="password"
              placeholder="New Password"
              className="w-full border rounded-xl px-4 py-3"
            />

            <button
              type="button"
              className="w-full bg-green-600 text-white py-3 rounded-xl"
            >
              Reset Password
            </button>
          </>
        )}

      </form>
    </AuthLayout>
  );
}

export default ResetPassword;