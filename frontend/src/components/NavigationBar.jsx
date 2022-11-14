import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Link } from "react-router-dom";
function NavigationBar() {
  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
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
            <NavDropdown title="Create" id="collasible-nav-dropdown">
              <NavDropdown.Item as={Link} to="/createQuestion">Create Question</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/createQuiz">
                Create Quiz
              </NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/autoGenerate">Auto Generate Quiz</NavDropdown.Item>
            </NavDropdown>
          </Nav>
          <Nav>
          <Nav.Link as={Link} to="/register">Register</Nav.Link>
            <Nav.Link as={Link} to="/login">Login</Nav.Link>
            <Nav.Link as={Link} to="/logout">
              Logout
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
export default NavigationBar;