import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { QRCodeCanvas } from "qrcode.react";
import { useNavigate } from "react-router-dom";
import Swal from 'sweetalert2';
import Leaderboard from "./Leaderboard";
import {Tab, TabList, TabPanel, Tabs} from "react-tabs";
import 'react-tabs/style/react-tabs.css';

const EventDetails = () => {
  const navigate = useNavigate();
  const { eventId } = useParams();
  const [event, setEvent] = useState(null);
  const [quizzes, setQuizzes] = useState([]);
  const [leaderboard, setLeaderboard] = useState([]);
  const [loadingLeaderboard, setLoadingLeaderboard] = useState(false);

  // Fetch event details
  const fetchEventDetails = useCallback(async () => {
    try {
      const { data } = await axios.get(`http://localhost:8080/api/events/${eventId}`);
      setEvent(data);
      // Extract quizzes from the event data
      if (data.quizKey) {
        setQuizzes([data.quizKey]); // Wrap quizKey in an array
      } else {
        setQuizzes([]); // No quiz assigned
      }
    } catch (error) {
      console.error("Error fetching event details:", error);
      Swal.fire({
        title: 'Error!',
        text: 'Failed to fetch event details. Please try again',
        icon: 'error',
        confirmButtonText: 'ok'
      });
    }
  }, [eventId]);

  // Fetch leaderboard for the event
  const fetchLeaderboard = useCallback(async () => {
    setLoadingLeaderboard(true);
    try {
      const { data } = await axios.get(`http://localhost:8080/api/events/${eventId}/leaderboard`);
      setLeaderboard(data);
    } catch (error) {
      console.error("Error fetching leaderboard:", error);
      Swal.fire({
        title: 'Error!',
        text: 'Failed to fetch leaderboard. Please try again',
        icon: 'error',
        confirmButtonText: 'ok'
      });
    } finally {
      setLoadingLeaderboard(false);
    }
  }, [eventId]);

  // Fetch data on component mount
  useEffect(() => {
    fetchEventDetails();
    fetchLeaderboard();
  }, [fetchEventDetails, fetchLeaderboard]);

  // Toggle event status (open/closed)
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
      });
    }
  };

  // Toggle quiz status (open/closed)
  const toggleQuizStatus = async (quizId) => {
    try {
      // Send request to toggle quiz status
      const { data } = await axios.put(`http://localhost:8080/api/quizzes/${quizId}/toggle-status`);

      // Update the quizzes state with the new open status
      setQuizzes((prevQuizzes) =>
        prevQuizzes.map((quiz) =>
          quiz.id === quizId ? { ...quiz, open: data.open } : quiz
        )
      );
      navigate("/events");
      // Show success message
      Swal.fire({
        title: 'Success!',
        text: 'Quiz status updated successfully!',
        icon: 'success',
        confirmButtonText: 'Cool'
      });
    } catch (error) {
      console.error("Error toggling quiz status:", error);
      Swal.fire({
        title: 'Error!',
        text: 'Failed to toggle quiz status.',
        icon: 'error',
        confirmButtonText: 'ok'
      });
    }
  };

  const eventUrl = `http://localhost:3000/event/${eventId}`;

  return (
    <div className="event-details">
      {event ? (
        <>
          <h3>{event.name}</h3>
          <Tabs>
            <TabList>
              <Tab>Info</Tab>
              <Tab>Leaderbord</Tab>
            </TabList>

            <TabPanel>
              <p>
                Status:{" "}
                <span className={event.open ? "status-open" : "status-closed"}>
              {event.open ? "Open" : "Closed"}
            </span>
              </p>
              <label className="switch">
                <input type="checkbox" checked={event.open} onChange={toggleEventStatus}/>
                <span className="slider round"></span>
              </label>

              {/* QR Code Section */}
              <div className="qr-section">
                <h4>Guests can join via this QR code:</h4>
                <QRCodeCanvas className="qr-code" value={eventId} size={256}/>
                <p>Event URL: <a href={eventUrl}>{eventUrl}</a></p>
              </div>

              {/* Assigned Quizzes */}
              <div className="quizzes-section">
                <h4>Assigned Quizzes:</h4>
                {quizzes.length > 0 ? (
                    <ul>
                      {quizzes.map((quiz) => (
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
                    <p>No quizzes assigned to this event.</p>
                )}
              </div>
            </TabPanel>
            <TabPanel>
              {/* Leaderboard Section */}
              <div className="leaderboard-section">
                <h4>Leaderboard</h4>
                {loadingLeaderboard ? (
                    <p>Loading leaderboard...</p>
                ) : (
                    <Leaderboard leaderboard={leaderboard} />
                )}
              </div>
            </TabPanel>
          </Tabs>
        </>
      ) : (
        <p>Loading event details...</p>
      )}
    </div>
  );
};

export default EventDetails;