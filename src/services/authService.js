import axios from "axios";

const API_URL = "http://localhost:8080/auth";

export const register = async (username, password) => {
    return axios.post(`${API_URL}/register`, { username, password });
};

export const login = async (username, password) => {
    return axios.post(`${API_URL}/login`, { username, password });
};

export const saveToken = (token) => {
    localStorage.setItem("jwt", token);
};

export const getToken = () => {
    return localStorage.getItem("jwt");
};

export const removeToken = () => {
    localStorage.removeItem("jwt");
};
