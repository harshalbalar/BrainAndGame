import React from "react";
import QuizForm from "./components/Quizform";
import QuizList from "./components/quizlist";
import './App.css';

const App = () => {
  return (
    <div>
      <h1>Quiz Creator</h1>
      <QuizForm/>
      <QuizList/>
    </div>
  );
};

export default App;