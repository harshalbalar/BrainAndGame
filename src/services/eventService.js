import axios from "axios";

const API_URL = "http://localhost:8080/api/events";

const createEvent = (event) => {
 const token = localStorage.getItem("jwt");
 if (token) {
 const userId = JSON.parse(atob(token.split(".")[1])).userId;
 event.userId = userId;
 }
 return axios.post(API_URL, event);
};

const getAllEvents = () => axios.get(API_URL);
const getEvent = (eventId) => axios.get(`${API_URL}/${eventId}`);
const assignQuizToEvent = (eventId, quizId) =>
 axios.post(`${API_URL}/${eventId}/assign-quiz/${quizId}`);

const eventService = {
 createEvent,
 getAllEvents,
 getEvent,
 assignQuizToEvent,
};

export default eventService;