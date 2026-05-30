import { Link } from "react-router-dom";

function AuthLayout({ title, subtitle, children, footer }) {
  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 via-white to-green-100 flex items-center justify-center p-4">

      <div className="w-full max-w-6xl grid lg:grid-cols-2 bg-white rounded-3xl shadow-2xl overflow-hidden">

        {/* Left Section */}
        <div className="hidden lg:flex flex-col justify-center items-center bg-green-50 p-12">

          <div className="text-center">

            <div className="text-6xl mb-4">💰</div>

            <h1 className="text-4xl font-bold text-green-700">
              Loan Management System
            </h1>

            <p className="mt-3 text-gray-600 text-lg">
              Customer Portal
            </p>

            <p className="mt-4 text-gray-500 max-w-md">
              Manage your loans, track payments and stay updated
              with your account details.
            </p>

          </div>

          <img
            src="https://illustrations.popsy.co/green/calculator.svg"
            alt="Loan"
            className="w-80 mt-10"
          />

          <div className="flex gap-8 mt-6 text-sm text-gray-600">
            <span>✓ Easy Application</span>
            <span>✓ Quick Approval</span>
            <span>✓ Secure & Trusted</span>
          </div>

        </div>

        {/* Right Section */}
        <div className="flex items-center justify-center p-8 lg:p-12">

          <div className="w-full max-w-md">

            <div className="text-center mb-8">

              <div className="text-5xl mb-3">💰</div>

              <h2 className="text-3xl font-bold text-green-700">
                {title}
              </h2>

              <p className="text-gray-500 mt-2">
                {subtitle}
              </p>

            </div>

            {children}

            {footer}

          </div>

        </div>

      </div>
    </div>
  );
}

export default AuthLayout;