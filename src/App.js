import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import EventList from "./components/legacy/EventList";
import EventDetails from "./components/legacy/EventDetails";
import EventForm from "./components/legacy/EventForm";
import QuizForm from "./components/legacy/Quizform";
import QuizList from "./components/legacy/quizlist";
import Navbar from "./components/legacy/Navbar";
import Register from "./components/Register";
import Login from "./components/Login";
import PrivateRoute from "./components/PrivateRoute";
import "./App.css";
import MyEvents from "./components/event/MyEvents";
import MyQuizzes from "./components/Quiz/MyQuizzes";

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
                                    <Route path="/quizzes" element={<MyQuizzes />} />
                                    <Route path="/events/:eventId" element={<EventDetails />} />
                                    <Route path="/events" element={<MyEvents />} />
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
