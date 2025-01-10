import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { QRCodeCanvas } from "qrcode.react";
import { useNavigate } from "react-router-dom";
import Swal from 'sweetalert2'

const EventDetails = () => {
  const navigate = useNavigate();
  const { eventId } = useParams();
  const [event, setEvent] = useState(null);
  const [quizzes, setQuizzes] = useState([]);
  const [selectedQuiz, setSelectedQuiz] = useState("");

  // Fetch event details
  const fetchEventDetails = useCallback(async () => {
    try {
      const { data } = await axios.get(`http://localhost:8080/api/events/${eventId}`);
      setEvent(data);
    } catch (error) {
      console.error("Error fetching event details:", error);
      Swal.fire({
        title: 'Error!',
        text: 'Failed to fetch event details. Please try again',
        icon: 'error',
        confirmButtonText: 'ok'
      })
      // alert("Failed to fetch event details. Please try again.");
    }
  }, [eventId]);

  // Fetch all quizzes
  const fetchQuizzes = useCallback(async () => {
    try {
      const { data } = await axios.get("http://localhost:8080/api/quizzes");
      setQuizzes(data);
    } catch (error) {
      console.error("Error fetching quizzes:", error);
      Swal.fire({
        title: 'Error!',
        text: 'Failed to fetch quizzes. Please try again.',
        icon: 'error',
        confirmButtonText: 'ok'
      })
      // alert("Failed to fetch quizzes. Please try again.");
    }
  }, []);

  // Fetch data on component mount
  useEffect(() => {
    fetchEventDetails();
    fetchQuizzes();
  }, [fetchEventDetails, fetchQuizzes]);

  const toggleEventStatus = async () => {
    try {
      const { data } = await axios.put(`http://localhost:8080/api/events/${eventId}/toggle-status`);
      setEvent((prev) => ({ ...prev, open: data.open }));
    } catch (error) {
      console.error("Error toggling event status:", error);
      Swal.fire({
        title: 'Error!',
        text: 'Failed to toggle event status.',
        icon: 'error',
        confirmButtonText: 'ok'
      })
      // alert("Failed to toggle event status.");
    }
  };

  const toggleQuizStatus = async (quizId) => {
    try {
      const { data } = await axios.put(`http://localhost:8080/api/quizzes/${quizId}/toggle-status`);
      setEvent((prev) => ({
        ...prev,
        quizzes: prev.quizzes.map((quiz) =>
          quiz.id === quizId ? { ...quiz, open: data.open } : quiz
        ),
      }));
      navigate("/events");
      Swal.fire({
        title: 'Success!',
        text: 'Quiz successfully saved!',
        icon: 'success',
        confirmButtonText: 'Cool'
      })
    } catch (error) {
      console.error("Error toggling quiz status:", error);
      // alert("Failed to toggle quiz status.");
      Swal.fire({
        title: 'Error!',
        text: 'Failed to toggle quiz status.',
        icon: 'error',
        confirmButtonText: 'ok'
      })
    }
  };
  
  const assignQuizToEvent = async () => {
    if (!selectedQuiz) {
      Swal.fire({
        title: 'Error!',
        text: 'Please select a quiz to assign.',
        icon: 'error',
        confirmButtonText: 'ok'
      })
      // alert("Please select a quiz to assign.");
      return;
    }
    try {
      await axios.post(`http://localhost:8080/api/events/${eventId}/assign-quiz/${selectedQuiz}`);
      fetchEventDetails(); // Refresh event details after assigning quiz
    } catch (error) {
      console.error("Error assigning quiz to event:", error);
      alert("This quiz has already been assigned to the event.");
    }
  };

  const eventUrl = `http://localhost:3000/event/${eventId}`;

  return (
    <div className="event-details">
      {event ? (
        <>
          <h3>{event.name}</h3>
          <p>
            Status:{" "}
            <span className={event.open ? "status-open" : "status-closed"}>
              {event.open ? "Open" : "Closed"}
            </span>
          </p>
          <label className="switch">
            <input type="checkbox" checked={event.open} onChange={toggleEventStatus} />
            <span className="slider round"></span>
          </label>

          {/* QR Code Section */}
          <div className="qr-section">
            <h4>Guests can join via this QR code:</h4>
            <QRCodeCanvas className="qr-code" value={eventId} size={256} />
            <p>Event URL: <a href={eventUrl}>{eventUrl}</a></p>
          </div>

          {/* Assigned Quizzes */}
          <div className="quizzes-section">
            <h4>Assigned Quizzes:</h4>
            {event.quizzes && event.quizzes.length > 0 ? (
              <ul>
                {event.quizzes.map((quiz) => (
                  <li key={quiz.id} className="quiz-items">
                    <span>{quiz.title}</span>
                    <label className="switch-status">
                      <input
                        type="checkbox"
                        checked={quiz.open}
                        onChange={() => toggleQuizStatus(quiz.id)}
                      />
                      <span className="slider round"></span>
                    </label>
                  </li>
                ))}
              </ul>
            ) : (
              <p>No quizzes assigned yet.</p>
            )}
          </div>

          {/* Assign Quiz */}
          <div className="assign-quiz">
            <label>Assign Quiz:</label>
            <select value={selectedQuiz} onChange={(e) => setSelectedQuiz(e.target.value)}>
              <option value="">Select a Quiz</option>
              {quizzes.map((quiz) => (
                <option key={quiz.id} value={quiz.id}>
                  {quiz.title}
                </option>
              ))}
            </select>
            <button className="assign-button" onClick={assignQuizToEvent}>Assign Quiz</button>
          </div>
        </>
      ) : (
        <p>Loading event details...</p>
      )}
    </div>
  );
};

export default EventDetails;
