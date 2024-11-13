// src/components/AddQuestionForm.js

import React, { useState } from "react";
import quizService from "../services/quizService";


const AddQuestionForm = ({ quizId, onSuccess }) => {
  const [questionText, setQuestionText] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await quizService.addQuestion(quizId, questionText);
      setMessage("Question successfully saved!");
      setQuestionText("");
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
        <button type="submit">Save Question</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default AddQuestionForm;
