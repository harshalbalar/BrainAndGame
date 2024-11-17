import React, { useState } from "react";
import quizService from "../services/quizService";

const AddQuestionForm = ({ quizId, onSuccess }) => {
  const [questionText, setQuestionText] = useState("");
  const [options, setOptions] = useState(["", "", "", ""]);
  const [correctAnswerIndex, setCorrectAnswerIndex] = useState(0);
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newQuestion = {
      text: questionText,
      options: options,
      correctAnswerIndex: correctAnswerIndex,
    };

    try {
      await quizService.addQuestion(quizId, newQuestion);
      setMessage("Question successfully saved!");
      setQuestionText("");
      setOptions(["", "", "", ""]);
      setCorrectAnswerIndex(0);
      onSuccess();  // Trigger a refresh or callback after successful save
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
        <br />
        <label>Option 1:</label>
        <input
          type="text"
          value={options[0]}
          onChange={(e) => setOptions([e.target.value, options[1], options[2], options[3]])}
          required
        />
        <br />
        <label>Option 2:</label>
        <input
          type="text"
          value={options[1]}
          onChange={(e) => setOptions([options[0], e.target.value, options[2], options[3]])}
          required
        />
        <br />
        <label>Option 3:</label>
        <input
          type="text"
          value={options[2]}
          onChange={(e) => setOptions([options[0], options[1], e.target.value, options[3]])}
          required
        />
        <br />
        <label>Option 4:</label>
        <input
          type="text"
          value={options[3]}
          onChange={(e) => setOptions([options[0], options[1], options[2], e.target.value])}
          required
        />
        <br />
        <label>Correct Answer Index (0-3):</label>
        <input
          type="number"
          min="0"
          max="3"
          value={correctAnswerIndex}
          onChange={(e) => setCorrectAnswerIndex(Number(e.target.value))}
          required
        />
        <br />
        <button type="submit">Save Question</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default AddQuestionForm;
