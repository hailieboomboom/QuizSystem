import React from "react";
import "./App.css";
import NavigationBar from "./components/NavigationBar";
import Header from "./components/Header";
import SignInSide from "./components/LoginForm";
import { ThemeProvider } from '@material-ui/core';
import theme from "./theme";

function App() {
  return (
      <ThemeProvider theme={theme}>

    <div className="App">
      <NavigationBar />
      <Header />
      <SignInSide />
    </div>
      </ThemeProvider>
  );
}

export default App;
