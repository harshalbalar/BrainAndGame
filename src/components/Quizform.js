import React, { useState } from "react";
import quizService from "../services/quizService";
import Swal from 'sweetalert2'


const QuizForm = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  // const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    const quiz = { title, description };

    try {
      await quizService.createQuiz(quiz);
      Swal.fire({
        title: 'Success!',
        text: 'Quiz successfully saved!',
        icon: 'success',
        confirmButtonText: 'Cool'
      })
      setTitle("");
      setDescription("");
    } catch (error) {
      Swal.fire({
        title: 'Error!',
        text: 'Error saving quiz, please try again.',
        icon: 'error',
        confirmButtonText: 'ok'
      })
      // setMessage("Error saving quiz, please try again.");
    }
  };

  return (
    <div id="quiz">
        <form onSubmit={handleSubmit} className="quiz-form">
        <p className="quiz-form-title">Add New Quiz</p>
          <input
            type="text"
            placeholder="Enter the Quiz Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
          <input
            type="text"
            placeholder="Enter the Quiz Description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
          <button type="submit">Save Quiz</button>
        {/* {message && <p className="quiz-message">{message}</p>} */}
        </form>
      </div>
  );
};

export default QuizForm;
