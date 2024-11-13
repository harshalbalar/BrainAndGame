// src/services/quizService.js

import axios from "axios";

const API_URL = "http://localhost:8080/api/quizzes";

const createQuiz = (quiz) => axios.post(API_URL, quiz);
const getAllQuizzes = () => axios.get(API_URL);

// New method to add a question to a specific quiz
const addQuestion = (quizId, question) => {
  return axios.post(`${API_URL}/${quizId}/questions`, question, {
    headers: { "Content-Type": "text/plain" },
  });
};

export default {
  createQuiz,
  getAllQuizzes,
  addQuestion,  // Export the new method
};
