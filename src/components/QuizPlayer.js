import React, { useState } from "react";

const QuizPlayer = ({ quiz, onFinish }) => {
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedOptionIndex, setSelectedOptionIndex] = useState(null);
  const [isAnswered, setIsAnswered] = useState(false); // Prevent multiple clicks

  const currentQuestion = quiz.questions[currentQuestionIndex];

  const handleOptionClick = (index) => {
    if (isAnswered) return; // Prevent further clicks
    setSelectedOptionIndex(index);
    setIsAnswered(true);

    // Auto-move to the next question after 3 seconds
    setTimeout(() => {
      if (currentQuestionIndex < quiz.questions.length - 1) {
        setCurrentQuestionIndex(currentQuestionIndex + 1);
        setSelectedOptionIndex(null);
        setIsAnswered(false); // Reset for the next question
      } else {
        onFinish(); // Finish the quiz
      }
    }, 3000);
  };

  const isCorrect = (index) => index === 0; // First option is correct

  return (
    <div className="quiz-player">
      <h3 className="quiz-title">{quiz.title}</h3>
      <h4 className="quiz-question">
        Question {currentQuestionIndex + 1}: {currentQuestion.text}
      </h4>
      <ul className="quiz-options">
        {currentQuestion.options.map((option, index) => (
          <li
            key={index}
            onClick={() => handleOptionClick(index)}
            className={`quiz-option ${
              selectedOptionIndex === index
                ? isCorrect(index)
                  ? "correct"
                  : "incorrect"
                : ""
            }`}
          >
            {option}
          </li>
        ))}
      </ul>
      {isAnswered && (
        <p className="quiz-feedback">
          {isCorrect(selectedOptionIndex)
            ? "Correct!"
            : `Wrong! The correct answer is: ${currentQuestion.options[0]}`}
        </p>
      )}
    </div>
  );
};

export default QuizPlayer;
