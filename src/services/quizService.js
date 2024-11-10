// src/services/quizService.js
import axios from "axios";

const API_URL = "http://localhost:8080/api/quizzes";

const createQuiz = (quiz) => {
  return axios.post(API_URL, quiz);
};

const getAllQuizzes = () => {
  return axios.get(API_URL);
};

export default {
  createQuiz,
  getAllQuizzes,
};
