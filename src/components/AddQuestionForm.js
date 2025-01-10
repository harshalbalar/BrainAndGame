import React, { useState } from "react";
import quizService from "../services/quizService";
import Swal from 'sweetalert2'

const AddQuestionForm = ({ quizId, onSuccess }) => {
  const [questionText, setQuestionText] = useState("");
  const [options, setOptions] = useState(["", "", "", ""]);
  // const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Automatically set the first option as the correct answer
    const newQuestion = {
      text: questionText,
      options: options,
      correctAnswerIndex: 0, // Always index 0
    };

    try {
      await quizService.addQuestion(quizId, newQuestion);
      Swal.fire({
        title: 'Success!',
        text: 'Question successfully saved!',
        icon: 'success',
        confirmButtonText: 'Cool'
      })
      // setMessage("Question successfully saved!");
      setQuestionText("");
      setOptions(["", "", "", ""]);
      onSuccess(); // Trigger a refresh or callback after successful save
    } catch (error) {
      Swal.fire({
        title: 'Error!',
        text: 'Error saving question, please try again.',
        icon: 'error',
        confirmButtonText: 'ok'
      })
    }
  };

  return (
    <div className="add-question-form">
      <h4 className="add-question-header">Add a Question</h4>
      <form onSubmit={handleSubmit} className="add-question-form-content">
        <label className="add-question-label">Question Text:</label>
        <input
          type="text"
          value={questionText}
          onChange={(e) => setQuestionText(e.target.value)}
          required
          className="add-question-input"
        />
        <label className="add-question-label">Option 1 (Correct Answer):</label>
        <input
          type="text"
          value={options[0]}
          onChange={(e) => setOptions([e.target.value, options[1], options[2], options[3]])}
          required
          className="add-question-input"
        />
        <label className="add-question-label">Option 2:</label>
        <input
          type="text"
          value={options[1]}
          onChange={(e) => setOptions([options[0], e.target.value, options[2], options[3]])}
          required
          className="add-question-input"
        />
        <label className="add-question-label">Option 3:</label>
        <input
          type="text"
          value={options[2]}
          onChange={(e) => setOptions([options[0], options[1], e.target.value, options[3]])}
          required
          className="add-question-input"
        />
        <label className="add-question-label">Option 4:</label>
        <input
          type="text"
          value={options[3]}
          onChange={(e) => setOptions([options[0], options[1], options[2], e.target.value])}
          required
          className="add-question-input"
        />
        <button type="submit" className="add-question-button">
          Save Question
        </button>
      </form>
      {/* {message && <p className="add-question-message">{message}</p>} */}
    </div>
  );
};

export default AddQuestionForm;
