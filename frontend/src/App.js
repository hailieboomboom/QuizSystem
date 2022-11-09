import React from "react";
import {
    BrowserRouter,
    Routes,
    Route,
    Link,
} from "react-router-dom";
import "./App.css";
import NavigationBar from "./components/NavigationBar";
import Header from "./components/Header";
import SignInSide from "./components/LoginForm";
import Home from "./pages/Home";
import Profile from "./pages/Profile"
import Quiz from "./pages/Quiz";
import Register from "./pages/Register"
import Login from "./pages/Login";
import Logout from "./pages/Logout";
import CreateQuestion from "./pages/CreateQuestion";
import CreateQuiz from "./pages/CreateQuiz"

function App() {
  return (
    <div className="App">
        <BrowserRouter>
            <NavigationBar />
            <Routes>
                <Route path="/" element={<Home/>} />
                <Route path="profile" element={<Profile/>} />
                <Route path="createQuestion" element={<CreateQuestion/>} />
                <Route path="createQuiz" element={<CreateQuiz/>} />
                <Route path="login" element={<Login/>} />
                <Route path="logout" element={<Logout/>} />
                <Route path="register" element={<Register/>} />
                <Route path="quiz" element={<Quiz/>} />
            </Routes>
        </BrowserRouter>

    </div>
  );
}

export default App;
