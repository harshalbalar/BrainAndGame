import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { getToken } from "../../services/authService";
import Swal from 'sweetalert2';

const MyEvents = () => {
  const [events, setEvents] = useState([]);
  const [quizzes, setQuizzes] = useState([]);
  const [newEventName, setNewEventName] = useState('');
  const [loading, setLoading] = useState(false);
  const [quizzesLoading, setQuizzesLoading] = useState(false);
  const [assigning, setAssigning] = useState({});

  // Fetch "My Events" on component mount
  useEffect(() => {
    fetchMyEvents();
    fetchQuizzes();
  }, []);

  const fetchMyEvents = async () => {
    setLoading(true);
    try {
      const token = getToken();
      const response = await axios.get('http://localhost:8080/api/events/my', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setEvents(response.data);
    } catch (err) {
      console.error('Failed to fetch events.', err);
      Swal.fire({
        title: 'Error!',
        text: 'Failed to fetch events.',
        icon: 'error',
        confirmButtonText: 'ok'
      });
    } finally {
      setLoading(false);
    }
  };

  const fetchQuizzes = async () => {
    setQuizzesLoading(true);
    try {
      const token = getToken();
      const response = await axios.get('http://localhost:8080/api/quizzes/my', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setQuizzes(response.data);
    } catch (err) {
      console.error('Failed to fetch quizzes.', err);
    } finally {
      setQuizzesLoading(false);
    }
  };

  const handleCreateEvent = async (e) => {
    e.preventDefault();
    if (!newEventName.trim()) {
      Swal.fire({
        title: 'Error!',
        text: 'Event name cannot be empty.',
        icon: 'error',
        confirmButtonText: 'ok'
      });
      return;
    }

    try {
      const token = getToken();
      const newEvent = {
        name: newEventName,
        isOpen: false,
      };
      await axios.post('http://localhost:8080/api/events', newEvent, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
      setNewEventName('');
      fetchMyEvents(); // Refresh the events list
    } catch (err) {
      console.error('Failed to create event.', err);
      Swal.fire({
        title: 'Error!',
        text: 'Failed to create event.',
        icon: 'error',
        confirmButtonText: 'ok'
      });
    }
  };

  const handleAssignQuiz = async (eventId, quizId) => {
    if (assigning[eventId]) return;

    setAssigning(prev => ({ ...prev, [eventId]: true }));
    try {
      const token = getToken();
      await axios.post(`http://localhost:8080/api/events/${eventId}/assign-quiz/${quizId}`, {}, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      fetchMyEvents(); // Refresh events to show the assigned quiz
    } catch (err) {
      console.error(`Failed to assign quiz to event ID ${eventId}.`, err);
      Swal.fire({
        title: 'Error!',
        text: `Something went wrong.`,
        icon: 'error',
        confirmButtonText: 'ok'
      });
    } finally {
      setAssigning(prev => ({ ...prev, [eventId]: false }));
    }
  };

  const toggleEventStatus = async (eventId) => {
    try {
      const token = getToken();
      const { data } = await axios.put(`http://localhost:8080/api/events/${eventId}/toggle-status`, {}, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setEvents(prevEvents => 
        prevEvents.map(event => 
          event.id === eventId ? { ...event, open: data.open } : event
        )
      );
      Swal.fire({
        title: 'Success!',
        text: 'Event successfully saved!',
        icon: 'success',
        confirmButtonText: 'Cool'
      });
    } catch (error) {
      console.error("Error toggling event status:", error);
      Swal.fire({
        title: 'Error!',
        text: 'Failed to toggle event status.',
        icon: 'error',
        confirmButtonText: 'ok'
      });
    }
  };

  return (
    <div id="event">
      <form onSubmit={handleCreateEvent} className="event-form">
        <p className='title'>Create an Event</p>
        <input
          type="text"
          placeholder="Event Name"
          value={newEventName}
          onChange={(e) => setNewEventName(e.target.value)}
        />
        <button type="submit">Create Event</button>
      </form>

      <h2 className="event-list-title">My Events</h2>

      {loading && <p>Loading events...</p>}
      {quizzesLoading && <p>Loading quizzes...</p>}

      <ul className="event-list">
        {events.map(event => (
          <li key={event.id} className="event-item">
            <div className='box1'>
              <div className='box2'>
                <h3 className="event-name">{event.name}</h3>
                <label className="switch">
                  <input 
                    type="checkbox" 
                    checked={event.open} 
                    onChange={() => toggleEventStatus(event.id)} />
                  <span className="slider round"></span>
                </label>
              </div>
              <p className="estatus">Status: {event.open ? 'Open' : 'Closed'}</p>
              <div className="assigned-quizzes">
                {event.quizKey && (
                  <h4>Assigned Quiz: {event.quizKey.title}</h4>
                )}
                <select 
                  defaultValue=""
                  onChange={(e) => handleAssignQuiz(event.id, e.target.value)}
                  disabled={assigning[event.id] || quizzesLoading}>
                  <option value="" disabled>Select a quiz</option>
                  {quizzes.map(quiz => (
                    <option key={quiz.id} value={quiz.id}>
                      {quiz.title}
                    </option>
                  ))}
                </select>
              </div>
              <button
                className="view-event-button"
                onClick={() => (window.location.href = `/events/${event.id}`)}
              >
                View Event
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default MyEvents;