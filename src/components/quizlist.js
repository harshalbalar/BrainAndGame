import React, { useEffect, useState } from "react";
import quizService from "../services/quizService";
import AddQuestionForm from "./AddQuestionForm";
import QuizPlayer from "./QuizPlayer";

const QuizList = () => {
  const [quizzes, setQuizzes] = useState([]);
  const [message, setMessage] = useState("");
  const [selectedQuizId, setSelectedQuizId] = useState(null);
  const [quizInPlay, setQuizInPlay] = useState(null);

  useEffect(() => {
    fetchQuizzes();
  }, []);

  const fetchQuizzes = async () => {
    try {
      const response = await quizService.getAllQuizzes();
      const quizzesWithParsedQuestions = response.data.map((quiz) => ({
        ...quiz,
        questions: quiz.questions.map((question) => JSON.parse(question)),
      }));
      setQuizzes(quizzesWithParsedQuestions);
      setMessage("");
    } catch (error) {
      setMessage("Error fetching quizzes, please try again.");
    }
  };

  const handleAddQuestionClick = (quizId) => {
    setSelectedQuizId(quizId);
  };

  const handlePlayQuizClick = (quiz) => {
    if (quiz.questions && quiz.questions.length > 0) {
      setQuizInPlay(quiz);
    } else {
      alert("Please add questions to the quiz before playing!");
    }
  };

  const handleQuizFinish = () => {
    setQuizInPlay(null);
    alert("Thanks for playing!");
  };

  const formatQuestion = (question, index) => (
    <div key={index} className="quiz-question">
      <h4 className="quiz-question-title">
        Question {index + 1}: {question.text}
      </h4>
      <div>
        <strong>Options:</strong>
        <ul className="quiz-options">
          {question.options.map((option, i) => (
            <li key={i}>{String.fromCharCode(65 + i)}. {option}</li>
          ))}
        </ul>
      </div>
      <div>
        <strong>Correct Answer: </strong>
        {question.options[0]}
      </div>
    </div>
  );

  return (
    <div className="quiz-container">
      <h2 className="quiz-title">All Created Quizzes</h2>
      <button className="quiz-refresh-button" onClick={fetchQuizzes}>
        Refresh Quizzes
      </button>
      {message && <p className="quiz-message">{message}</p>}
      {quizInPlay ? (
        <QuizPlayer quiz={quizInPlay} onFinish={handleQuizFinish} />
      ) : (
        <ul className="quiz-list">
          {quizzes.map((quiz) => (
            <li key={quiz.id} className="quiz-item">
              <h3 className="quiz-item-title">{quiz.title || "No title available"}</h3>
              <p className="quiz-item-description">{quiz.description || "No description available"}</p>
              <button
                className="quiz-add-question-button"
                onClick={() => handleAddQuestionClick(quiz.id)}
              >Add Question</button>
              {selectedQuizId === quiz.id && (
                <AddQuestionForm quizId={quiz.id} onSuccess={fetchQuizzes} />
              )}
              <button
                className="quiz-play-button"
                onClick={() => handlePlayQuizClick(quiz)}
              >Play Quiz</button>
              <h4 className="quiz-question-header">Questions:</h4>
              <ul className="quiz-questions-list">
                {quiz.questions && quiz.questions.length > 0 ? (
                  quiz.questions.map((question, index) => formatQuestion(question, index))
                ) : (
                  <p className="quiz-no-questions">No questions added yet.</p>
                )}
              </ul>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default QuizList;