import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Link } from "react-router-dom";
import {apis} from "../utils/apis";
import {isLoggedIn,setCookie, deleteCookie} from "../utils/cookies"
import Button from "@mui/material/Button";
import {useEffect, useState} from "react";
import {Dropdown, DropdownButton} from "react-bootstrap";

function NavigationBar() {

  const [logedOut, setLogout] = useState(false);
  useEffect(() => {
    return () => {

    };
  }, [logedOut]);

  console.log("is logged in "+ isLoggedIn())
console.log(logedOut)
  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" >
      <Container>
        <Navbar.Brand as={Link} to="/">
          <img src="https://surveymonkey-assets.s3.amazonaws.com/survey/182409455/e1ca79ba-8544-401a-b369-7cd97429a630.png" width="84" height="50"
               className="d-inline-block align-content-center" alt=""/>
    Quizz App
          </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/">Home</Nav.Link>
            <Nav.Link as={Link} to="/profile">Profile</Nav.Link>
            <div className="me-2">
              <Dropdown>
                <Dropdown.Toggle variant="outline-info" id="dropdown-basic" >
                  Create
                </Dropdown.Toggle>

                <Dropdown.Menu>
                  <Dropdown.Item  as={Link} to="/createQuestion">Create Question</Dropdown.Item>
                  <Dropdown.Item as={Link} to="/createQuiz">Create Quiz</Dropdown.Item>
                  <Dropdown.Item as={Link} to="/autoGenerate">Auto generate quiz</Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            </div>

            <div className="me-2">
              <Dropdown>
                <Dropdown.Toggle variant="outline-info" id="dropdown-basic" >
                  Quiz
                </Dropdown.Toggle>
                <Dropdown.Menu>
                  <Dropdown.Item  as={Link} to="/viewQuizzes">Take Quiz</Dropdown.Item>
                  <Dropdown.Item as={Link} to="/myQuizzes">Manage Quiz</Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            </div>


          </Nav>

            {isLoggedIn() ? (
                <Nav>
                  <Nav.Link onClick={() => {deleteCookie(); setLogout(true)}} as={Link} to="/register">
                   <Button color="success">Logout</Button>
                  </Nav.Link>
                </Nav>
            ) : (
                <Nav>
                  <Nav.Link as={Link} to="/register" onClick={() =>  setLogout(false)}>Register</Nav.Link>
                  <Nav.Link as={Link} to="/login" onClick={() =>  setLogout(false)}>Login</Nav.Link>
                </Nav>
                  )}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
export default NavigationBar;
