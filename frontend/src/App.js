import React from "react";
import "./App.css";
import NavigationBar from "./components/NavigationBar";
import Header from "./components/Header";
import SignInSide from "./components/LoginForm";
function App() {
  return (
    <div className="App">
      <NavigationBar />
      <Header />
      <SignInSide />
    </div>
  );
}

export default App;
