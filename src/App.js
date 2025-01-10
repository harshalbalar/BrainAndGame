import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import EventList from "./components/EventList";
import EventDetails from "./components/EventDetails";
import EventForm from "./components/EventForm";
import QuizForm from "./components/Quizform";
import QuizList from "./components/quizlist";
import Navbar from "./components/Navbar";
import Register from "./components/Register";
import Login from "./components/Login";
import PrivateRoute from "./components/PrivateRoute";
import "./App.css";

const App = () => {
    return (
        <Router>
            <Routes>
                {/* Public routes */}
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />

                {/* Private routes */}
                <Route
                    path="/*"
                    element={
                        <PrivateRoute>
                            <Navbar />
                            <div className="main-content">
                                <Routes>
                                    <Route path="/add-event" element={<EventForm />} />
                                    <Route path="/add-quiz" element={<QuizForm />} />
                                    <Route path="/quizzes" element={<QuizList />} />
                                    <Route path="/events/:eventId" element={<EventDetails />} />
                                    <Route path="/events" element={<EventList />} />
                                </Routes>
                            </div>
                        </PrivateRoute>
                    }
                />
            </Routes>
        </Router>
    );
};

export default App;
