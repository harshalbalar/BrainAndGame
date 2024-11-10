// src/components/QuizForm.js
import React, { useState } from "react";
import quizService from "../services/quizService";

const QuizForm = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    const quiz = { title, description };

    try {
      await quizService.createQuiz(quiz);
      setMessage("Quiz successfully saved!");
      setTitle("");
      setDescription("");
    } catch (error) {
      setMessage("Error saving quiz, please try again.");
    }
  };

  return (
    <div>
      <h2>Create a New Quiz</h2>
      <form onSubmit={handleSubmit}>
        <label>Quiz Title:</label>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
        <br/>
        <label>Description:</label>
        <input
          type="text"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />
        <br/>
        <button type="submit">Create Quiz</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default QuizForm;
