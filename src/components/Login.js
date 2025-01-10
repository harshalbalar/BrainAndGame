import React, { useState } from "react";
import { login, saveToken } from "../services/authService";
import { useNavigate, Link } from "react-router-dom";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await login(username, password);
            saveToken(response.data.jwt);
            setError("");
            navigate("/add-event");
        } catch (error) {
            setError(error.response?.data?.error || "Invalid credentials");
        }
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type="submit">Login</button>
            </form>
            {error && <p style={{ color: "red" }}>{error}</p>}

            <div className="register-link">
                <p>Don't have an account? <Link to="/register">Register here</Link>.</p>
            </div>
        </div>
    );
};

export default Login;
