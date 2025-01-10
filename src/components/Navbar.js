import React from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "./logo.jpg";
import { removeToken, getToken } from "../services/authService";
import {jwtDecode} from "jwt-decode";

const Navbar = () => {
  const navigate = useNavigate();
  const token = getToken();
  const isLoggedIn = !!token;
  let username = "User";

  if (isLoggedIn) {
    try {
      username = jwtDecode(token).sub || "User";
    } catch (error) {
      console.error("Invalid token:", error);
    }
  }

  const handleLogout = () => {
    removeToken();
    navigate("/login");
  };

  return (
    <header className="header">
      <div className="logo-container">
        <img src={logo} alt="Brain and Game Logo" className="logo" />
      </div>
      {isLoggedIn && (
        <nav className="navbar">
          <ul>
            <li>
              <Link to="/events">Home</Link>
            </li>
            <li>
              <Link to="/add-event">Add Event</Link>
            </li>
            <li>
              <Link to="/add-quiz">Add Quiz</Link>
            </li>
            <li>
              <Link to="/events">View Events</Link>
            </li>
            <li>
              <Link to="/quizzes">View Quizzes</Link>
            </li>
          </ul>
        </nav>
      )}
      <div className="user-section">
        {isLoggedIn ? (
          <div className="user-info">
            <span className="welcome-text">Welcome, {username}</span>
            <button onClick={handleLogout} className="logout-button">
              Logout
            </button>
          </div>
        ) : (
          <Link to="/login" className="login-link">
            Login
          </Link>
        )}
      </div>
    </header>
  );
};

export default Navbar;
