import React, { useState, useEffect } from "react";
import axios from "axios";
import { getToken } from "../services/authService"; // Import token helper

const EventList = () => {
  const [events, setEvents] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchEvents();
  }, []);

  const fetchEvents = async () => {
    try {
      const token = getToken(); // Get the user's token for authorization
      const response = await axios.get("http://localhost:8080/api/events", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setEvents(response.data); // Set only the user's events
    } catch (error) {
      setError("Error fetching events. Please try again.");
      console.error("Error fetching events", error);
    }
  };

  // Toggle the status of an event (open/closed)
  const toggleEventStatus = async (eventId) => {
    try {
      const token = getToken(); // Include token in the request
      const response = await axios.put(
        `http://localhost:8080/api/events/${eventId}/toggle-status`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      // Update the event's status locally
      setEvents(
        events.map((event) =>
          event.id === eventId ? { ...event, open: response.data.open } : event
        )
      );
    } catch (error) {
      console.error("Error toggling event status", error);
    }
  };

  return (
    <div className="event-list-container">
      <h2 className="event-list-title">My Events</h2>
      {error && <p className="error-message">{error}</p>}
      <div className="event-list-container-list">
        <ul className="event-list">
          {events.map((event) => (
            <li key={event.id} className="event-item">
              <h3 className="event-name">{event.name}</h3>
              <p className="event-status">
                Status:{" "}
                <span className={event.open ? "open" : "closed"}>
                  {event.open ? "Open" : "Closed"}
                </span>
              </p>

              {/* Toggle switch for event status */}
              <label className="switch">
                <input
                  type="checkbox"
                  checked={event.open}
                  onChange={() => toggleEventStatus(event.id)}
                />
                <span className="slider"></span>
              </label>

              {/* Display assigned quizzes */}
              <div className="assigned-quizzes">
                <h4>Assigned Quizzes:</h4>
                {event.quizzes.length > 0 ? (
                  <ul>
                    {event.quizzes.map((quiz) => (
                      <li key={quiz.id} className="quiz-item">
                        {quiz.title}{" "}
                        <span className={quiz.open ? "open" : "closed"}>
                          {quiz.open ? "Open" : "Closed"}
                        </span>
                      </li>
                    ))}
                  </ul>
                ) : (
                  <p className="no-quizzes">No quizzes assigned yet.</p>
                )}
              </div>

              <button
                className="view-event-button"
                onClick={() => (window.location.href = `/events/${event.id}`)}
              >
                View Event
              </button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default EventList;
