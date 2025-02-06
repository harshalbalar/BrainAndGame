// src/components/CreateQuestion.js

import React, { useState } from 'react';
import axios from "axios";
import {getToken} from "../../services/authService";

const CreateQuestion = ({ quizId, updateQuizzes }) => {
    const [questionText, setQuestionText] = useState('');
    const [answers, setAnswers] = useState(['', '']); // Start with two answer fields
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const handleAnswerChange = (index, value) => {
        const newAnswers = [...answers];
        newAnswers[index] = value;
        setAnswers(newAnswers);
    };

    const addAnswerField = () => {
        setAnswers([...answers, '']);
    };

    const removeAnswerField = (index) => {
        const newAnswers = [...answers];
        newAnswers.splice(index, 1);
        setAnswers(newAnswers);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!questionText.trim()) {
            setError('Question text cannot be empty.');
            return;
        }
        if (answers.some(answer => !answer.trim())) {
            setError('All answer fields must be filled.');
            return;
        }
        setLoading(true);
        setError(null);
        setSuccess(null);
        try {
            const questionData = {
                text: questionText,
                answers: answers.map((answerText, index) => ({
                    text: answerText,
                    correct: index === 0, // First answer is correct: true, others: false
                })),
            };
            const token = getToken()
            console.log(questionData);
            await axios.post(`http://localhost:8080/api/questions/quiz/${quizId}`, questionData, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });
            setSuccess('Question successfully created.');
            setQuestionText('');
            setAnswers(['', '']); // Reset to two empty fields
            updateQuizzes(); // Refresh the quizzes or questions list
        } catch (err) {
            setError('Failed to create question.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="add-question-form">
            <h3 className="add-question-header">Create a New Question</h3>
            {error && <p style={styles.error}>{error}</p>}
            {success && <p style={styles.success}>{success}</p>}
            <form onSubmit={handleSubmit} className="add-question-form-content">
                <input
                    type="text"
                    placeholder="Question Text"
                    value={questionText}
                    onChange={(e) => setQuestionText(e.target.value)}
                    className="add-question-input"
                    required
                />
                <div style={styles.answersContainer}>
                    <h4>Answers:</h4>
                    {answers.map((answer, index) => (
                        <div key={index} style={styles.answerField}>
                            <input
                                type="text"
                                placeholder={index === 0 ? "Correct Answer" : `Option ${index + 1}`}
                                value={answer}
                                onChange={(e) => handleAnswerChange(index, e.target.value)}
                                className="add-question-input"
                                required
                            />
                            {answers.length > 2 && (
                                <button
                                    type="button"
                                    onClick={() => removeAnswerField(index)}
                                    style={styles.removeButton}
                                >
                                    Remove
                                </button>
                            )}
                        </div>
                    ))}
                    <button type="button" onClick={addAnswerField} style={styles.addButton}>
                        Add Answer
                    </button>
                </div>
                <button type="submit" style={styles.submitButton} disabled={loading}>
                    {loading ? 'Creating...' : 'Create Question'}
                </button>
            </form>
        </div>
    );
};

// Simple inline styles for demonstration
const styles = {
    container: {
        maxWidth: '600px',
        margin: '20px auto',
        padding: '20px',
        border: '1px solid #ccc',
        borderRadius: '5px',
        fontFamily: 'Arial, sans-serif',
        backgroundColor: '#f9f9f9',
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
    },
    input: {
        padding: '10px',
        marginBottom: '10px',
        borderRadius: '5px',
        border: '1px solid #ccc',
        fontSize: '16px',
    },
    answersContainer: {
        marginBottom: '10px',
    },
    answerField: {
        display: 'flex',
        alignItems: 'center',
        marginBottom: '5px',
    },
    removeButton: {
        marginLeft: '10px',
        padding: '5px 10px',
        borderRadius: '5px',
        border: 'none',
        backgroundColor: '#dc3545',
        color: '#fff',
        cursor: 'pointer',
    },
    addButton: {
        padding: '5px 10px',
        borderRadius: '5px',
        border: 'none',
        backgroundColor: '#28a745',
        color: '#fff',
        cursor: 'pointer',
        marginTop: '5px',
    },
    submitButton: {
        padding: '10px',
        borderRadius: '5px',
        border: 'none',
        backgroundColor: '#007bff',
        color: '#fff',
        fontSize: '16px',
        cursor: 'pointer',
    },
    error: {
        color: 'red',
    },
    success: {
        color: 'green',
    },
};

export default CreateQuestion;
