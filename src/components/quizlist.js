// src/components/QuizList.js
import React, { useEffect, useState } from "react";
import quizService from "../services/quizService";

const QuizList = () => {
  const [quizzes, setQuizzes] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetchQuizzes();
  }, []);

  const fetchQuizzes = async () => {
    try {
      const response = await quizService.getAllQuizzes();
      console.log("Fetched quizzes:", response.data);
      setQuizzes(response.data);
      setMessage("");
    } catch (error) {
      console.error("Error fetching quizzes:", error);
      setMessage("Error fetching quizzes, please try again.");
    }
  };

  return (
    <div>
      <h2>All Created Quizzes</h2>
      <button onClick={fetchQuizzes}>Refresh Quizzes</button>
      {message && <p>{message}</p>}
      <ul>
        {quizzes.map((quiz) => (
          <li key={quiz.id}>
            <h3>{quiz.title || "No title available"}</h3>
            <p>{quiz.description || "No description available"}</p>
            <ul>
              {quiz.questions && quiz.questions.length > 0 ? (
                quiz.questions.map((question, index) => (
                  <li key={index}>{question}</li>
                ))
              ) : (
                <p>No questions added yet.</p>
              )}
            </ul>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default QuizList;
