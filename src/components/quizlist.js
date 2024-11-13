// src/components/QuizList.js

import React, { useEffect, useState } from "react";
import quizService from "../services/quizService";
import AddQuestionForm from "./AddQuestionForm";  // Import the AddQuestionForm component


const QuizList = () => {
  const [quizzes, setQuizzes] = useState([]);
  const [message, setMessage] = useState("");
  const [selectedQuizId, setSelectedQuizId] = useState(null); // Track selected quiz for adding question

  useEffect(() => {
    fetchQuizzes();
  }, []);

  const fetchQuizzes = async () => {
    try {
      const response = await quizService.getAllQuizzes();
      setQuizzes(response.data);
      setMessage("");
    } catch (error) {
      setMessage("Error fetching quizzes, please try again.");
    }
  };

  const handleAddQuestionClick = (quizId) => {
    setSelectedQuizId(quizId); // Show form for the selected quiz
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
            <button onClick={() => handleAddQuestionClick(quiz.id)}>
              Add Question
            </button>
            {selectedQuizId === quiz.id && (
              <AddQuestionForm quizId={quiz.id} onSuccess={fetchQuizzes} />
            )}
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
