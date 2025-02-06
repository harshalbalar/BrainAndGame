// src/components/MyQuizzes.js

import React, { useState, useEffect } from 'react';
import axios from "axios";
import { getToken } from "../../services/authService";
import CreateQuestion from "../question/CreateQuestion";

const MyQuizzes = () => {
    const [quizzes, setQuizzes] = useState([]);
    const [newQuizTitle, setNewQuizTitle] = useState('');
    const [newQuizDescription, setNewQuizDescription] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    // Fetch "My Quizzes" on component mount
    useEffect(() => {
        fetchMyQuizzes();
    }, []);

    const fetchMyQuizzes = async () => {
        setLoading(true);
        setError(null);
        const token = getToken()
        try {
            const response = await axios.get('http://localhost:8080/api/quizzes/my', {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });
            console.log(response.data)
            setQuizzes(response.data);
        } catch (err) {
            setError('Failed to fetch quizzes.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleCreateQuiz = async (e) => {
        e.preventDefault();
        if (!newQuizTitle.trim()) {
            setError('Quiz title cannot be empty.');
            return;
        }

        try {
            const newQuiz = {
                title: newQuizTitle,
                description: newQuizDescription,
                isOpen: false, // Default value
                // The user association is handled by the backend
            };
            const token = getToken()
            const response = await axios.post('http://localhost:8080/api/quizzes', newQuiz, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });
            console.log(response.data);
            setNewQuizTitle('');
            setNewQuizDescription('');
            fetchMyQuizzes(); // Refresh the quizzes list
        } catch (err) {
            setError('Failed to create quiz.');
            console.error(err);
        }
    };

    return (
        <div id="quiz">

            <form onSubmit={handleCreateQuiz} className="quiz-form">
                <h2 className="quiz-form-title">Create New Quiz</h2>
                <input
                    type="text"
                    placeholder="Quiz Title"
                    value={newQuizTitle}
                    onChange={(e) => setNewQuizTitle(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Quiz Description"
                    value={newQuizDescription}
                    onChange={(e) => setNewQuizDescription(e.target.value)}
                    required
                />
                <button type="submit" >Create Quiz</button>
            </form>
            <h2 className="event-list-title">My Quizzes</h2>

            {loading && <p>Loading quizzes...</p>}
            {error && <p style={styles.error}>{error}</p>}

            <ul className='quiz-list'>
                {quizzes.map(quiz => (
                    <li key={quiz.id} className="quiz-item">
                        <h3 className="quiz-item-title">Quiz Name : {quiz.title}</h3>
                        <p className="quiz-item-description">Description: {quiz.description}</p>
                        <p>Status: {quiz.open ? 'Open' : 'Closed'}</p>
                        <div className='box3'>
                        {quiz.questions.map(question => {
                            return <>
                                <p className="quiz-item-description" style={{ marginTop: "20px" }}>Question: {question.text}</p>
                                <div className='box4'>
                                <p>Answers: </p>
                                <div>
                                {question.answers.map(answer => {
                                    return <div style={{ marginLeft: "20px", marginBottom: "15px" }}>
                                        <p>○ {answer.text}</p>
                                    </div>
                                })}
                                </div>
                                </div>
                                
                            </>
                        })}
                        </div>
                        <CreateQuestion quizId={quiz.id} updateQuizzes={fetchMyQuizzes}></CreateQuestion>
                        {/* Add more quiz details as needed */}
                    </li>
                ))}
            </ul>


        </div>
    );
};

// Simple inline styles for demonstration
const styles = {
    container: {
        margin: '0 auto',
        padding: '20px',
        fontFamily: 'Arial, sans-serif',
    },
    list: {
        listStyleType: 'none',
        padding: 0,
    },
    listItem: {
        border: '1px solid #ccc',
        borderRadius: '5px',
        padding: '15px',
        marginBottom: '15px',
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
    button: {
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
};

export default MyQuizzes;
