import React, { useState } from "react";
import { register, login, saveToken } from "../services/authService";
import { useNavigate, Link } from "react-router-dom";

const Register = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const response = await register(username, password);
            setMessage(response.data.message);

            // Automatically log in after successful registration
            const loginResponse = await login(username, password);
            saveToken(loginResponse.data.jwt);
            console.log(loginResponse.data.jwt)

            // Redirect to default route
            navigate("/add-event");
        } catch (error) {
            setError(error.response?.data?.error || "An error occurred");
        }
    };

    return (
        <div className="register-container">
            <h2>Register</h2>
            <form onSubmit={handleRegister}>
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
                <button type="submit">Register</button>
            </form>
            {message && <p style={{ color: "green" }}>{message}</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
            <div className="register-link">
                <p>I have an account? <Link to="/login">Login here</Link>.</p>
            </div>
        </div>
    );
};

export default Register;
