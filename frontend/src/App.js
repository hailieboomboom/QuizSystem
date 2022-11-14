import React from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import "./App.css";
import NavigationBar from "./components/NavigationBar";
import Header from "./components/Header";
import SignInSide from "./components/LoginForm";
import Home from "./pages/Home";
import Profile from "./pages/Profile";
import Quiz from "./pages/Quiz";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Logout from "./pages/Logout";
import CreateQuestion from "./pages/CreateQuestion";
import CreateQuiz from "./pages/CreateQuiz";
import Questions from "./pages/Questions";
import ViewQuestions from "./pages/ViewQuestions";
import AutogenerateQuiz from "./pages/AutoGenerateQuiz";
import EditQuestion from "./pages/EditQuestion";
import AttemptQuizzes from "./pages/AttemptQuizzes";
import MyQuizzes from "./pages/MyQuizzes";
import AttemptedQuizzes from "./pages/AttemptedQuizzes";
import Dashboard from './pages/Dashboard'

import {
  RecoilRoot,
  atom,
  selector,
  useRecoilState,
  useRecoilValue,
} from 'recoil';

function App() {
  return (
    <div className="App">
      <RecoilRoot>
      <BrowserRouter>
        <NavigationBar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="profile" element={<Profile />} />
          <Route path="createQuestion" element={<CreateQuestion />} />
          <Route path="createQuiz" element={<CreateQuiz />} />
          <Route path="login" element={<Login />} />
          <Route path="logout" element={<Logout />} />
          <Route path="register" element={<Register />} />
          <Route path="quiz" element={<Quiz />} />
          <Route path="viewQuizzes" element={<AttemptQuizzes/>} />
          <Route path="attemptedQuizzes" element={<AttemptedQuizzes/>} />
          <Route path="myQuizzes" element={<MyQuizzes/>} />
          <Route path="questions" element={<Questions />} />
          <Route path="viewQuestions" element={<ViewQuestions />} />
          <Route path="autoGenerate" element={<AutogenerateQuiz />} />
          <Route path="editQuestion" element={<EditQuestion/>} />
          <Route path="dashboard" element={<Dashboard />} />
        </Routes>
      </BrowserRouter>
      </RecoilRoot>
    </div>
  );
}

export default App;
