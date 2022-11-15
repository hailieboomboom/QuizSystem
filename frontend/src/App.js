import React from "react";
import { BrowserRouter, Routes, Route,redirect } from "react-router-dom";
import "./App.css";
import NavigationBar from "./components/NavigationBar";
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
import SalesDashboard from "./pages/SalesDashboard";
import TrainerDashboard from "./pages/TrainerDashboard";
import {createTheme, colors, ThemeProvider} from "@mui/material";
import{isLoggedIn} from "./utils/cookies";
import Hero from "./components/Hero"

import {
  RecoilRoot,
  atom,
  selector,
  useRecoilState,
  useRecoilValue,
} from 'recoil';

const theme = createTheme({
  palette: {
    type: 'light',
    primary: {
      main: '#6414a8',
    },
    secondary: {
      main: '#f50057',
    },
  },
  transitions: {
    easing: {
      // This is the most common easing curve.
      easeInOut: 'cubic-bezier(0.4, 0, 0.2, 1)',
      // Objects enter the screen at full velocity from off-screen and
      // slowly decelerate to a resting point.
      easeOut: 'cubic-bezier(0.0, 0, 0.2, 1)',
      // Objects leave the screen at full velocity. They do not decelerate when off-screen.
      easeIn: 'cubic-bezier(0.4, 0, 1, 1)',
      // The sharp curve is used by objects that may return to the screen at any time.
      sharp: 'cubic-bezier(0.4, 0, 0.6, 1)',
    },
  },
})
function App() {
  return (
      <ThemeProvider theme={theme}>
        <div className="App">

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
              <Route path="sales" element={<SalesDashboard />} />
              <Route path="trainer" element={<TrainerDashboard />} />
            </Routes>
          </BrowserRouter>

        </div>
      </ThemeProvider>


  );
}

export default App;
