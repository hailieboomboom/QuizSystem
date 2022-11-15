import React, { Fragment, useEffect, useState } from 'react'
import '../styles/profileStyle.css';
import editButton from '../styles/editButton.png';
import profilePicture from '../styles/profilePicture.png';
import downArrow from '../styles/downArrow.png';
import {Link} from "react-router-dom";
import Grid from "@mui/material/Grid";
import {apis} from "../utils/apis.js";
import {getUserId} from "../utils/cookies.js";
import FormDialog from "../components/FormDialog";


const Profile = () => {

  const [username, setUsername] = useState();
  const [email, setEmail] = useState();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [role, setRole] = useState();
  const [openDialog, setOpenDialog] = useState(false);
  // const [refresh, s]

  useEffect(()=> {
    apis.getUserById(getUserId()).then(
      res => {
        const data = res.data;
        setUsername(data.username);
        setEmail(data.email);
        setFirstName(data.firstName);
        setLastName(data.lastName);
        setRole(data.role);
      }
    )
  }, [])  

  function handleViewQuestions(){
    console.log("Clicked!")
  }

  function closeWindow(){
    setOpenDialog(false);
  }

  const roleMapper = (name) => {
    switch(name){
      case "AUTHORISED_TRAINER":
        return "Trainer";
      case "AUTHORISED_SALES":
        return "Sales";
      case "TRAINING": 
        return "Student(Training)";
      case "POND":
        return "Student(Pond)";
      case "BEACHED":
        return "Student(Beached)";
    }
  }

  return (
      <div className={"profileContainer"}>
          <div className={"profileBox"}>
              <img src={profilePicture} className={"profilePic"}/>
              <img src={editButton} className={"editIcon"} onClick={()=>setOpenDialog(true)}/>
              <h3>{username}</h3>
              <p>Role: {roleMapper(role)}</p>
              <p>First name: {firstName}</p>
              <p>Last name: {lastName}</p>
              <p>Email: {email}</p>
              {/* <p>Role: {roleMapper(role)}</p> */}
              <button onClick={handleViewQuestions} as={Link} to="/questions" type={"button"}>View Your Questions</button>
              {/* <div className={"profileBottom"}>
                  <p>More information:</p>
                  <img className={"downArrow"} src={downArrow}/>
              </div> */}
          </div>
          <div>
            <FormDialog role={roleMapper(role)} openNow={openDialog} closeWindow={closeWindow}></FormDialog>
          </div>
          {/* <Grid className={"profileGrid"}>
            <Grid item>
              <div className={"profileCenter"}>
                <h1 className={"profileTitle"}>This is your profile!</h1>
              </div>
            </Grid>
          </Grid> */}
      </div>
  )
}

export default Profile