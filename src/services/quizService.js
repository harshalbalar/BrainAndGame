// src/services/quizService.js

import axios from "axios";

const API_URL = "http://localhost:8080/api/quizzes";

const createQuiz = (quiz) => axios.post(API_URL, quiz);
const getAllQuizzes = () => axios.get(API_URL);

const addQuestion = (quizId, question) => {
  return axios.post(`${API_URL}/${quizId}/questions`, question);
};

export default {
  createQuiz,
  getAllQuizzes,
  addQuestion,  // Export the new method
};