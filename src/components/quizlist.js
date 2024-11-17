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
      console.log("Fetched quizzes:", response.data);  // Log the raw response data

      // Parse the stringified questions
      const quizzesWithParsedQuestions = response.data.map(quiz => {
        const parsedQuestions = quiz.questions.map(question => {
          // Parse the stringified question into an actual object
          return JSON.parse(question);
        });

        // Return the updated quiz object with parsed questions
        return { ...quiz, questions: parsedQuestions };
      });

      setQuizzes(quizzesWithParsedQuestions);
      setMessage("");
    } catch (error) {
      setMessage("Error fetching quizzes, please try again.");
    }
  };

  const handleAddQuestionClick = (quizId) => {
    setSelectedQuizId(quizId); // Show form for the selected quiz
  };

  const formatQuestion = (question, index) => {
    console.log("Rendering question:", question);  // Log the question object
    
    return (
      <div key={index}>
        <h4>Question {index + 1}: {question.text}</h4>
        <div>
          <strong>Options:</strong>
          {question.options && Array.isArray(question.options) && question.options.length > 0 ? (
            <ul>
              {question.options.map((option, i) => (
                <li key={i}>
                  {String.fromCharCode(97 + i)}. {option}  {/* 'a', 'b', 'c', 'd' */}
                </li>
              ))}
            </ul>
          ) : (
            <p>No options available.</p>  // Handle if options are missing
          )}
        </div>
        <div>
          <strong>Correct Answer:</strong> 
          {question.correctAnswerIndex !== undefined ? (
            String.fromCharCode(97 + question.correctAnswerIndex)  // 'a', 'b', 'c', 'd' mapping
          ) : (
            "Not specified"  // Handle missing or undefined correctAnswerIndex
          )}
        </div>
      </div>
    );
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
                quiz.questions.map((question, index) => formatQuestion(question, index))
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
