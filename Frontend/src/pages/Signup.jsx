import { Link } from "react-router-dom";
import AuthLayout from "../components/AuthLayout";

function Signup() {
  return (
    <AuthLayout
      title="Create Account"
      subtitle="Join our loan management platform"
      footer={
        <p className="text-center mt-6 text-gray-600">
          Already have an account?{" "}
          <Link
            to="/"
            className="text-green-600 font-semibold"
          >
            Login
          </Link>
        </p>
      }
    >
      <form className="space-y-4">

        <input
          type="text"
          placeholder="Full Name"
          className="w-full border rounded-xl px-4 py-3"
        />

        <input
          type="email"
          placeholder="Email"
          className="w-full border rounded-xl px-4 py-3"
        />

        <input
          type="password"
          placeholder="Password"
          className="w-full border rounded-xl px-4 py-3"
        />

        <button
          className="w-full bg-green-600 hover:bg-green-700 text-white py-3 rounded-xl font-semibold"
        >
          Create Account
        </button>

      </form>
    </AuthLayout>
  );
}

export default Signup;