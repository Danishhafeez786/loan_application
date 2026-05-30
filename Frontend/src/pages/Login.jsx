import { Link } from "react-router-dom";
import AuthLayout from "../components/AuthLayout";

function Login() {
  return (
    <AuthLayout
      title="Customer Login"
      subtitle="Access your account securely"
      footer={
        <p className="text-center mt-6 text-gray-600">
          New here?{" "}
          <Link
            to="/signup"
            className="text-green-600 font-semibold"
          >
            Register Now
          </Link>
        </p>
      }
    >
      <form className="space-y-4">

        <input
          type="email"
          placeholder="Email Address"
          className="w-full border rounded-xl px-4 py-3 focus:ring-2 focus:ring-green-500"
        />

        <input
          type="password"
          placeholder="Password"
          className="w-full border rounded-xl px-4 py-3 focus:ring-2 focus:ring-green-500"
        />

        <div className="flex justify-between text-sm">

          <label className="flex items-center gap-2">
            <input type="checkbox" />
            Remember me
          </label>

          <Link
            to="/reset-password"
            className="text-green-600"
          >
            Forgot Password?
          </Link>

        </div>

        <button
          className="w-full bg-green-600 hover:bg-green-700 text-white py-3 rounded-xl font-semibold"
        >
          Login
        </button>

      </form>
    </AuthLayout>
  );
}

export default Login;