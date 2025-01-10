import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Swal from 'sweetalert2';
import eventService from '../services/eventService';

const EventForm = () => {
  const [name, setName] = useState("");
  const [isOpen] = useState(false); // Always false (Closed)
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    const newEvent = {
      name,
      isOpen, // Always false (Closed)
    };

    eventService
      .createEvent(newEvent)
      .then((response) => {
        Swal.fire({
          title: 'Success!',
          text: 'Event successfully saved!',
          icon: 'success',
          confirmButtonText: 'Cool',
        });
        navigate("/events"); // Navigate to the events page
      })
      .catch((error) => {
        Swal.fire({
          title: 'Error!',
          text: 'There was an error creating the event!',
          icon: 'error',
          confirmButtonText: 'ok',
        });
        console.error("There was an error creating the event!", error);
      });
  };

  return (
    <div id="event">
      <form onSubmit={handleSubmit} className="event-form">
        <p>Create an Event</p>
        <input
          type="text"
          name="Name"
          id="eventName"
          placeholder="Enter the Event Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <br />
        <button type="submit">Add Event</button>
      </form>
    </div>
  );
};

export default EventForm;