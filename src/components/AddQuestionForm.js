import React, { useState } from "react";
import quizService from "../services/quizService";

const AddQuestionForm = ({ quizId, onSuccess }) => {
  const [questionText, setQuestionText] = useState("");
  const [options, setOptions] = useState(["", "", "", ""]);
  const [correctAnswerIndex, setCorrectAnswerIndex] = useState(0);
  const [message, setMessage] = useState("");

  const handleOptionChange = (index, value) => {
    const updatedOptions = [...options];
    updatedOptions[index] = value;
    setOptions(updatedOptions);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const question = {
      text: questionText,
      options: options,
      correctAnswerIndex: correctAnswerIndex
    };

    try {
      await quizService.addQuestion(quizId, question);
      setMessage("Question successfully saved!");
      setQuestionText("");
      setOptions(["", "", "", ""]);
      setCorrectAnswerIndex(0);
      onSuccess();
    } catch (error) {
      setMessage("Error saving question, please try again.");
    }
  };

  return (
    <div>
      <h4>Add a Question</h4>
      <form onSubmit={handleSubmit}>
        <label>Question Text:</label>
        <input
          type="text"
          value={questionText}
          onChange={(e) => setQuestionText(e.target.value)}
          required
        />
        
        {options.map((option, index) => (
          <div key={index}>
            <label>Option {index + 1}:</label>
            <input
              type="text"
              value={option}
              onChange={(e) => handleOptionChange(index, e.target.value)}
              required
            />
          </div>
        ))}
        
        <label>Correct Answer:</label>
        <select
          value={correctAnswerIndex}
          onChange={(e) => setCorrectAnswerIndex(parseInt(e.target.value))}
        >
          {options.map((_, index) => (
            <option key={index} value={index}>
              Option {index + 1}
            </option>
          ))}
        </select>

        <button type="submit">Save Question</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default AddQuestionForm;
